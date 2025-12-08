package modes;

import defines.Define_Mode;
import hardware.Motor;
import hardware.BaseButton;
import hardware.ColorSensor;

import lejos.hardware.Button;


public class ManualMode extends Mode {
	private Motor motor;
	private BaseButton base_btn;
	private ColorSensor color_sensor;
	
	// Konstruktoren
	public ManualMode(Motor m, BaseButton bs, ColorSensor cs) {
		this.motor = m;
		this.base_btn = bs;
		this.color_sensor = cs;
		this.iID = Define_Mode.iManualMode;
		this.sName = "Manueller Modus";
	}
	
	// private Methoden
	public void vRoutine() {
	/*
	 * Routinen Ablauf des Manuellen Modus
	 */
		boolean boRun = true;
		System.out.println("Hoch / Runter / Escape");
		while(boRun) {
			if(this.base_btn.boButtonPressed(Button.ID_UP)) {				// Öffne Waffeleisen wenn Taste OBEN gedrückt wurde
				this.motor.vOpen();
				while (this.motor.getLeftMotor().isMoving() &&				// Solange beide Motoren drehen
					   this.motor.getRightMotor().isMoving())
				{		
					if(this.color_sensor.boIsOpen()) {						// Wenn Waffeleisen geöffnet, stoppe beide Motoren
						this.motor.getLeftMotor().stop();
						this.motor.getRightMotor().stop();
					}
				}
			}
			if(this.base_btn.boButtonPressed(Button.ID_DOWN)) {				// Schließe Waffeleisen wenn Taste UNTEN gedrückt wurde
				this.motor.vClose();
				while (this.motor.getLeftMotor().isMoving() &&				// Solange beide Motoren drehen
					   this.motor.getRightMotor().isMoving())
				{
					if(this.color_sensor.boIsClosed()) {					// Wenn Waffeleisen geschlossen, stoppe beide Motoren
						this.motor.getLeftMotor().stop();
						this.motor.getRightMotor().stop();
					}
				}
			}
			if(this.base_btn.boButtonPressed(Button.ID_ESCAPE)) {			// Breche ab, wenn Taste ESCAPE gedrückt wurde
				boRun = false;
			}
		}
	}
}
