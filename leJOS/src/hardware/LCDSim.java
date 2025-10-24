package hardware;

public class LCDSim {
    public static void drawString(String text, int x, int y) {
        System.out.println("LCDSim [" + x + "," + y + "]: " + text);
    }

    public static void clear() {
        System.out.println("LCDSim gelöscht.");
    }
}
