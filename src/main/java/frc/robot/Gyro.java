package frc.robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class Gyro {

    private static AHRS ahrs;

    public Gyro() {
    }

    /**
     * Initialization code for gyro. Runs at start up.
     */
    public static void gyroInit() {
        try{
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            DriverStation.reportError("Error instantiating navx-MXP" + e.getMessage(), true);
        }
    }

    /**
     * Getter method for accesing the current angle
     * @return angle in degrees
     */
    public static double getAngle() {
        return ahrs.getAngle();
    }

    /**
     * Method for resetting the default angle on the gyro.
     * Sets current angle to 0.
     */
    public static void resetGyro() {
        ahrs.reset();
    }
}