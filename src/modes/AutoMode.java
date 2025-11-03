package modes;

import hardware.ColorSensor;
import hardware.Motor;
import hardware.Speaker;
import hardware.StartBtn;
import hardware.StopBtn;
import lejos.hardware.Button;

public class AutoMode extends Mode {
	public ColorSensor color_sensor = new ColorSensor();
	public Motor motor = new Motor(1, 70);
	public Speaker speaker = new Speaker();
	public StartBtn start_btn = new StartBtn();
	// Konstruktoren
	public AutoMode() {
		this.iID = 2;
		this.sName = "Automatik Modus";
		
	}
	// private Methoden
	public void vRoutine() {
	/*
	 * Routinen Ablauf des Auto Modus
	 */
		System.out.println(this.sName);
		if(start_btn.boButtonPressed(Button.ID_UP)) {
			color_sensor.boEvalWaffleState();
			motor.vOpen();
			motor.vClose();
			speaker.vDoBeep();
		}
	// TODO: Routine implementieren
	}
}
