package menu;
import display.LCD;
import lejos.hardware.Button;

public class ModeMenu extends LCD {						// diese Klasse erbt von der Klasse LCD
	// private Attribute
	private int iMode;									// integer zur Modus Speicherung (1 = manuell / 2 = auto)
	private boolean boExitProgramm;
	
	// private Methoden
	private void vSetMode() {
	/*
	 * Setzt den Modus anhand welcher Knopf gedrueckt wurde
	 */
		this.iMode = Button.waitForAnyPress();
	}
	
	// Oeffentliche Methoden
	public int iGetMode() {
	/*
	 * Gibt den aktuellen Modus an den Aufrufer zurueck
	 */
		return this.iMode;
	}
	
	public void vShowMenu() {
	/*
	 * Zeigt die Auswahlmoeglichkeiten der einzelnen Modi an.
	 * Erwartet dann einen Tastendruck um den Modus auszuwaehlen.
	 * Zeigt an welcher Modus gewaehlt wurde.
	 */
		this.vZeigeTxt("Bitte waehlen Sie einen Modus auf den Pfeiltasten.");					
		// wegen langem Text, scroll-Moeglichkeit waere hier optimal
		Button.waitForAnyPress();
		
		this.vZeigeTxt("oben -> manueller Betrieb");
		this.vZeigeTxt("mitte -> Automatikbetrieb");
		this.vZeigeTxt("unten -> Kalibrierung");
		
		this.vSetMode();												// Funktionsaufruf um den Modus auszuwaehlen
		
		switch(this.iMode)
        {
	        case 0: 
	        	this.vZeigeTxt("Taste 0");
	        	//Modus = 0; // - noch keine Verwendung
	        	break;
	        		
	        case 1: 
	        	this.vZeigeTxt("Taste: Pfeil oben"); 					// --> Pfeil oben
	        	this.vZeigeTxt("Modus manueller Betrieb gewaehlt!"); 	// - manueller Betrieb = Motor kann spaeter ueber Tasten angesteuert werden
	        	break;
	        		
	        case 2: 
	        	this.vZeigeTxt("Taste: Mitte "); 						// --> mittelere Taste
	        	this.vZeigeTxt("Modus Automatik gewaehlt!"); 			// - Automatikbetrieb = mach eine Waffel...
	        	break;
	        		
	        case Button.ID_ESCAPE: 
	        	this.vZeigeTxt("Programm beenden... ");
	        	this.boExitProgramm = true;
	        	//Modus = 3; // - noch keine Verwendung
	        	break;
	        		
	        case Button.ID_DOWN: 
	        	this.vZeigeTxt("Taste: Pfeil unten"); 					// --> Pfeil unten
	        	this.vZeigeTxt("Modus Kalibrierung gewaehlt!"); 		// - Kalibrierung = Kalibiere Farbsensor um zwischen leeren Waffeleisen und rohem Teig zu unterscheiden 
	        	break;
	        		
	        case 5: 
	        	this.vZeigeTxt("Taste 5");
	        	break;
	        		
	        default: 
	        	this.vZeigeTxt("Unbekannte Taste");						// --> Pfeil rechts, Pfeil links und oben links 
	        	break;
        }
		Button.waitForAnyPress();										// damit Text nicht vorzeitig verschwindet
	}
	
	public boolean boGetExitProgramm() {
		return this.boExitProgramm;
	}
}
