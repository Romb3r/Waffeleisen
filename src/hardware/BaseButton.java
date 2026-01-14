package hardware;
import lejos.hardware.Button;

public class BaseButton {
	
	public boolean boButtonPressed(int iExpectedMask) {
	/*
	 * Ließt die Tasten aus und überprüft, ob die gedrückte Taste der erwarteten entspricht. Liefert dann true oder false zurück
	 */
		int iPressedMask = Button.readButtons();
		if (iPressedMask == iExpectedMask) {							// Vergleiche ob gedrückte Taste == erwartete Taste
			return true;
		}
		return false;
	}
	
	public boolean boButtonPressedBlocking(int iExpectedMask) {
		/*
		 * Ließt die Tasten aus. Überprüft, ob die gedrückte Taste der erwarteten entspricht. Liefert dann true oder false zurück.
		 * Die Funktion blockiert den weiteren Ablauf des Programms, bis eine Taste gedrückt wurde
		 */
		int iPressedMask = Button.waitForAnyPress();					// Diese Funktion blockiert bis eine Taste gedrückt wurde
		if (iPressedMask == iExpectedMask) {							// Vergleiche ob gedrückte Taste == erwartete Taste
			return true;
		}
		return false;
	}
	
	public boolean boButtonPressedBlockingTimeout(int iExpectedMask, int iTimeoutMS) {
		/*
		 * Ließt die Tasten aus. Überprüft, ob die gedrückte Taste der erwarteten entspricht. Liefert dann true oder false zurück.
		 * Die Funktion blockiert den weiteren Ablauf des Programms um den gegebenen Timeout, bis eine Taste gedrückt wurde
		 */
		int iPressedMask = Button.waitForAnyPress(iTimeoutMS);			// Diese Funktion blockiert bis eine Taste gedrückt wurde oder der Timeout erreicht wurde
		if (iPressedMask == iExpectedMask) {							// Vergleiche ob gedrückte Taste == erwartete Taste
			return true;
		}
		return false;
	}
}
