package hardware;

import lejos.hardware.ev3.LocalEV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.EV3MediumRegulatedMotor;

public class Motor {
	// private Attribute
	private EV3LargeRegulatedMotor CMotorLeft = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("A"));			// EV3LareRegulatedMotor Objekt an Port A erzeugen
	private EV3LargeRegulatedMotor CMotorRight = new EV3LargeRegulatedMotor(LocalEV3.get().getPort("B"));           // EV3LareRegulatedMotor Objekt an Port B erzeugen
	private EV3MediumRegulatedMotor CMotorState = new EV3MediumRegulatedMotor(LocalEV3.get().getPort("C"));			// EV3MediumRegulateMotor Objekt an Port C erzeugen
	private int iSpeed;																								// Drehgeschwindigkeit der Motoren
	private int iMaxAngle;																							// Drehwinkel der Motoren
	
	// Konstruktoren
	public Motor(int iSpeed, int iMaxAngle) {																		// \
		this.iSpeed = iSpeed;																						//  \
		this.iMaxAngle = iMaxAngle;																					//	 Initialisieren der Werte im Konstruktor
		CMotorLeft.setSpeed(this.iSpeed);																			//  /
		CMotorRight.setSpeed(this.iSpeed);																			// /
		CMotorState.setSpeed(200);
	}
	
	// oeffentliche Methoden
	public void vOpen() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geoeffnet wird
	 */
		CMotorLeft.forward();
		CMotorRight.forward();
	}
	
	public void vClose() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geschlossen wird 
	 */
		CMotorLeft.backward();
		CMotorRight.backward();
	}
	
	public void vSensorIn() {
	/*
	 * Sensor faehrt ins Werkzeug ein, zum WaffelStateCheck
	 */
		CMotorState.rotate(190);
	}
	
	public void vSensorOut() {
	/*
	 * Sensor faehrt aus dem Werkzeug heraus, auf Grundstellung
	 */
		CMotorState.rotate(-1*190);
	}
	
	public EV3LargeRegulatedMotor getLeftMotor() {
		return CMotorLeft;
	}
	
	public EV3LargeRegulatedMotor getRightMotor() {
		return CMotorRight;
	}
	
	public EV3MediumRegulatedMotor getStateMotor() {
		return CMotorState;
	}
}
