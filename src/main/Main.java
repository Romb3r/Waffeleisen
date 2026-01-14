// SWT - Waffeleisen by Olli & Soeren
// changed HelloWorld to Main 30.10.25 SG
// added PowerUp and Menu to structure 30.10.25 SG
package main;

import menu.ModeMenu;
import menu.SystemInfoMenu;
import defines.Define_Hardware;
import defines.Define_Mode;
import hardware.BaseButton;
import hardware.ColorSensor;
import hardware.Motor;
import hardware.Speaker;
import modes.AutoMode;
import modes.ManualMode;
import modes.CalibrationMode;


public class Main {
	public static void main(String[] args) {
		int iModus = 0;
		ColorSensor color_sensor = new ColorSensor();												// erzeuge Objekt der Klasse ColorSensor
		Motor motor = new Motor(Define_Hardware.iMotorSpeed, Define_Hardware.iMotorRotationAngle * Define_Hardware.iMotorNumRotations);			// erzeuge Objekt der Klasse Motor. Eine Umdrehung * Anzahl Umdrehungen
		Speaker speaker = new Speaker();                                                            // erzeuge Objekt der Klasse Speaker
		BaseButton base_btn = new BaseButton();														// erzeuge Objekt der Klasse BaseButton
		SystemInfoMenu CSysInfoMenu = new SystemInfoMenu();											// erzeuge Objekt der Klasse SystemInfoMenu
		ModeMenu CModeMenu = new ModeMenu();														// erzeuge Objekt der Klasse ModeMenu
		CSysInfoMenu.vZeigeTxt(CSysInfoMenu.sGetSysInfoString());									// Zeige im SystemInfoMenu Konstruktoraufruf erzeugten System Status String
		while(!CModeMenu.boGetExitProgramm()) {														// Laufe solange bis der Escape Button gedrueckt wurde
			CModeMenu.vShowMenu();																	// Starte Menuefuehrung zur Modusauswahl
			iModus = CModeMenu.iGetMode();															// Hole den ausgewaehlten Modus
			if(iModus == Define_Mode.iAutoMode) {													// \
				AutoMode CAutoMode = new AutoMode(color_sensor, motor, speaker, base_btn);			//  \
				CAutoMode.vRoutine();																//   \
			}																						//	  \
			else if(iModus == Define_Mode.iManualMode) {											//     \ 
				ManualMode CManualMode = new ManualMode(motor, base_btn, color_sensor);				//      Aufruf unterschiedlicher Konstruktoren und Routinen fuer unterschiedliche Modi
				CManualMode.vRoutine();																//     /
			}																						//    /
			else if(iModus == Define_Mode.iCalibMode) {												//   /
				CalibrationMode CCalibMode = new CalibrationMode(color_sensor, motor, base_btn);	//  /
				CCalibMode.vRoutine();																// /
			}
		}
	}
}
