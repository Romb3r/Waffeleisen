package defines;

public class Define_Timer {
	public static int iSleepTimeMS = 10 * 1000;				// 10 Sekunden * 1000 um auf millisekunden zu kommen
	public static int iFillUpTime = 15 * 1000;
	public static int iWaffleRemovalTime = 10 * 1000;
	public static int iWaitTimeStopBtnPress = 10 * 1000;
	
	public static void vResetSleepTime() {
		iSleepTimeMS = 10 * 1000;
	}
}
