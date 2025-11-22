package hardware;

import defines.Define_WaffleState;

import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.ev3.LocalEV3;



public class ColorSensor {
	// private Attribute
	private EV3ColorSensor color_sensor = new EV3ColorSensor(LocalEV3.get().getPort("S1"));
	private SensorMode sensor_mode = color_sensor.getRGBMode();
	private float rgb_colors[];
	
	// Konstruktoren
	public ColorSensor() {
		this.rgb_colors = new float[sensor_mode.sampleSize()];
		this.color_sensor.setCurrentMode("RGB");
	}
	
	// öffentliche Methoden
	public int iEvalWaffleState() {
	/*
	 * Anhand des Farbsensors wird ausgewertet, ob die Waffel fertig gebacken wurde
	 */
		color_sensor.fetchSample(rgb_colors, 0);
		// TODO: Logik implementieren
		
		return Define_WaffleState.iEmpty;
	}
}
