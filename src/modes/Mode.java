package modes;

import hardware.BaseButton;
import hardware.ColorSensor;
import hardware.Motor;

public class Mode {
	// geschuetzte Attribute
	protected int iID;					// ID des Modus
	protected String sName;				// Name des Modus
	protected ColorSensor color_sensor;
	protected Motor motor;
	protected BaseButton base_btn;
}


