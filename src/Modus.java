
public class Modus {

	public static void startup(int AuswahlModus)
	{
		switch(AuswahlModus)
		{
			case 0: // Automatikbetrieb
				automatik();
				break;
				
			case 1: // manueller Betrieb
				manuell();
				break;
				
			case 2: // Konfiguration
				konfiguration();
				break;
				
			default:
				System.out.println("Fehler bei Modus Auswahl");
				break;
		}
	}

	private static void konfiguration()
	{
		// TODO Methoden für einen Test-Lauf um Parameter zu speichern
		
	}

	private static void manuell()
	{
		// TODO Methoden für manuellen Betrieb
		
	}

	private static void automatik()
	{
		// TODO Methoden für Automatikbetrieb
		
	}

	
}
