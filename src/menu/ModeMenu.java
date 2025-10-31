package menu;
import display.LCD;
import lejos.hardware.Button;

public class ModeMenu extends LCD {						// diese Klasse erbt von der Klasse LCD
	// private Attribute
	private int iMode;									// integer zur Modus Speicherung (1 = manuell / 2 = auto)
	
	// private Methoden
	private void vSetMode() {
	/*
	 * Setzt den Modus anhand welcher Knopf gedrückt wurde
	 */
		this.iMode = Button.waitForAnyPress();
	}
	
	// öffentliche Methoden
	public int iGetMode() {
	/*
	 * Gibt den aktuellen Modus an den Aufrufer zurück
	 */
		return this.iMode;
	}
	
	public void vShowMenu() {
	/*
	 * Zeigt die Auswahlmöglichkeiten der einzelnen Modi an.
	 * Erwartet dann einen Tastendruck um den Modus auszuwählen.
	 * Zeigt an welcher Modus gewählt wurde.
	 */
		this.vZeigeTxt("Bitte waehlen Sie einen Modus auf den Pfeiltasten.");					
		// wegen langem Text, scroll-MÃ¶glichkeit wÃ¤re hier optimal
		Button.waitForAnyPress();
		
		this.vZeigeTxt("oben -> manueller Betrieb");
		this.vZeigeTxt("mitte -> Automatikbetrieb");
		this.vZeigeTxt("unten -> Konfiguration");
		
		this.vSetMode();								// Funktionsaufruf um den Modus auszuwählen
		
		switch(this.iMode)
        {
	        case 0: 
	        	this.vZeigeTxt("Taste 0");
	        	//Modus = 0; // - noch keine Verwendung
	        	break;
	        		
	        case 1: 
	        	this.vZeigeTxt("Taste: Pfeil oben"); 	// --> Pfeil oben
	        	this.vZeigeTxt("Modus manueller Betrieb gewÃ¤hlt!"); 	// - manueller Betrieb = Motor kann später über Tasten angesteuert werden
	        	break;
	        		
	        case 2: 
	        	this.vZeigeTxt("Taste: Mitte "); 	// --> mittelere Taste
	        	this.vZeigeTxt("Modus Automatik gewÃ¤hlt. Bitte aufpassen beim erhitzen des Waffeleisens!"); 	// - Automatikbetrieb = mach eine Waffel...
	        	break;
	        		
	        case 3: 
	        	this.vZeigeTxt("Taste 3");
	        	//Modus = 3; // - noch keine Verwendung
	        	break;
	        		
	        case 4: 
	        	this.vZeigeTxt("Taste: Pfeil unten"); 	// --> Pfeil unten
	        	this.vZeigeTxt("Modus Konfiguration gewÃ¤hlt!"); 	// - Konfiguration kÃ¶nnte sowas wie ein "Probe"-Waffel sein. vllt stoppen wir die Zeit & man kann seinen BrÃ¤unungsgrad speichern.. 
	        	break;
	        		
	        case 5: 
	        	this.vZeigeTxt("Taste 5");
	        	break;
	        		
	        default: 
	        	this.vZeigeTxt("Unbekannte Taste");		// --> Pfeil rechts, Pfeil links und oben links 
	        	break;
        }
		Button.waitForAnyPress();	// damit Text nicht vorzeitig verschwindet
	}
}
