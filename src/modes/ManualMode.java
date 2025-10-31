package modes;

import hardware.Motor;
import hardware.StartBtn;
import hardware.StopBtn;

public class ManualMode extends Mode {
	public Motor motor = new Motor(1, 70);
	public StartBtn start_btn = new StartBtn();
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
		System.out.println(this.sName);
	// TODO: Routine implementieren
	}
}
