import ev3.hardware.ButtonWrapper;
import ev3.hardware.motor.MotorWrapper;
import lejos.hardware.motor.Motor;
import sim.hardware.ButtonSim;
import sim.hardware.LCDSim;
import sim.hardware.MotorSim;
import ev3.hardware.ButtonWrapper;

public class Main {

    public static void main(String[] args) {
        //---- begin simulation ----
        LCDSim.clear();
        LCDSim.drawString("Hallo von leJOS Sim!", 0, 0);

        MotorSim.A.forward();
        MotorSim.B.backward();

        ButtonSim.waitForAnyPress();

        MotorSim.A.stop();
        MotorSim.B.stop();
        LCDSim.drawString("Ende.", 0, 1);

        //---- end simulation ----

        ButtonWrapper bWrapper = new ButtonWrapper();
        // Button button1 = bWrapper.getButton();
        MotorWrapper mWrapper = new MotorWrapper();
        Motor motor1 = mWrapper.getMotor();
    }
}
