package hardware;

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
	
	public boolean boIsOpen() {
		color_sensor.fetchSample(rgb_colors, 0);
		if((rgb_colors[0] > 0.042156864) && (rgb_colors[0] < 0.04411765)) {			// Idikator für OFFEN => Gelber Stein
			if(rgb_colors[2] > 0.0001) {											// B Stelle muss größer 0.0001 sein
				return true;
			}
		}
		return false;
	}
	
	public boolean boIsClosed() {
		color_sensor.fetchSample(rgb_colors, 0);
		if((rgb_colors[0] > 0.028431373) && (rgb_colors[0] < 0.029411765)) {		// Idikator für GESCHLOSSEN => Roter Stein
			if(rgb_colors[2] <= 0.0001) {											// B Stelle muss fast 0 sein
				return true;
			}
		}
		return false;
	}
}
