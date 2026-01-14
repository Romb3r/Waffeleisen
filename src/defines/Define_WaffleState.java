package defines;

public class Define_WaffleState {
	public static int iNotReady = 0;		// Waffel noch nicht fertig
	public static int iReady = 1;			// Waffel fertig
	public static int iEmpty = 2;			// Kein Teig im Waffeleisen
	
	public static int iPosRed = 0;			//   Positionen für 2 dimensionales Array
	public static int iPosGreen = 1;		//  /
	public static int iPosBlue = 2;         // /
	
	public static int iPosMax = 0;			//  Index für die Max & Min Werte (zum Speichern im Array)
	public static int iPosMin = 1;          // /
	
	public static int iNumCalibSteps = 20;	// Anzahl wie oft der Farbsensor eine Messung zur Kalibrierung durchführen soll
}
