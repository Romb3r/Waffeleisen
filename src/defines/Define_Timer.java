package defines;

public class Define_Timer {
	public static int iSleepTimeMS = 30 * 1000;				// 30 Sekunden * 1000 um auf millisekunden zu kommen
	public static int iFillUpTime = 40 * 1000;
	public static int iWaffleRemovalTime = 20 * 1000;
	public static int iWaitTimeStopBtnPress = 10 * 1000;
	
	public static void vResetSleepTime() {
		iSleepTimeMS = 30 * 1000;
	}
}
