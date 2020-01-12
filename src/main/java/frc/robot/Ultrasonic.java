package frc.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Ultrasonic {

    private static final AnalogInput ultrasonic = new AnalogInput(Constants.ultrasonicChannel);
    public Ultrasonic() {

    }

    public static double getVoltage() {
        return ultrasonic.getVoltage();
    }

    public static double getDistance() {
        return getVoltage() * Constants.ultrasonicVoltsToDistance;
    }

    public static void report() {
        SmartDashboard.putNumber("Ultrasonic Distance", getDistance());
        SmartDashboard.putNumber("Ultrasonic Voltage", getVoltage());
    }
}