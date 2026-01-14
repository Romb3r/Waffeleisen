package hardware;

import lejos.hardware.sensor.SensorMode;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.hardware.ev3.LocalEV3;
import lejos.utility.Delay;

import defines.Define_WaffleState;
import hardware.Motor;


public class ColorSensor {
	// private Attribute
	private EV3ColorSensor EndSwitchColor_sensor = new EV3ColorSensor(LocalEV3.get().getPort("S1"));
	private EV3ColorSensor WaffelStateColor_sensor = new EV3ColorSensor(LocalEV3.get().getPort("S4"));
	private SensorMode sensor_mode = EndSwitchColor_sensor.getRGBMode();
	private float rgb_colors[];									// Ergebnis Array für ein Color Fetch vom Sensor
	private float red;
	private float green;	
	private float blue;
	
	private float[][] fEmptyMinMax;								// Ergebnis Array für die unterschiedlichen RGB Werte im Fall Empty (Waffeleisen nicht befuellt)
	private float[][] fNotReadyMinMax;							// Ergebnis Array für die unterschiedlichen RGB Werte im Fall NotReady (Teig ist roh)
	
	private static float[][] fArrEmpty;							// Dieses Array nimmt die Messwerte im Falle Empty auf
	private static float[][] fArrNotReady;						// Dieses Array nimmt die Messwerte im Falle NotReady auf
	
	// Konstruktoren
	public ColorSensor() {
		this.rgb_colors = new float[sensor_mode.sampleSize()];
		this.fEmptyMinMax = new float[3][Define_WaffleState.iNumCalibSteps];
		this.fNotReadyMinMax = new float[3][Define_WaffleState.iNumCalibSteps];
		this.fArrEmpty = new float[3][Define_WaffleState.iNumCalibSteps];
		this.fArrNotReady = new float[3][Define_WaffleState.iNumCalibSteps];
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
	
	public void vCalibEmpty(Motor motor) {
		// Messungen machen
		int iRotationAngle = 187;
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			motor.vSensorIn(iRotationAngle);
			this.vFetchSampleWaffleState();
			fArrEmpty[Define_WaffleState.iPosRed][i] = this.red;
			fArrEmpty[Define_WaffleState.iPosGreen][i] = this.green;
			fArrEmpty[Define_WaffleState.iPosBlue][i] = this.blue;
			Delay.msDelay(1500);
			motor.vSensorOut(iRotationAngle);
			iRotationAngle = iRotationAngle + 3;
		}
		
		// Min und Max Werte für die entsprechenden Farben kalkulieren und wegspeichern
		float fMin = fArrEmpty[Define_WaffleState.iPosRed][0];
		float fMax = fArrEmpty[Define_WaffleState.iPosRed][0];
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			if(fArrEmpty[Define_WaffleState.iPosRed][i] < fMin) {
				fMin = fArrEmpty[Define_WaffleState.iPosRed][i];
			}
			if(fArrEmpty[Define_WaffleState.iPosRed][i] > fMax) {
				fMax = fArrEmpty[Define_WaffleState.iPosRed][i];
			}
		}
		this.fEmptyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMax] = fMax;					// Abspeichern der Max & Min Werte für Rot im Zustand Empty (Kein Teig im Eisen)
		this.fEmptyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMin] = fMin;
		
		fMin = fArrEmpty[Define_WaffleState.iPosGreen][0];
		fMax = fArrEmpty[Define_WaffleState.iPosGreen][0];
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			if(fArrEmpty[Define_WaffleState.iPosGreen][i] < fMin) {
				fMin = fArrEmpty[Define_WaffleState.iPosGreen][i];
			}
			if(fArrEmpty[Define_WaffleState.iPosGreen][i] > fMax) {
				fMax = fArrEmpty[Define_WaffleState.iPosGreen][i];
			}
		}
		this.fEmptyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMax] = fMax;					// Abspeichern der Max & Min Werte für Rot im Zustand Empty (Kein Teig im Eisen)
		this.fEmptyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMin] = fMin;
		
		fMin = fArrEmpty[Define_WaffleState.iPosBlue][0];
		fMax = fArrEmpty[Define_WaffleState.iPosBlue][0];
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			if(fArrEmpty[Define_WaffleState.iPosBlue][i] < fMin) {
				fMin = fArrEmpty[Define_WaffleState.iPosBlue][i];
			}
			if(fArrEmpty[Define_WaffleState.iPosBlue][i] > fMax) {
				fMax = fArrEmpty[Define_WaffleState.iPosBlue][i];
			}
		}
		this.fEmptyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMax] = fMax;					// Abspeichern der Max & Min Werte für Rot im Zustand Empty (Kein Teig im Eisen)
		this.fEmptyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMin] = fMin;	
	}
	
	public void vCalibNotReady(Motor motor) {
		// Messungen machen
		int iRotationAngle = 187;
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			motor.vSensorIn(iRotationAngle);
			this.vFetchSampleWaffleState();
			fArrNotReady[Define_WaffleState.iPosRed][i] = this.red;
			fArrNotReady[Define_WaffleState.iPosGreen][i] = this.green;
			fArrNotReady[Define_WaffleState.iPosBlue][i] = this.blue;
			Delay.msDelay(1500);
			motor.vSensorIn(iRotationAngle);
			iRotationAngle = iRotationAngle + 3;
		}
		
		// Min und Max Werte für die entsprechenden Farben kalkulieren und wegspeichern
		float fMin = fArrNotReady[Define_WaffleState.iPosRed][0];
		float fMax = fArrNotReady[Define_WaffleState.iPosRed][0];
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			if(fArrNotReady[Define_WaffleState.iPosRed][i] < fMin) {
				fMin = fArrNotReady[Define_WaffleState.iPosRed][i];
			}
			if(fArrNotReady[Define_WaffleState.iPosRed][i] > fMax) {
				fMax = fArrNotReady[Define_WaffleState.iPosRed][i];
			}
		}
		this.fNotReadyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMax] = fMax;					// Abspeichern der Max & Min Werte für Rot im Zustand Empty (Kein Teig im Eisen)
		this.fNotReadyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMin] = fMin;
		
		fMin = fArrNotReady[Define_WaffleState.iPosGreen][0];
		fMax = fArrNotReady[Define_WaffleState.iPosGreen][0];
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			if(fArrNotReady[Define_WaffleState.iPosGreen][i] < fMin) {
				fMin = fArrNotReady[Define_WaffleState.iPosGreen][i];
			}
			if(fArrNotReady[Define_WaffleState.iPosGreen][i] > fMax) {
				fMax = fArrNotReady[Define_WaffleState.iPosGreen][i];
			}
		}
		this.fNotReadyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMax] = fMax;					// Abspeichern der Max & Min Werte für Rot im Zustand Empty (Kein Teig im Eisen)
		this.fNotReadyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMin] = fMin;
		
		fMin = fArrNotReady[Define_WaffleState.iPosBlue][0];
		fMax = fArrNotReady[Define_WaffleState.iPosBlue][0];
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			if(fArrNotReady[Define_WaffleState.iPosBlue][i] < fMin) {
				fMin = fArrNotReady[Define_WaffleState.iPosBlue][i];
			}
			if(fArrNotReady[Define_WaffleState.iPosBlue][i] > fMax) {
				fMax = fArrNotReady[Define_WaffleState.iPosBlue][i];
			}
		}
		this.fNotReadyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMax] = fMax;					// Abspeichern der Max & Min Werte für Rot im Zustand Empty (Kein Teig im Eisen)
		this.fNotReadyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMin] = fMin;	
	}

	public int iEvalWaffleState() {
		this.vFetchSampleWaffleState();
		
		if((this.red >= this.fEmptyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMin]) && (this.red <= this.fEmptyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMax]) &&
		   (this.green >= this.fEmptyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMin]) && (this.green <= this.fEmptyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMax]) &&
		   (this.blue >= this.fEmptyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMin]) && (this.blue <= this.fEmptyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMax])) {
			return Define_WaffleState.iEmpty;
		}
		else if((this.red >= this.fNotReadyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMin]) && (this.red <= this.fNotReadyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMax]) &&
				(this.green >= this.fNotReadyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMin]) && (this.green <= this.fNotReadyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMax]) &&
				(this.blue >= this.fNotReadyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMin]) && (this.blue <= this.fNotReadyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMax])) {
			return Define_WaffleState.iNotReady;
		}
		return Define_WaffleState.iReady;
	}
	
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
	
}
