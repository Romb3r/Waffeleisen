// SWT - Waffeleisen by Olli & Soeren
// changed HelloWorld to Main 30.10.25 SG
// added PowerUp and Menu to structure 30.10.25 SG

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;


public class Main {
	public static void main(String[] args) {
        // === PowerUp - Sequenz ===
		
        // holt den aktuellen Brick für PowerUp
        EV3 ev3 = (EV3) BrickFinder.getDefault();
        
        // PowerOutput gibt den aktuellen Akku-Stand aus
        PowerUp.PowerOutput(ev3);
        
        // laesst den Bediener einen Auswahl ueber den Modus treffen 
        int AuswahlModus = Menu.showMenu();  
        
        // startet den Modus
        Modus.startup(AuswahlModus);
        
        
  
        

        
	}
}
