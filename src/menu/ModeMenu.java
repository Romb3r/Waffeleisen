package menu;
import display.LCD;
import lejos.hardware.Button;

public class ModeMenu extends LCD {						// diese Klasse erbt von der Klasse LCD
	// private Attribute
	private int iMode;									// integer zur Modus Speicherung (1 = manuell / 2 = auto / 4 = kalibrierung)
	private boolean boExitProgramm;						// Abbruchbedingung fuer main Schleife in Main.java
	
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
		this.vZeigeTxt("Modusauswahl:");					
		this.vZeigeTxt("oben -> manueller Betrieb");
		this.vZeigeTxt("mitte -> Automatikbetrieb");
		this.vZeigeTxt("unten -> Kalibrierung");
		
		this.vSetMode();												// Funktionsaufruf um den Modus auszuwaehlen
		
		switch(this.iMode)												// Switch zur Informationsausgabe auf dem Display
        {	
	        case Button.ID_UP: 
	        	this.vZeigeTxt("Taste: Pfeil oben"); 					
	        	this.vZeigeTxt("Modus Manuell gewaehlt!"); 				// - manueller Betrieb = Motor kann spaeter ueber Tasten angesteuert werden
	        	break;
	        		
	        case Button.ID_ENTER: 
	        	this.vZeigeTxt("Taste: Mitte "); 						// --> mittelere Taste
	        	this.vZeigeTxt("Modus Automatik gewaehlt!"); 			// - Automatikbetrieb = mach eine Waffel...
	        	break;
	        		
	        case Button.ID_ESCAPE: 							
	        	this.vZeigeTxt("Programm beenden... ");					// --> Escaoe Taste
	        	this.boExitProgramm = true;								// Breche das Hauptprogramm ab
	        	//Modus = 3; // - noch keine Verwendung
	        	break;
	        		
	        case Button.ID_DOWN: 
	        	this.vZeigeTxt("Taste: Pfeil unten"); 					
	        	this.vZeigeTxt("Modus Kalibrierung gewaehlt!"); 		// - Kalibrierung = Kalibiere Farbsensor um zwischen leeren Waffeleisen und rohem Teig zu unterscheiden 
	        	break;
	        		
	        default: 
	        	this.vZeigeTxt("Unbekannte Taste");
	        	break;
        }
		Button.waitForAnyPress();										// damit Text nicht vorzeitig verschwindet
	}
	
	public boolean boGetExitProgramm() {
		return this.boExitProgramm;
	}
}
