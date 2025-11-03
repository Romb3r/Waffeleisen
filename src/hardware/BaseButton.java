package hardware;
import lejos.hardware.Button;

public class BaseButton {
	
	public boolean boButtonPressed(int iExpectedMask) {
	/*
	 * Ließt die Tasten aus und überprüft, ob die gedrückte Taste der erwarteten entspricht. Liefert dann true oder false zurück
	 */
		int iPressedMask = Button.readButtons();
		if (iPressedMask == iExpectedMask) {
			return true;
		}
		return false;
	}
}
