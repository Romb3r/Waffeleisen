package modes;

import defines.Define_Hardware;
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
	private Speaker speaker;
	private int iMotorState;
	
	// Konstruktoren
	public AutoMode(ColorSensor cs, Motor m, Speaker s, BaseButton bs) {
		this.color_sensor = cs;
		this.motor = m;
		this.speaker = s;
		this.base_btn = bs;
		this.iID = Define_Mode.iAutoMode;
		this.sName = "Automatik Modus";
	}
	
	// oeffentliche Methoden
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
					this.iMotorState = this.motor.iOpen();																		// Wenn Waffeleisen nicht offen, oeffne Motor
					this.vStopMotor(true);																						// Stoppe Motor, wenn Waffeleiesen geoeffnet
				}
				do {		
					this.motor.vSensorIn(200);																					// Waffle State Sensor reinfahren

					iWaffleState = this.color_sensor.iEvalWaffleState();														// Pruefe Waffle State
					if(iWaffleState == Define_WaffleState.iEmpty) {
						System.out.println("Teig einfuellen!");
						this.motor.vSensorOut(200);
						boRun = !this.base_btn.boButtonPressedBlockingTimeout(Button.ID_ESCAPE, Define_Timer.iFillUpTime);		// Wenn Stop Knopf gedrueckt wurde, liefert die Funktion true zurueck, allerdings soll dann der Automatikmodus abgebrochen werden, also invertieren mit "!"
						if(!boRun) {
							break;																								// Verlasse Do while Schleife
						}
					}	
				} while (iWaffleState == Define_WaffleState.iEmpty);															// Wiederhole Do While Schleife wenn kein Teig vorhanden 																						// Waffle State Sensor wieder rausfahren
				this.motor.vSensorOut(200);
				if(iWaffleState == Define_WaffleState.iNotReady) {
					this.iMotorState = this.motor.iClose();																		// Schliesse Waffeleisen
					this.vStopMotor(false);																						// Stoppe Motor, wenn Waffeleisen geschlossen
				}
				if(iWaffleState == Define_WaffleState.iNotReady) {																	
					while (true) {																								// Laufe bis break	
						vCook(Define_Timer.iBakeTimeMS);																		// Waffeleisen zu: Backe Teig	
						this.iMotorState = this.motor.iOpen();																	// Oeffne Waffeleisen
						this.vStopMotor(true); 																					// Stoppe Motor, wenn Waffeleisen geoeffnet
						
						this.motor.vSensorIn(200); 																				// Bringe Sensor fuer die Ueberpruefung des Waffle States in Position
						iWaffleState = this.color_sensor.iEvalWaffleState();													// Werte aus, ob Teig fertig, wenn ja gehe aus der Schleife raus
						this.motor.vSensorOut(200); 																			// Fahre Sensor zurueck in Position
						if(iWaffleState == Define_WaffleState.iReady) {
							break;
						}
						else {
							this.iMotorState = this.motor.iClose(); 															// Schliesse Waffeleisen um weiter zu backen
							this.vStopMotor(false); 																			// Stoppe Motor, wenn Waffeleisen geschlossen
							System.out.println("Weiterbacken!");
							System.out.println("Restbackzeit: " + Define_Timer.iBakeTimeMS + "s");
						}
					}
				}
				if(iWaffleState == Define_WaffleState.iReady) {
					if(this.iMotorState == Define_Hardware.iMotorClosed) {
						this.motor.iOpen();
						this.vStopMotor(false);
					}
					System.out.println("Waffel fertig!");
					this.speaker.vDoBeep();																						// Waffel fertig, gebe einen Ton wieder
					System.out.println("Teig nachfuellen...");	
					boRun = !this.base_btn.boButtonPressedBlockingTimeout(Button.ID_ESCAPE, Define_Timer.iWaffleRemovalTime);	// Wenn Stop Knopf gedrueckt wurde, liefert die Funktion true zurueck, allerdings soll dann der Automatikmodus abgebrochen werden, also invertieren mit "!"
					Define_Timer.vResetSleepTime();																				// Setze Timer der Backzeit zurueck fuer neue Waffel
				}
			}
		}
	}
	
	public void vStopMotor(boolean boCheckWhenOpen) {
		/*
		 * Halte die Motore an, wenn der Farbsensor die entsprechende Endmarkierung detektiert
		 * boCheckWhenOpen: True  -> Waffeleisen soll sich oeffnen und aufhoeren wenn geoeffnet
		 *                  False -> Waffeleisen soll sich schliessen und aufhoeren wenn geschlossen
		 */
		while (this.motor.getLeftMotor().isMoving() &&				// Solange beide Motoren drehen
			   this.motor.getRightMotor().isMoving())
		{	
			if(boCheckWhenOpen) {
				if(this.color_sensor.boIsOpen()) {					// Wenn Waffeleisen geoeffnet, stoppe beide Motoren
					this.motor.getLeftMotor().stop();
					this.motor.getRightMotor().stop();
				}
			}
			else if(!boCheckWhenOpen) {
				if(this.color_sensor.boIsClosed()) {				// Wenn Waffeleisen geschlossen, stoppe beide Motoren
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
		Delay.msDelay(iWaitTime);									// Warte um die Zeit iWaitTime
		Define_Timer.iBakeTimeMS -= 5 * 1000;						// Dekrementiere die Zeit fuer die die Waffel weiter gebacken wird in einem moeglichen weiteren Backvorgang. * 1000 um auf ms zu kommen 
	}
	
	public static void vWaitFor(int iWaitTime) {
		/*
		 * Warte um die Uebergebene Zeit
		 */
		Delay.msDelay(iWaitTime);
	}
}
