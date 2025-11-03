package hardware;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Motor {
	// private Attribute
	private EV3LargeRegulatedMotor CMotorLeft = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));			// EV3LareRegulatedMotor Objekt an Port A erzeugen
	private EV3LargeRegulatedMotor CMotorRight = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));           // EV3LareRegulatedMotor Objekt an Port A erzeugen
	private int iSpeed;																								// Drehgeschwindigkeit der Motoren
	private int iMaxAngle;																							// Drehwinkel der Motoren
	
	// Konstruktoren
	public Motor(int iSpeed, int iMaxAngle) {																		// \
		this.iSpeed = iSpeed;																						//  \
		this.iMaxAngle = iMaxAngle;																					//	 Initialisieren der Werte im Konstruktor
		CMotorLeft.setSpeed(this.iSpeed);																			//  /
		CMotorRight.setSpeed(this.iSpeed);																			// /
	}
	
	// öffentliche Methoden
	public void vOpen() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geöffnet wird
	 */
		CMotorLeft.rotate(this.iMaxAngle, true);			    // Drehe linken Motor. True damit die Funktion nicht blockiert.
		CMotorRight.rotate(this.iMaxAngle * (-1), true);		// Drehe rechten Motor. * (-1) um die Drehrichtung anzupassen. True damit die Funktion nicht blockiert.

	}
	
	public void vClose() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geschlossen wird 
	 */
		CMotorLeft.rotate(this.iMaxAngle * (-1), true);				// Drehe linken Motor. * (-1) um die Drehrichtung anzupassen. True damit die Funktion nicht blockiert.
		CMotorRight.rotate(this.iMaxAngle, true);				    // Drehe rechten Motor. True damit die Funktion nicht blockiert.
	}
}
