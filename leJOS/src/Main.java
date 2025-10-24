import hardware.ButtonSim;
import hardware.LCDSim;
import hardware.MotorSim;
import hardware.MotorSim;

public class Main {
    public static void main(String[] args) {
        LCDSim.clear();
        LCDSim.drawString("Hallo von leJOS Sim!", 0, 0);

        MotorSim.A.forward();
        MotorSim.B.backward();

        ButtonSim.waitForAnyPress();

        MotorSim.A.stop();
        MotorSim.B.stop();
        LCDSim.drawString("Ende.", 0, 1);
    }
}
