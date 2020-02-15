package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {

    private static final AnalogInput ultrasonic = new AnalogInput(Constants.ultrasonicChannel);
    private static AHRS ahrs;
    private static NetworkTable table;
    private static NetworkTableEntry tv, tx, ty, ta;
    private static double targets, xOffset, yOffset, area;


    public static void periodic() {
        limelightPeriodic();
        report();
    }


    //Initialization function
    /**
     * Initialization function to initialize and instantiate network table and table entries.
     */
    public static void limelightInit() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
        tv = table.getEntry("tv");
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
    }

    /**
     * Gyroscope initialization code.
     */
    public static void gyroInit() {
        try{
            ahrs = new AHRS(SPI.Port.kMXP);
        } catch(Exception e) {
            System.err.println(e.getMessage());
            DriverStation.reportError("Error instantiating navx-MXP" + e.getMessage(), true);
        }
    }

    //Limelight
    /**
     * Periodic function which updates values from network tables.
     */
    private static void limelightPeriodic() {
        targets = tv.getDouble(Constants.limelightDefault);
        xOffset = tx.getDouble(Constants.limelightDefault);
        yOffset = ty.getDouble(Constants.limelightDefault);
        area = ta.getDouble(Constants.limelightDefault);
    }

    /**
     * Getter method for number of targets.
     * @return double number of targets.
     */
    public static double getTargets() {
        return targets;
    }

    /**
     * Getter method for xOffset
     * @return double X axis offset in angles. -27° to 27°
     */
    public static double getXOffset() {
        return xOffset;
    }

    /**
     * Getter method for yOffset.
     * @return double Y axis offset in angles. -20.5° to 20.5°
     */
    public static double getYOffset() {
        return yOffset;
    }

    /**
     * Getter method for Target Area
     * @return double % of image area. 0 to 100%
     */
    public static double getArea() {
        return area;
    }

    /**
     * Method for setting the mode for Limelight. Either vision or camera.
     * @param mode enum for limelight camera mode.
     */
    public static void setLimelightMode(Constants.limelightMode mode)  {
        switch(mode) {
            case visionProcessingPower:
                table.getEntry("camMode").setNumber(0);
                table.getEntry("ledMode").setNumber(3);
                table.getEntry("pipeline").setNumber(0);
                break;
            case visionProcessingLoading:
                table.getEntry("camMode").setNumber(0);
                table.getEntry("ledMode").setNumber(3);
                table.getEntry("pipeline").setNumber(1);
                break;
            case camera:
                table.getEntry("camMode").setNumber(1);
                table.getEntry("ledMode").setNumber(1);
                break;
            default:
                break;
        }
    }

    //Ultrasonic
    /**
     * Getter method for getting raw voltage from ultrasonic.
     * @return Double raw voltage from ultrasonic, roughtly between 0 and 6V.
     */
    private static double getVoltage() {
        return ultrasonic.getVoltage();
    }

    /**
     * Method for converting voltage to meters.
     * @return double distance in meters.
     */
    public static double getDistance() {
        return getVoltage() * Constants.ultrasonicVoltsToDistance;
    }

    //Gyro
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

    /**
     * Method for reporting to smart dashboard.
     */
    private static void report() {
        SmartDashboard.putNumber("Ultrasonic Distance", getDistance());
        SmartDashboard.putNumber("Ultrasonic Voltage", getVoltage());
        SmartDashboard.putNumber("Limelight targets", targets);
        SmartDashboard.putNumber("Limelight xOffset", xOffset);
        SmartDashboard.putNumber("Limelight yOffset", yOffset);
        SmartDashboard.putNumber("Limelight area", area);
        SmartDashboard.putNumber("Gyro Angle", getAngle());
    }
}