

import lejos.hardware.Button;

public class Menu {

	// zeigt Menu und laesst eine Auswahl treffen
	public static int showMenu()
	{
		int ButtonID;
		int Modus;
		System.out.println("Bitte waehlen Sie einen Modus auf den Pfeiltasten.");
		// wegen langem Text, scroll-Möglichkeit wäre hier optimal
		Button.waitForAnyPress();
		
		
		System.out.println("oben -> manueller Betrieb");
		System.out.println("mitte -> Automatikbetrieb");
		System.out.println("unten -> Konfiguration");
		ButtonID = Button.waitForAnyPress();
		
		Modus = 0;
		switch(ButtonID)
        {
	        case 0: System.out.println("Taste 0");
	        		//Modus = 0; // - noch keine Verwendung
	        		break;
	        		
	        case 1: System.out.println("Taste: Pfeil oben"); // --> Pfeil oben
	        		Modus = 1; // - manueller Betrieb = Motor kann angesteuert werden oder Sensor gibt aktuellen Stand der Leuchtmittel aus
	        		System.out.println("Modus manueller Betrieb gewählt!");
	        		break;
	        		
	        case 2: System.out.println("Taste: Mitte "); // --> mittelere Taste
	        		Modus = 2; // - Automatikbetrieb = mach eine Waffel...
	        		System.out.println("Modus Automatik gewählt. Bitte aufpassen beim erhitzen des Waffeleisens!");
	        		break;
	        		
	        case 3: System.out.println("Taste 3");
	        		//Modus = 3; // - noch keine Verwendung
	        		break;
	        		
	        case 4: System.out.println("Taste: Pfeil unten"); // --> Pfeil unten
	        		Modus = 4; // - Konfiguration könnte sowas wie ein "Probe"-Waffel sein. vllt stoppen wir die Zeit & man kann seinen Bräunungsgrad speichern.. 
	        		System.out.println("Modus Konfiguration gewählt!");
	        		break;
	        		
	        case 5: System.out.println("Taste 5");
	        		Modus = 5;
	        		break;
	        		
	        default: System.out.println("Taste 6???"); // --> Pfeil rechts, Pfeil links und oben links 
	        		break;
        }
		// damit Text nicht vorzeitig verschwindet
		Button.waitForAnyPress();
		return Modus;
	}

}
