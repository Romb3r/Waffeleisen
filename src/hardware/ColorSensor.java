package hardware;

import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.ev3.LocalEV3;



public class ColorSensor {
	// private Attribute
	private EV3ColorSensor EndSwitchColor_sensor = new EV3ColorSensor(LocalEV3.get().getPort("S1"));
	private EV3ColorSensor WaffelStateColor_sensor = new EV3ColorSensor(LocalEV3.get().getPort("S4"));
	private SensorMode sensor_mode = EndSwitchColor_sensor.getRGBMode();
	private float rgb_colors[];
	private float red = rgb_colors[0];
	private float green = rgb_colors[1];
	private float blue = rgb_colors[2];
	
	// Konstruktoren
	public ColorSensor() {
		this.rgb_colors = new float[sensor_mode.sampleSize()];
		this.EndSwitchColor_sensor.setCurrentMode("RGB");
		this.WaffelStateColor_sensor.setCurrentMode("RGB");
	}
	
	public boolean boIsOpen() {
		EndSwitchColor_sensor.fetchSample(rgb_colors, 0);
		float newfloat = rgb_colors[0] - rgb_colors[1];
		if(newfloat < 0) {
			newfloat = newfloat * -1;
		}
		if(newfloat < 0.1)	{	
			// wenn Werte ungefähr gleich, dann ist es Gelb
			return true;
		}
		return false;
	}
	
	public boolean boIsClosed() {
		this.EndSwitchColor_sensor.fetchSample(rgb_colors, 0);
		if((red > 0.028431373) && (red < 0.029411765)) {		// Idikator fuer GESCHLOSSEN => Roter Stein
			if(rgb_colors[2] <= 0.0001) {											// B Stelle muss fast 0 sein
				return true;
			}
		}
		return false;
	}

	public int iEvalWaffleState() {
		// Check fuer WaffelState --> blau wert ist der beste Indikator!
		this.WaffelStateColor_sensor.fetchSample(rgb_colors, 0);
		if(red > green) {
			if(green > blue)
			return 1; 
			}
		else if(rgb_colors[2] > 0.3) {
			return 0;
			}
		return 2;
	}
}
