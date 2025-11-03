package hardware;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Motor {
	// private Attribute
	private EV3LargeRegulatedMotor CMotorLeft = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));
	private EV3LargeRegulatedMotor CMotorRight = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));
	private int iSpeed;
	private int iMaxAngle;
	
	// Konstruktoren
	public Motor(int iSpeed, int iMaxAngle) {
		this.iSpeed = iSpeed;
		this.iMaxAngle = iMaxAngle;
		CMotorLeft.setSpeed(this.iSpeed);
		CMotorRight.setSpeed(this.iSpeed);
	}
	
	// öffentliche Methoden
	public void vOpen() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geöffnet wird
	 */
		CMotorLeft.rotate(this.iMaxAngle * (-1), true);			// * (-1) um die Drehrichtung anzupassen
		CMotorRight.rotate(this.iMaxAngle * (-1), true);		// * (-1) um die Drehrichtung anzupassen

	}
	
	public void vClose() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geschlossen wird 
	 */
		CMotorLeft.rotate(this.iMaxAngle, true);
		CMotorRight.rotate(this.iMaxAngle, true);
	}
}
