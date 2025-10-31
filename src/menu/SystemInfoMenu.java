package menu;
import lejos.hardware.Power;
import lejos.hardware.ev3.EV3;
import lejos.hardware.BrickFinder;
import display.LCD;

public class SystemInfoMenu extends LCD {				// Diese Klasse erbt von der LCD Klasse
	// private Attribute
	private EV3 ev3;
	private float fVoltage;
	private Power CPower;
	private String sSysPowerState;
	
	// private Methoden
	private void vBuildPowerStateString() {
	/*
	 * Baut abhängig von der Spannung des EV3 einen Systeminfo String zusammen und speichert diesen in einem Attribut
	 */
		this.fVoltage = this.fGetVoltage();				// fülle fVoltage
	    if (this.fVoltage > 8.2)
	    {
	      	this.sSysPowerState = "Der Akku ist voll geladen --> " + this.fVoltage + "V";			// Wenn Spannung über 8.2 Volt
	    }
	    else if (this.fVoltage > 6.9)
	    {
	    	this.sSysPowerState = "Der Akku ist normal geladen --> " + this.fVoltage + "V";			// Wenn Akkuladung ausreicht
	    }
	    else
	    {
	    	this.sSysPowerState = "Achtung! Spannung niedrig, Verhalten beeintraechtigt! Grenzwert 6,8V --> " + this.fVoltage + "V";		// Wenn zu wenig Spannung
	    }
	}
	
	// Konstruktoren
	public SystemInfoMenu() {
	/*
	 * Standardkonstruktor SystemInfoMenu Klasse
	 * Füllt private Attribute und ruft die Funktion auf um den Systeminfo String zu bauen
	 */
		this.ev3 = (EV3)BrickFinder.getDefault();		// fülle ev3 Objekt
		this.CPower = ev3.getPower();					// fülle CPower Objekt
		this.vBuildPowerStateString();					// Baue Status String
	}
	
	// öffentliche Methoden
	public float fGetVoltage() {
	/*
	 * Gibt die aktuelle Spannung des EV3 zurück
	 */
		return this.CPower.getVoltage();
	}
	
	public String sGetSysInfoString() {
	/*
	 * Gibt den Systeminfo String zurück
	 */
		return this.sSysPowerState;
	}
}
