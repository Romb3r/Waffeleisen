package defines;

public class Define_Timer {
	public static int iBakeTimeMS = 30 * 1000;				// 30 Sekunden * 1000 um auf millisekunden zu kommen
	public static int iFillUpTime = 10 * 1000;				// Zeit zum Teig einfuellen
	public static int iWaffleRemovalTime = 20 * 1000;		// Zeit zum Waffel entfernen
	public static int iWaitTimeStopBtnPress = 10 * 1000;	// Timeout fuer den Stop Button
	
	public static void vResetSleepTime() {
		/*
		 * Setze Back Zeit zurueck
		 */
		iBakeTimeMS = 30 * 1000;							
	}
}
