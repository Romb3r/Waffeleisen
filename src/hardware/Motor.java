package hardware;

import defines.Define_Hardware;
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
	
	public Motor() {
		
	}
	
	// oeffentliche Methoden
	public int iOpen() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geoeffnet wird
	 */
		CMotorLeft.forward();																						// Linker Motor vorwaerts
		CMotorRight.forward();																						// Rechter Motor vorwaerts
		return Define_Hardware.iMotorOpen;
	}
	
	public int iClose() {
	/*
	 * Steuert den Motor so an, dass das Waffeleisen geschlossen wird 
	 */
		CMotorLeft.backward();																						// Linker Motor rueckwaerts
		CMotorRight.backward();																						// Rechter Motor rueckwarts
		return Define_Hardware.iMotorClosed;
	}
	
	public void vSensorIn(int iAngle) {
	/*
	 * Sensor faehrt ins Werkzeug ein, zum WaffelStateCheck
	 */
		CMotorState.rotate(iAngle);																					// Fahre Sensor in das Waffeleisen
	}
	
	public void vSensorOut(int iAngle) {
	/*
	 * Sensor faehrt aus dem Werkzeug heraus, auf Grundstellung
	 */
		CMotorState.rotate(-1*iAngle);																					// Fahre Sensor wieder aus dem Waffeleisen raus
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
