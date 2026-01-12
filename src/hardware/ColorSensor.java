package hardware;

import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.hardware.ev3.LocalEV3;

import defines.Define_WaffleState;


public class ColorSensor {
	// private Attribute
	private EV3ColorSensor EndSwitchColor_sensor = new EV3ColorSensor(LocalEV3.get().getPort("S1"));
	private EV3ColorSensor WaffelStateColor_sensor = new EV3ColorSensor(LocalEV3.get().getPort("S4"));
	private SensorMode sensor_mode = EndSwitchColor_sensor.getRGBMode();
	private float rgb_colors[];
	private float red;
	private float green;
	private float blue;
	
	// Konstruktoren
	public ColorSensor() {
		this.rgb_colors = new float[sensor_mode.sampleSize()];
		this.EndSwitchColor_sensor.setCurrentMode(this.EndSwitchColor_sensor.getColorIDMode().getName());
		this.WaffelStateColor_sensor.setCurrentMode(this.WaffelStateColor_sensor.getRGBMode().getName());
	}
	
	// Oeffentliche Methoden
	public boolean boIsOpen() {
		/*
		 * Werte Farbsensor aus, wenn Blau erkannt liefere true, ansonsten false
		 */
		if(this.EndSwitchColor_sensor.getColorID() == Color.BLUE) {
			return true;
		}
		return false;
	}
	
	public boolean boIsClosed() {
		/*
		 * Werte Farbsensor aus, wenn Rot erkannt liefere true, ansonsten false
		 */
		if(this.EndSwitchColor_sensor.getColorID() == Color.RED) {
			return true;
		}
		return false;
	}

	public int iEvalWaffleState() {
		boolean boValid = false;
		this.vFetchSampleWaffleState();
		
		while(!boValid) {
			System.out.println("Hole Wafflestate");
			this.vFetchSampleWaffleState();
			if(this.boCheckRed(0.016, 0.017) && this.boCheckGreen(0.075, 0.085) && this.boCheckBlue(0.008, 0.009)) {
				System.out.println("Empty");
				return Define_WaffleState.iEmpty;
			}
			
			if(this.boCheckRed(0.0875, 0.08975) && this.boCheckGreen(0.0885, 0.0985) && this.boCheckBlue(0.0275, 0.0375)) {
				System.out.println("Not ready");
				return Define_WaffleState.iNotReady;
			}
			
			if(this.boCheckRed(0.004, 0.001) && this.boCheckGreen(0.003, 0.018) && this.boCheckBlue(0.0009, 0.004)) {
				System.out.println("Ready");
				return Define_WaffleState.iReady;
			}
		}
		return 99;
	}
		
		//Berechnungen
		/*float REDminusGREEN = red - green;
		float REDminusBLUE = red - blue;
		float GREENminusBLUE = green - blue;
		float GREENminusRED  = green - red;
		float BLUEminusRED = blue - red;
		float BLUEminusGREEN = blue - green;
		
		// negativer Wert umwandeln
		if(REDminusGREEN < 0) {
			REDminusGREEN = REDminusGREEN * (-1);
		}
		if(REDminusGREEN < 0.08 && blue < 0.01) {
			System.out.println("Empty");
			return Define_WaffleState.iEmpty;
		}
		else if (REDminusGREEN < 0.08 && blue < 0.01)
		{
			System.out.println("Ready");
			return Define_WaffleState.iReady;
		}
		else if(red > green && green > blue)
		{
			System.out.println("Not Ready");
			return Define_WaffleState.iNotReady;
		}
		else {System.out.println("quatsch ausgewertet");
		return 99;
		}
		
	}*/
	
	private void vFetchSampleWaffleState() {
		/*
		 * Hole ein Color Sensor Sample
		 * Speichere in den verschiedenen Variablen
		 */
		this.WaffelStateColor_sensor.fetchSample(this.rgb_colors, 0);
		this.red = rgb_colors[0];
		this.green = rgb_colors[1];
		this.blue = rgb_colors[2];
	}
	
	private boolean boCheckRed(double fMin, double fMax) {
		if(red <= fMax && red > fMin) {
			return true;
		}
		return false;
	}
	
	private boolean boCheckGreen(double fMin, double fMax) {
		if(green <= fMax && green > fMin) {
			return true;
		}
		return false;
	}
	
	private boolean boCheckBlue(double fMin, double fMax) {
		if(blue <= fMax && blue > fMin) {
			return true;
		}
		return false;
	}
}
