package hardware;

import lejos.hardware.Sound;
import java.io.File;

public class Speaker {
	
	// öffentliche Methoden
	public void vDoBeep() {
		/*
		 * Zwei Pipetoene ertoenen
		 */
		Sound.twoBeeps();
	}
}
