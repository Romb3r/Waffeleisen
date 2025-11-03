package modes;

import defines.Define_Hardware;
import defines.Define_Mode;
import hardware.Motor;
import hardware.BaseButton;

import lejos.hardware.Button;


public class ManualMode extends Mode {
	public Motor motor = new Motor(Define_Hardware.iMotorSpeed, Define_Hardware.iMotorRotationAngle * Define_Hardware.iMotorNumRotations);			// Eine Umdrehung * Anzahl Umdrehungen
	public BaseButton base_btn = new BaseButton();
	
	// Konstruktoren
	public ManualMode() {
		this.iID = Define_Mode.iManualMode;
		this.sName = "Manueller Modus";
	}
	// private Methoden
	public void vRoutine() {
	/*
	 * Routinen Ablauf des Auto Modus
	 */
		boolean boRun = true;
		System.out.println("START KNOPF PRESSEN");
		while(boRun) {
			if(base_btn.boButtonPressed(Button.ID_UP)) {
				motor.vOpen();
			}
			if(base_btn.boButtonPressed(Button.ID_DOWN)) {
				motor.vClose();
			}
			if(base_btn.boButtonPressed(Button.ID_ENTER)) {
				boRun = false;
			}
		}
	}
}
