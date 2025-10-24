package sim.hardware;

public class MotorSim {
    public static final MotorSim A = new MotorSim("A");
    public static final MotorSim B = new MotorSim("B");
    public static final MotorSim C = new MotorSim("C");

    private final String name;

    private MotorSim(String name) {
        this.name = name;
    }

    public void forward() {
        System.out.println("MotorSim " + name + " läuft vorwärts.");
    }

    public void backward() {
        System.out.println("MotorSim " + name + " läuft rückwärts.");
    }

    public void stop() {
        System.out.println("MotorSim " + name + " stoppt.");
    }
}
