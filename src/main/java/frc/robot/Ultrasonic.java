package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Ultrasonic {

    private static final AnalogInput ultrasonic = new AnalogInput(Constants.ultrasonicChannel);
    public Ultrasonic() {

    }

    /**
     * Getter method for getting raw voltage from ultrasonic.
     * @return Double raw voltage from ultrasonic, roughtly between 0 and 6V.
     */
    public static double getVoltage() {
        return ultrasonic.getVoltage();
    }

    /**
     * Method for converting voltage to meters.
     * @return
     */
    public static double getDistance() {
        return getVoltage() * Constants.ultrasonicVoltsToDistance;
    }

    public static void report() {
        SmartDashboard.putNumber("Ultrasonic Distance", getDistance());
        SmartDashboard.putNumber("Ultrasonic Voltage", getVoltage());
    }
}