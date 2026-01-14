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
	}
}
