package modes;

import defines.Define_Mode;
import defines.Define_Timer;
import defines.Define_WaffleState;
import defines.Define_State;
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
		boolean boRun = true;
		boolean geoeffnet = false;
		int Schritt = Define_State.INIT;
		int Waffelstate = 99;
		
		if(this.base_btn.boButtonPressedBlocking(Button.ID_ENTER)) {
			while(boRun) {
				if(Schritt == Define_State.INIT) {
					if(this.color_sensor.boIsOpen()) {
						Schritt = Define_State.INIT_OPENED;
						geoeffnet = true;
					}
					if(this.color_sensor.boIsClosed()) {
						Schritt = Define_State.INIT_CLOSED;
						geoeffnet = false;
					}
				}
				if(!geoeffnet && Schritt == Define_State.INIT_CLOSED) {
					this.motor.vOpen();
					this.vStopMotor(true);
					geoeffnet = true;
					Schritt = Define_State.INIT_OPENED;
				}
				if((Schritt == Define_State.INIT_CLOSED || Schritt == Define_State.INIT_OPENED) && geoeffnet) {
					this.motor.vSensorIn();
					Schritt = Define_State.EVALUATE;
				}
				if(Schritt == Define_State.EVALUATE) {
					Waffelstate = this.color_sensor.iEvalWaffleState();
					switch (Waffelstate) {
					case Define_WaffleState.iNotReady:
						System.out.println("Not Ready");
						Schritt = Define_State.OPENED;
						break;
					case Define_WaffleState.iReady:
						System.out.println("Ready");
						Schritt = Define_State.READY;
						break;
					case Define_WaffleState.iEmpty:
						System.out.println("Empty");
						Schritt = Define_State.FILL_UP;
						
						break;
					case 99:
						System.out.println("Keiner Aenderung des Zustands durch Methode iEvalWaffleState");
					default:
						// Funktion kann: Ready/NotReady/Empty - zurueck geben & 99 als Startwert
						break;
					}
				}
				if(Schritt == Define_State.OPENED) {
					this.motor.vSensorOut();
					System.out.println("Waffeleisen schliessen");
					this.motor.vClose();
					this.vStopMotor(false);
					geoeffnet = false;
					Schritt = Define_State.COOKING_SHORT;
				}
				if(Schritt == Define_State.READY) {
					this.motor.vSensorOut();
					System.out.println("Waffel fertig");
					this.speaker.vDoBeep();
					Schritt = Define_State.EXIT;
				}
				if(Schritt == Define_State.FILL_UP) {
					this.motor.vSensorOut();
					System.out.println("Teig einfuellen");
					vWaitFor(Define_Timer.iFillUpTime);
					Schritt = Define_State.EVALUATE;
				}
				if(Schritt == Define_State.COOKING) {
					vCook(Define_Timer.iSleepTimeMS);
					Schritt = Define_State.INIT_CLOSED;	
				}
				if(Schritt == Define_State.COOKING_SHORT) {
					vCook(Define_Timer.iSleepTimeMS);
					Schritt = Define_State.INIT_CLOSED;
				}
				if(Schritt == Define_State.EXIT) {
					System.out.println("Abbrechen? - Escape\n" + "Weiter? - Enter");
					Delay.msDelay(2000);
					if(this.base_btn.boButtonPressedBlockingTimeout(Button.ID_ESCAPE, Define_Timer.iWaffleRemovalTime)) {
						this.motor.vClose();
						this.vStopMotor(false);
						geoeffnet = false;
						Define_Timer.vResetSleepTime();
						Schritt = Define_State.INIT_CLOSED;
						boRun = false;
						break;
					}
					System.out.println("Teig einfuellen");
					vWaitFor(Define_Timer.iFillUpTime);
					Schritt = Define_State.INIT_OPENED;
				}
			}	
		}
		System.out.println("Routine wird beendet");
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
	//	Define_Timer.iSleepTimeMS -= 5 * 1000;																			// Dekrementiere die Zeit fuer die die Waffel weiter gebacken wird in einem moeglichen weiteren Backvorgang. * 1000 um auf ms zu kommen 
	}
	
	public static void vWaitFor(int iWaitTime) {
		/*
		 * Warte um die Uebergebene Zeit
		 */
		Delay.msDelay(iWaitTime);
	}
}

