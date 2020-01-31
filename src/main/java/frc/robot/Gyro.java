package frc.robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class Gyro {

    private static AHRS ahrs;

    public Gyro() {
    }

    public static void gyroInit() {
        try{
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            DriverStation.reportError("Error instantiating navx-MXP" + e.getMessage(), true);
        }
    }

    public static double getAngle() {
        return ahrs.getAngle();
    }

    public static void resetGyro() {
        ahrs.reset();
    }
}