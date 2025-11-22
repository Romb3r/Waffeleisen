package modes;

import defines.Define_Mode;
import defines.Define_Hardware;
import hardware.BaseButton;
import hardware.ColorSensor;
import hardware.Motor;
import hardware.Speaker;

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
		System.out.println(this.sName);
		if(base_btn.boButtonPressed(Button.ID_ENTER)) {
			color_sensor.boEvalWaffleState();
			motor.vOpen();
			motor.vClose();
			speaker.vDoBeep();
		}
	// TODO: Routine implementieren
	}
}
