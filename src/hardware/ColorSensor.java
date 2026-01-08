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
		if(this.EndSwitchColor_sensor.getColorID() == Color.BLUE) {
			return true;
		}
		return false;
	}
	
	public boolean boIsClosed() {
		if(this.EndSwitchColor_sensor.getColorID() == Color.RED) {
			return true;
		}
		return false;
	}

	public int iEvalWaffleState() {
		this.vFetchSampleWaffleState();
		float grey = red - green;
		if(red > green && green > blue) {
			return Define_WaffleState.iReady; 
		}
		else if(grey > 0.1){
			return Define_WaffleState.iNotReady;
		}
		else {
			return Define_WaffleState.iEmpty;
		}
	}
	
	private void vFetchSampleWaffleState() {
		this.WaffelStateColor_sensor.fetchSample(this.rgb_colors, 0);
		this.red = rgb_colors[0];
		this.green = rgb_colors[1];
		this.blue = rgb_colors[2];
	}
}
