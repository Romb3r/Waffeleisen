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
	private float rgb_colors[];									// Ergebnis Array fuer ein Color Fetch vom Sensor
	private float red;											// \
	private float green;										//  Hier werden die Einzelfarben des Arrays rgb_colors abgespeichert
	private float blue;											// /
	
	private float[][] fEmptyMinMax;								// Ergebnis Array fuer die unterschiedlichen RGB Werte im Fall Empty (Waffeleisen nicht befuellt) fuer die Kalibrierung
	private float[][] fNotReadyMinMax;							// Ergebnis Array fuer die unterschiedlichen RGB Werte im Fall NotReady (Teig ist roh) fuer die Kalibrierung
	private static float[][] fArrCalibValues;					// Dieses Array nimmt die Messwerte auf die benutzt werden zum kalibrieren
	
	// Konstruktoren
	public ColorSensor() {
		this.rgb_colors = new float[sensor_mode.sampleSize()];
		this.fEmptyMinMax = new float[3][2];
		this.fNotReadyMinMax = new float[3][2];
		this.EndSwitchColor_sensor.setCurrentMode(this.EndSwitchColor_sensor.getColorIDMode().getName());
		this.WaffelStateColor_sensor.setCurrentMode(this.WaffelStateColor_sensor.getRGBMode().getName());
		fArrCalibValues = new float[3][Define_WaffleState.iNumCalibSteps];
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
		/*
		 * Macht eine definierte Anzahl von Messungen fuer keinen Teig im Waffeleisen (Empty)
		 * Ermittelt Min und Max Werte fuer die verschiedenen RGB Werte
		 */
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			motor.vSensorIn(200);																			// Farbsensor in das Waffeleisen einfahren (Winkel: 200)
			this.vFetchSampleWaffleState();																	// Eine Messung machen
			fArrCalibValues[Define_WaffleState.iPosRed][i] = this.red;										// Rot Wert an Stelle [rot][i] speichern
			fArrCalibValues[Define_WaffleState.iPosGreen][i] = this.green;									// Gruen Wert an Stelle [gruen][i] speichern
			fArrCalibValues[Define_WaffleState.iPosBlue][i] = this.blue;									// Blau Wert an Stelle [blau][i] speichern
			Delay.msDelay(500);																				// 0.5s Delay um Mechanik zu schonen
			motor.vSensorOut(200);																			// Farbsensor aus dem Waffeleisen fahren (Winkel: 200)
			Delay.msDelay(500);																				// 0.5s Delay um Mechanik zu schonen
		}
		
		// Min und Max Werte für die entsprechenden Farben kalkulieren und wegspeichern
		float fMin = fArrCalibValues[Define_WaffleState.iPosRed][0];										// \
		float fMax = fArrCalibValues[Define_WaffleState.iPosRed][0];										//  \
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {										//   \
			if(fArrCalibValues[Define_WaffleState.iPosRed][i] < fMin) {										//    \
				fMin = fArrCalibValues[Define_WaffleState.iPosRed][i];										//     Min und Max Wert fuer Rot Empty ermitteln
			}																								//    /
			if(fArrCalibValues[Define_WaffleState.iPosRed][i] > fMax) {										//   /
				fMax = fArrCalibValues[Define_WaffleState.iPosRed][i];										//  /
			}																				
		}
		this.fEmptyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMax] = fMax;					//  Abspeichern der Max & Min Werte fuer Rot im Zustand Empty (Kein Teig im Eisen)
		this.fEmptyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMin] = fMin;					// /
		
		fMin = fArrCalibValues[Define_WaffleState.iPosGreen][0];											// \
		fMax = fArrCalibValues[Define_WaffleState.iPosGreen][0];											//  \
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {										//   \
			if(fArrCalibValues[Define_WaffleState.iPosGreen][i] < fMin) {									//    \
				fMin = fArrCalibValues[Define_WaffleState.iPosGreen][i];									//     Min und Max Werte fuer Gruen Empty ermittlen
			}																								//    /
			if(fArrCalibValues[Define_WaffleState.iPosGreen][i] > fMax) {									//   /
				fMax = fArrCalibValues[Define_WaffleState.iPosGreen][i];									//  /
			}
		}
		this.fEmptyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMax] = fMax;					// Abspeichern der Max & Min Werte für Rot im Zustand Empty (Kein Teig im Eisen)
		this.fEmptyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMin] = fMin;
		
		fMin = fArrCalibValues[Define_WaffleState.iPosBlue][0];												// \
		fMax = fArrCalibValues[Define_WaffleState.iPosBlue][0];												//  \
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {										//   \
			if(fArrCalibValues[Define_WaffleState.iPosBlue][i] < fMin) {									//    \
				fMin = fArrCalibValues[Define_WaffleState.iPosBlue][i];										//     Min und Max Werte fuer Blau Empty ermittlen
			}																								//    /
			if(fArrCalibValues[Define_WaffleState.iPosBlue][i] > fMax) {									//   /
				fMax = fArrCalibValues[Define_WaffleState.iPosBlue][i];										//  /
			}
		}
		this.fEmptyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMax] = fMax;					// Abspeichern der Max & Min Werte fuer Rot im Zustand Empty (Kein Teig im Eisen)
		this.fEmptyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMin] = fMin;	
	}
	
	public void vCalibNotReady(Motor motor) {
		/*
		 * Macht eine definierte Anzahl von Messungen fuer einen rohen Teig im Waffeleisen (NotReady)
		 * Ermittelt Min und Max Werte fuer die verschiedenen RGB Werte
		 */
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {
			motor.vSensorIn(200);																			// Farbsensor in das Waffeleisen einfahren (Winkel: 200)
			this.vFetchSampleWaffleState();																	// Eine Messung machen
			fArrCalibValues[Define_WaffleState.iPosRed][i] = this.red;										// Rot Wert an Stelle [rot][i] speichern
			fArrCalibValues[Define_WaffleState.iPosGreen][i] = this.green;									// Gruen Wert an Stelle [gruen][i] speichern
			fArrCalibValues[Define_WaffleState.iPosBlue][i] = this.blue;									// Blau Wert an Stelle [blau][i] speichern
			Delay.msDelay(500);																				// 0.5s Delay um Mechanik zu schonen
			motor.vSensorOut(200);																			// Farbsensor aus dem Waffeleisen fahren (Winkel: 200)
			Delay.msDelay(500);																				// 0.5s Delay um Mechanik zu schonen
		}
		
		// Min und Max Werte für die entsprechenden Farben kalkulieren und wegspeichern
		float fMin = fArrCalibValues[Define_WaffleState.iPosRed][0];										// \
		float fMax = fArrCalibValues[Define_WaffleState.iPosRed][0];										//  \
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {										//   \
			if(fArrCalibValues[Define_WaffleState.iPosRed][i] < fMin) {										//    \
				fMin = fArrCalibValues[Define_WaffleState.iPosRed][i];										//     Min und Max Wert fuer Rot NotReady ermitteln
			}																								//    /
			if(fArrCalibValues[Define_WaffleState.iPosRed][i] > fMax) {										//   /
				fMax = fArrCalibValues[Define_WaffleState.iPosRed][i];										//  /
			}																				
		}
		this.fNotReadyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMax] = fMax;				// Abspeichern der Max & Min Werte fuer Rot im Zustand NotReady (Teig roh)
		this.fNotReadyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMin] = fMin;
		
		fMin = fArrCalibValues[Define_WaffleState.iPosGreen][0];											// \
		fMax = fArrCalibValues[Define_WaffleState.iPosGreen][0];											//  \
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {										//   \
			if(fArrCalibValues[Define_WaffleState.iPosGreen][i] < fMin) {									//    \
				fMin = fArrCalibValues[Define_WaffleState.iPosGreen][i];									//     Min und Max Werte fuer Gruen NotReady ermittlen
			}																								//    /
			if(fArrCalibValues[Define_WaffleState.iPosGreen][i] > fMax) {									//   /
				fMax = fArrCalibValues[Define_WaffleState.iPosGreen][i];									//  /
			}
		}
		this.fNotReadyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMax] = fMax;				// Abspeichern der Max & Min Werte für Gruen im Zustand NotReady (Teig roh)
		this.fNotReadyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMin] = fMin;
		
		fMin = fArrCalibValues[Define_WaffleState.iPosBlue][0];												// \
		fMax = fArrCalibValues[Define_WaffleState.iPosBlue][0];												//  \
		for(int i = 0; i < Define_WaffleState.iNumCalibSteps; i++) {										//   \
			if(fArrCalibValues[Define_WaffleState.iPosBlue][i] < fMin) {									//    \
				fMin = fArrCalibValues[Define_WaffleState.iPosBlue][i];										//     Min und Max Werte fuer Blau NotReady ermittlen
			}																								//    /
			if(fArrCalibValues[Define_WaffleState.iPosBlue][i] > fMax) {									//   /
				fMax = fArrCalibValues[Define_WaffleState.iPosBlue][i];										//  /
			}
		}
		this.fNotReadyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMax] = fMax;				// Abspeichern der Max & Min Werte fuer Rot im Zustand NotReady (Teig roh)
		this.fNotReadyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMin] = fMin;	
	}

	public int iEvalWaffleState() {
		this.vFetchSampleWaffleState();
		
		if((this.red >= this.fEmptyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMin]) && (this.red <= this.fEmptyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMax]) &&
		   (this.green >= this.fEmptyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMin]) && (this.green <= this.fEmptyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMax]) &&
		   (this.blue >= this.fEmptyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMin]) && (this.blue <= this.fEmptyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMax])) {
			// Wenn rot, gruen und blau innerhalb der Min & Max Werte fuer den Fall Empty liegen, gebe iEmpty zurueck
			return Define_WaffleState.iEmpty;
		}
		else if((this.red >= this.fNotReadyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMin]) && (this.red <= this.fNotReadyMinMax[Define_WaffleState.iPosRed][Define_WaffleState.iPosMax]) &&
				(this.green >= this.fNotReadyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMin]) && (this.green <= this.fNotReadyMinMax[Define_WaffleState.iPosGreen][Define_WaffleState.iPosMax]) &&
				(this.blue >= this.fNotReadyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMin]) && (this.blue <= this.fNotReadyMinMax[Define_WaffleState.iPosBlue][Define_WaffleState.iPosMax])) {
			// Wenn rot, gruen und blau innerhalb der Min & Max Werte fuer den Fall NotReady liegen, gebe iNotReady zurueck
			return Define_WaffleState.iNotReady;
		}
		// Sonst gebe iReady zurueck
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
