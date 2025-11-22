package hardware;

import lejos.hardware.Sound;
import java.io.File;

public class Speaker {
	// private Attribute
	private Sound CSound;
	File file = new File("D:\\workspace\\Waffeleisen\\resources");
	
	// öffentliche Methoden
	public void vDoBeep() {
		// TODO implementieren
	}
	
	public void vPlaySong() {
		Sound.playSample(file, 100);
	}
}
