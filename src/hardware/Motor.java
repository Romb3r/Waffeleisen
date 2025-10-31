package hardware;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Motor {
	// private Attribute
	private EV3LargeRegulatedMotor CMotor = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("S2"));
	private int iSpeed;
	private int iMaxAngle;
	
	// Konstruktoren
	public Motor(int iSpeed, int iMaxAngle) {
		this.iSpeed = iSpeed;
		this.iMaxAngle = iMaxAngle;
	}
	
	// öffentliche Methoden
	public void vOpen() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geöffnet wird
	 */
		// TODO: implementieren
	}
	
	public void vClose() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geschlossen wird 
	 */
		// TODO: implementieren
	}
}
