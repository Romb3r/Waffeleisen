// SWT - Waffeleisen by Olli & Soeren
// added PowerUp 30.10.25 SG
// added PowerOutput 30.10.25 SG
 
import lejos.hardware.Button;
import lejos.hardware.Power;
import lejos.hardware.ev3.EV3;

public class PowerUp {

	// PowerUp gibt die Spannung des Akkus aus
	public static void PowerOutput(EV3 ev3)
	{
	    Power power = ev3.getPower();
	    float Voltage = power.getVoltage();
	    
	    if (Voltage > 8.2)
	    {
	      	System.out.println("Der Akku ist voll geladen --> " + Voltage + "V");
	    }
	    else if (Voltage > 6.9)
	    {
	      	System.out.println("Der Akku ist normal geladen --> " + Voltage + "V");
	    }
	    else
	    {
	       	System.out.println("Achtung! Spannung niedrig, Verhalten beeintraechtigt! Grenzwert 6,8V --> " + Voltage + "V");
	    }
	    Button.waitForAnyPress(); // Damit der Output nicht verschwindet     
	}

}
