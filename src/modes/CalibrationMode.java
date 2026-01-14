package modes;

import defines.Define_Mode;
import hardware.BaseButton;
import hardware.ColorSensor;
import hardware.Motor;

import lejos.hardware.Button;

public class CalibrationMode extends Mode {
	private ColorSensor color_sensor;
	private Motor motor;
	private BaseButton base_btn;
	
	public CalibrationMode(ColorSensor cs, Motor m, BaseButton bs) {
		this.color_sensor = cs;
		this.motor = m;
		this.base_btn = bs;
		this.iID = Define_Mode.iCalibMode;
		this.sName = "Kalibrierungs Modus";
	}
	
	public void vRoutine() {
		this.motor.iOpen();
		this.vStopMotor(true);
		
		System.out.println("Waffeleisen leer?");
		System.out.println("Enter zum bestaetigen...");
		this.base_btn.boButtonPressed(Button.ID_ENTER);
		this.motor.vSensorIn();
		this.color_sensor.vCalibEmpty();
		this.motor.vSensorOut();
		
		System.out.println("Rohteig eingefuellt?");
		System.out.println("Enter zum bestaetigen...");
		this.base_btn.boButtonPressed(Button.ID_ENTER);
		this.motor.vSensorIn();
		this.color_sensor.vCalibNotReady();
		this.motor.vSensorOut();
		
		this.motor.iClose();
		this.vStopMotor(false);
	}
	
	public void vStopMotor(boolean boCheckWhenOpen) {
		while (this.motor.getLeftMotor().isMoving() &&				// Solange beide Motoren drehen
			   this.motor.getRightMotor().isMoving())
		{	
			if(boCheckWhenOpen) {									// Abhaengig der Uebergabe, pruefe ob Waffeleisen geschlossen oder geoeffnet
				if(this.color_sensor.boIsOpen()) {					// Wenn Waffeleisen geoeffnet, stoppe beide Motoren
					this.motor.getLeftMotor().stop();
					this.motor.getRightMotor().stop();
				}
			}
			else if(!boCheckWhenOpen) {								// Abhaengig der Uebergabe, pruefe ob Waffeleisen geschlossen oder geoeffnet
				if(this.color_sensor.boIsClosed()) {				// Wenn Waffeleisen geoeffnet, stoppe beide Motoren
					this.motor.getLeftMotor().stop();
					this.motor.getRightMotor().stop();
				}
			}
		}
	}
}
