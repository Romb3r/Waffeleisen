// SWT - Waffeleisen by Olli & Soeren
// changed HelloWorld to Main 30.10.25 SG
// added PowerUp and Menu to structure 30.10.25 SG
package main;

import menu.ModeMenu;
import menu.SystemInfoMenu;
import defines.Define_Mode;
import modes.AutoMode;
import modes.ManualMode;

public class Main {
	public static void main(String[] args) {
		int iModus = 0;
		SystemInfoMenu CSysInfoMenu = new SystemInfoMenu();								// erzeuge Objekt der Klasse SystemInfoMenu
		ModeMenu CModeMenu = new ModeMenu();											// erzeuge Objekt der Klasse ModeMenu
		CSysInfoMenu.vZeigeTxt(CSysInfoMenu.sGetSysInfoString());						// Zeige im SystemInfoMenu Konstruktoraufruf erzeugten System Status String
		CModeMenu.vShowMenu();															// Starte Menüführung zur Modusauswahl
		iModus = CModeMenu.iGetMode();													// Hole den ausgewählten Modus
		if(iModus == Define_Mode.iAutoMode) {											// \
			AutoMode CAutoMode = new AutoMode();										//  \
			CAutoMode.vRoutine();														//   \
		}																				//	   Aufruf unterschiedlicher Konstruktoren und Routinen für unterschiedliche Modi
		else if(iModus == Define_Mode.iManualMode) {									//   /
			ManualMode CManualMode = new ManualMode();									//  /
			CManualMode.vRoutine();														// /
		}
	}			
}
