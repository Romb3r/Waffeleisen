package modes;

import defines.Define_Mode;
import hardware.BaseButton;
import hardware.ColorSensor;
import hardware.Motor;

import lejos.hardware.Button;

public class CalibrationMode extends Mode {
	
	// Konstruktoren
	public CalibrationMode(ColorSensor cs, Motor m, BaseButton bs) {
		this.color_sensor = cs;
		this.motor = m;
		this.base_btn = bs;
		this.iID = Define_Mode.iCalibMode;
		this.sName = "Kalibrierungs Modus";
	}
	
	// Oeffentliche Methoden
	public void vRoutine() {
		/*
		 * Routine fuer die Kalibrierung des Farbsensors
		 * Dieser Sensor soll den Wafflestate auslesen
		 */
		this.motor.iOpen();														// Oeffne Waffeleisen
		this.vStopMotor(true);
		
		System.out.println("Waffeleisen leer?");
		System.out.println("Enter zum bestaetigen...");
		this.base_btn.boButtonPressedBlocking(Button.ID_ENTER);					// Warte bis ENTER gedrueckt wurde
		this.color_sensor.vCalibEmpty(this.motor);								// Mache die Kalibrierung fuer den Empty Fall (Kein Teig im Waffeleisen)
		
		System.out.println("Rohteig eingefuellt?");
		System.out.println("Enter zum bestaetigen...");
		this.base_btn.boButtonPressedBlocking(Button.ID_ENTER);					// Warte bis ENTER gedrueckt wurde
		this.color_sensor.vCalibNotReady(this.motor);							// Mache die Kalibrierung fuer den NotReady Fall (Teig ist roh)
		
		this.motor.iClose();													// Schliesse Waffeleisen
		this.vStopMotor(false);
	}
	
	public void vStopMotor(boolean boCheckWhenOpen) {
		/*
		 * Halte die Motore an, wenn der Farbsensor die entsprechende Endmarkierung detektiert
		 * boCheckWhenOpen: True  -> Waffeleisen soll sich oeffnen und aufhoeren wenn geoeffnet
		 *                  False -> Waffeleisen soll sich schliessen und aufhoeren wenn geschlossen
		 */
		while (this.motor.getLeftMotor().isMoving() &&				// Solange beide Motoren drehen
			   this.motor.getRightMotor().isMoving())
		{	
			if(boCheckWhenOpen) {
				if(this.color_sensor.boIsOpen()) {					// Wenn Waffeleisen geoeffnet, stoppe beide Motoren
					this.motor.getLeftMotor().stop();
					this.motor.getRightMotor().stop();
				}
			}
			else if(!boCheckWhenOpen) {
				if(this.color_sensor.boIsClosed()) {				// Wenn Waffeleisen geschlossen, stoppe beide Motoren
					this.motor.getLeftMotor().stop();
					this.motor.getRightMotor().stop();
				}
			}
		}
	}
}
