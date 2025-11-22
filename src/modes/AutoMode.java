package modes;

import defines.Define_Mode;
import defines.Define_Hardware;
import defines.Define_Timer;
import defines.Define_WaffleState;
import hardware.BaseButton;
import hardware.ColorSensor;
import hardware.Motor;
import hardware.Speaker;

import lejos.utility.Delay;
import lejos.hardware.Button;

public class AutoMode extends Mode {
	public ColorSensor color_sensor = new ColorSensor();
	public Motor motor = new Motor(Define_Hardware.iMotorSpeed, Define_Hardware.iMotorRotationAngle * Define_Hardware.iMotorNumRotations);			// Eine Umdrehung * Anzahl Umdrehungen
	public Speaker speaker = new Speaker();
	public BaseButton base_btn = new BaseButton();
	
	// Konstruktoren
	public AutoMode() {
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
		if(base_btn.boButtonPressedBlocking(Button.ID_ENTER)) {															// Starte die Automatik Routine wenn Start Knopf gedrückt
			while (boRun) {																								// Lasse solange den Automatikmodus laufen, bis der Stop Knopf gedrückt wurde
				if (color_sensor.iEvalWaffleState() == Define_WaffleState.iEmpty) {
					vWaitForFillup(); 																					// Wenn Waffeleisen kein Teig enthält, warte um Teig einfüllen zu können
				}
				motor.vClose();
				while (iWaffleState == Define_WaffleState.iNotReady && iWaffleState != Define_WaffleState.iEmpty) {		// Laufe solange bis Waffel fertig
					vCook(Define_Timer.iSleepTimeMS);																	// Waffeleisen zu: Backe Teig	
					motor.vOpen();																						// Öffne Waffeleisen
					iWaffleState = color_sensor.iEvalWaffleState();														// Werte aus, ob Teig fertig, wenn ja gehe aus der Schleife raus
					motor.vClose(); 																					// Schließe Waffeleisen um weiter zu backen
				}
				speaker.vDoBeep();																						// Waffel fertig, gebe einen Ton wieder
				vWaitForWaffleRemoval();  																				// Warte um Waffel entnehmen zu können
				boRun = !base_btn.boButtonPressedBlockingTimeout(Button.ID_ESCAPE, Define_Timer.iWaitTimeStopBtnPress);	// Wenn Stop Knopf gedrückt wurde, liefert die Funktion true zurück, allerdings soll dann der Automatikmodus abgebrochen werden, also invertieren mit "!"
				Define_Timer.iSleepTimeMS = 30 * 1000;																	// Setze Timer der Backzeit zurück für neue Waffel
			}
		}
	}
	
	// statische Funktionen
	public static void vCook(int iWaitTime) {
		/*
		 * Warte um die Zeit iWaitTime
		 */
		Delay.msDelay(iWaitTime);
		Define_Timer.iSleepTimeMS -= 5 * 1000;																		// Dekrementiere die Zeit für die die Waffel weiter gebacken wird in einem möglichen weiteren Backvorgang. * 1000 um auf ms zu kommen 
	}
	
	public static void vWaitForFillup() {
		Delay.msDelay(Define_Timer.iFillUpTime);
	}
	
	public static void vWaitForWaffleRemoval() {
		Delay.msDelay(Define_Timer.iWaffleRemovalTime);
	}
}
