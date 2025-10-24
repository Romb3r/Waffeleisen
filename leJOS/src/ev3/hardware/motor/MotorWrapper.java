package ev3.hardware.motor;

import lejos.hardware.motor.Motor;


public class MotorWrapper {
    private Motor motor;

    public void setMotor(Motor motor) {
        this.motor = motor;
    }

    public Motor getMotor() {
        return motor;
    }


}
