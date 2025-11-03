package hardware;
import lejos.hardware.Button;

public class BaseButton {
	// geschützte Attribute
	protected int iID;
	
	// öffentliche Methoden
	public int iGetID() {
		return this.iID;
	}
	
	public boolean boButtonPressed(int iExpectedMask) {
	/*
	 * Wrapper für lejos API
	 */
		int iPressedMask = Button.readButtons();
		if (iPressedMask == iExpectedMask) {
			return true;
		}
		return false;
	}
}
