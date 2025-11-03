package modes;

import defines.Define_Hardware;
import hardware.Motor;
import hardware.StartBtn;
import hardware.StopBtn;
import hardware.CancelBtn;

import lejos.hardware.Button;


public class ManualMode extends Mode {
	public Motor motor = new Motor(Define_Hardware.iMotorSpeed, Define_Hardware.iMotorRotationAngle * Define_Hardware.iMotorNumRotations);			// Eine Umdrehung * Anzahl Umdrehungen
	public StartBtn start_btn = new StartBtn();
	public StopBtn stop_btn = new StopBtn();
	public CancelBtn cancel_btn = new CancelBtn();
	
	// Konstruktoren
	public ManualMode() {
		this.iID = 1;
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
			if(start_btn.boButtonPressed(Button.ID_UP)) {
				motor.vOpen();
			}
			if(start_btn.boButtonPressed(Button.ID_DOWN)) {
				motor.vClose();
			}
			if(start_btn.boButtonPressed(Button.ID_ENTER)) {
				boRun = false;
			}
		}
	}
}
