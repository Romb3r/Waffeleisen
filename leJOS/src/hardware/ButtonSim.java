package hardware;
import lejos.hardware.Button;
import java.util.Scanner;

public class ButtonSim {
    public static void waitForAnyPress() {
        System.out.println("(Sim) Drücke Enter zum Fortfahren...");
        new Scanner(System.in).nextLine();
    }
}
