package modes;

import defines.Define_Mode;
import defines.Define_Timer;
import defines.Define_WaffleState;
import hardware.BaseButton;
import hardware.ColorSensor;
import hardware.Motor;
import hardware.Speaker;

import lejos.utility.Delay;
import lejos.hardware.Button;

public class AutoMode extends Mode {
	private ColorSensor color_sensor;
	private Motor motor;
	private Speaker speaker;
	private BaseButton base_btn;
	
	// Konstruktoren
	public AutoMode(ColorSensor cs, Motor m, Speaker s, BaseButton bs) {
		this.color_sensor = cs;
		this.motor = m;
		this.speaker = s;
		this.base_btn = bs;
		this.iID = Define_Mode.iAutoMode;
		this.sName = "Automatik Modus";
	}
	
	// private Methoden
	public void vRoutine() {
	/*
	 * Routinen Ablauf des Auto Modus
	 */
		int iWaffleState = Define_WaffleState.iEmpty;
		boolean boRun = true;
		System.out.println("Start Knopf pressen wenn Teig im Eisen");
		if(this.base_btn.boButtonPressedBlocking(Button.ID_ENTER)) {															// Starte die Automatik Routine wenn Start Knopf gedrueckt
			while (boRun) {																										// Lasse solange den Automatikmodus laufen, bis der Stop Knopf gedrueckt wurde
				if (this.color_sensor.boIsClosed()) {
					this.motor.vOpen();																							// Wenn Waffeleisen nicht offen, oeffne Motor
					this.vStopMotor(true);																						// Stoppe Motor, wenn Waffeleiesen geoeffnet
				}
				do {		
					this.motor.vSensorIn();																						// Waffle State Sensor reinfahren
					iWaffleState = this.color_sensor.iEvalWaffleState();														// Pruefe Waffle State
					if(iWaffleState == Define_WaffleState.iEmpty) {
						System.out.println("Teig einfuellen!");
						this.motor.vSensorOut();
						boRun = !this.base_btn.boButtonPressedBlockingTimeout(Button.ID_ESCAPE, 5000);		// Wenn Stop Knopf gedrueckt wurde, liefert die Funktion true zurueck, allerdings soll dann der Automatikmodus abgebrochen werden, also invertieren mit "!"
						if(!boRun) {
							break;																								// Verlasse Do while Schleife
						}
					}	
				} while (iWaffleState == Define_WaffleState.iEmpty);															// Wiederhole Do While Schleife wenn kein Teig vorhanden 																						// Waffle State Sensor wieder rausfahren
				this.motor.vSensorOut();
				this.motor.vClose();																							// Schliesse Waffeleisen
				this.vStopMotor(false);																							// Stoppe Motor, wenn Waffeleisen geschlossen
				while (iWaffleState == Define_WaffleState.iNotReady) {															// Laufe solange bis Waffel fertig
					vCook(Define_Timer.iSleepTimeMS);																			// Waffeleisen zu: Backe Teig	
					this.motor.vOpen();																							// Oeffne Waffeleisen
					this.vStopMotor(true); 																						// Stoppe Motor, wenn Waffeleisen geoeffnet
					
					this.motor.vSensorIn(); 																					// Bringe Sensor fuer die Ueberpruefung des Waffle States in Position
					iWaffleState = this.color_sensor.iEvalWaffleState();														// Werte aus, ob Teig fertig, wenn ja gehe aus der Schleife raus
					this.motor.vSensorOut(); 																					// Fahre Sensor zurueck in Position
					if(iWaffleState == Define_WaffleState.iReady) {
						break;
					}
					this.motor.vClose(); 																						// Schliesse Waffeleisen um weiter zu backen
					this.vStopMotor(false); 																					// Stoppe Motor, wenn Waffeleisen geschlossen
				}
				if(iWaffleState == Define_WaffleState.iReady) {
					this.speaker.vDoBeep();																							// Waffel fertig, gebe einen Ton wieder
					boRun = !this.base_btn.boButtonPressedBlockingTimeout(Button.ID_ESCAPE, Define_Timer.iWaffleRemovalTime);		// Wenn Stop Knopf gedrueckt wurde, liefert die Funktion true zurueck, allerdings soll dann der Automatikmodus abgebrochen werden, also invertieren mit "!"
					Define_Timer.vResetSleepTime();																					// Setze Timer der Backzeit zurueck fuer neue Waffel
				}
			}
		}
	}
	
	public void vStopMotor(boolean boCheckWhenOpen) {
		while (this.motor.getLeftMotor().isMoving() &&				// Solange beide Motoren drehen
			   this.motor.getRightMotor().isMoving())
		{	
			if(boCheckWhenOpen) {									// Abhaengig der Uebergabe, pruefe ob Waffeleisen geschlossen oder geoeffnet
				if(this.color_sensor.boIsOpen()) {					// Wenn Waffeleisen geoeffnet, stoppe beide Motoren
					this.motor.getLeftMotor().stop();
					this.motor.getRightMotor().stop();
				}
			}
			else if(!boCheckWhenOpen) {								// Abhaengig der Uebergabe, pruefe ob Waffeleisen geschlossen oder geoeffnet
				if(this.color_sensor.boIsClosed()) {				// Wenn Waffeleisen geoeffnet, stoppe beide Motoren
					this.motor.getLeftMotor().stop();
					this.motor.getRightMotor().stop();
				}
			}
		}
	}
	
	// statische Funktionen
	public static void vCook(int iWaitTime) {
		/*
		 * Warte um die Zeit iWaitTime und dekrementiere um 5 Sekunden pro Aufruf in der Schleife
		 */
		System.out.println("Backe Waffel!");
		Delay.msDelay(iWaitTime);																						// Warte um die Zeit iWaitTime
		Define_Timer.iSleepTimeMS -= 5 * 1000;																			// Dekrementiere die Zeit fuer die die Waffel weiter gebacken wird in einem moeglichen weiteren Backvorgang. * 1000 um auf ms zu kommen 
	}
	
	public static void vWaitFor(int iWaitTime) {
		/*
		 * Warte um die Uebergebene Zeit
		 */
		Delay.msDelay(iWaitTime);
	}
}
