package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Class for Limelight.
 */
public class Limelight {

    //declarations
    private static Limelight limelight;
    private static NetworkTable table;
    private static NetworkTableEntry tv, tx, ty, ta;
    private static double targets, xOffset, yOffset, area;

    /**
     * Initializes and accesses Limelight object.
     * Used to pass Limelight function throughout the code.
     */
    public static Limelight getLimelight() {
        if(limelight == null) {
            limelight = new Limelight();
        }
        return limelight;
    }

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
     * Periodic function which updates values from network tables.
     */
    public static void limelightPeriodic() {
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
    public static void setMode(Constants.limelightMode mode)  {
        switch(mode) {
            case visionProcessing:
                table.getEntry("camMode").setNumber(0);
                table.getEntry("ledMode").setNumber(3);
                break;
            case camera:
                table.getEntry("camMode").setNumber(1);
                table.getEntry("ledMode").setNumber(1);
                break;
            default:
                break;
        }
    }
}