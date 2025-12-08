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
		if(base_btn.boButtonPressedBlocking(Button.ID_ENTER)) {																	// Starte die Automatik Routine wenn Start Knopf gedrückt
			while (boRun) {																										// Lasse solange den Automatikmodus laufen, bis der Stop Knopf gedrückt wurde
				if (this.color_sensor.iEvalWaffleState() == Define_WaffleState.iEmpty) {
					vWaitFor(Define_Timer.iFillUpTime); 																		// Wenn Waffeleisen kein Teig enthält, warte um Teig einfüllen zu können
				}
				this.motor.vClose();
				while (iWaffleState == Define_WaffleState.iNotReady && iWaffleState != Define_WaffleState.iEmpty) {				// Laufe solange bis Waffel fertig
					vCook(Define_Timer.iSleepTimeMS);																			// Waffeleisen zu: Backe Teig	
					this.motor.vOpen();																							// Öffne Waffeleisen
					this.vStopMotor(true); 																						// Stoppe Motor, wenn Waffeleisen geöffnet
					
					iWaffleState = this.color_sensor.iEvalWaffleState();														// Werte aus, ob Teig fertig, wenn ja gehe aus der Schleife raus
					this.motor.vClose(); 																						// Schließe Waffeleisen um weiter zu backen
					this.vStopMotor(false); 																					// Stoppe Motor, wenn Waffeleisen geschlossen
				}
				this.speaker.vDoBeep();																							// Waffel fertig, gebe einen Ton wieder
				vWaitFor(Define_Timer.iWaffleRemovalTime);  																	// Warte um Waffel entnehmen zu können
				boRun = !this.base_btn.boButtonPressedBlockingTimeout(Button.ID_ESCAPE, Define_Timer.iWaitTimeStopBtnPress);	// Wenn Stop Knopf gedrückt wurde, liefert die Funktion true zurück, allerdings soll dann der Automatikmodus abgebrochen werden, also invertieren mit "!"
				Define_Timer.vResetSleepTime();																					// Setze Timer der Backzeit zurück für neue Waffel
			}
		}
	}
	
	public void vStopMotor(boolean boCheckWhenOpen) {
		while (this.motor.getLeftMotor().isMoving() &&				// Solange beide Motoren drehen
			   this.motor.getRightMotor().isMoving())
		{	
			if(boCheckWhenOpen) {									// Abhängig der Übergabe, prüfe ob Waffeleisen geschlossen oder geöffnet
				if(this.color_sensor.boIsOpen()) {					// Wenn Waffeleisen geöffnet, stoppe beide Motoren
					this.motor.getLeftMotor().stop();
					this.motor.getRightMotor().stop();
				}
			}
			else if(!boCheckWhenOpen) {								// Abhängig der Übergabe, prüfe ob Waffeleisen geschlossen oder geöffnet
				if(this.color_sensor.boIsClosed()) {				// Wenn Waffeleisen geöffnet, stoppe beide Motoren
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
		Delay.msDelay(iWaitTime);																						// Warte um die Zeit iWaitTime
		Define_Timer.iSleepTimeMS -= 5 * 1000;																			// Dekrementiere die Zeit für die die Waffel weiter gebacken wird in einem möglichen weiteren Backvorgang. * 1000 um auf ms zu kommen 
	}
	
	public static void vWaitFor(int iWaitTime) {
		/*
		 * Warte um die übergebene Zeit
		 */
		Delay.msDelay(iWaitTime);
	}
}
