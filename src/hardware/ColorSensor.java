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
	private int iTest = 0;
	
	// Konstruktoren
	public ColorSensor() {
		this.rgb_colors = new float[sensor_mode.sampleSize()];
		this.EndSwitchColor_sensor.setCurrentMode(this.EndSwitchColor_sensor.getColorIDMode().getName());			// Color ID Mode
		this.WaffelStateColor_sensor.setCurrentMode("RGB");
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
		// Check fuer WaffelState --> blau wert ist der beste Indikator!
		/*this.vFetchSampleWaffleState();
		if(red > green) {
			if(green > blue)
			return Define_WaffleState.iReady; 
			}
		else if(this.rgb_colors[2] > 0.3) {
			return Define_WaffleState.iNotReady;
			}*/
		if(this.iTest == 0) {
			this.iTest += 1;
			return Define_WaffleState.iEmpty;
		}
		if(this.iTest == 1) {
			this.iTest += 1;
			return Define_WaffleState.iNotReady;
		}
		else {
			this.iTest = 0;
			return Define_WaffleState.iReady;
		}
	}
	
	private void vFetchSampleEndSwitch() {
		this.EndSwitchColor_sensor.fetchSample(this.rgb_colors, 0);
		this.red = rgb_colors[0];
		this.green = rgb_colors[1];
		this.blue = rgb_colors[2];
	}
	
	private void vFetchSampleWaffleState() {
		this.WaffelStateColor_sensor.fetchSample(this.rgb_colors, 0);
		this.red = rgb_colors[0];
		this.green = rgb_colors[1];
		this.blue = rgb_colors[2];
	}
}
