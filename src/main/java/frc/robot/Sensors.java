package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sensors {

    private static AHRS ahrs;
    private static RobotMap robotMap;
    private static double targets, xOffset, yOffset, area, distance;
    //TODO move to RobotMap after testing
    private static final AnalogInput ultrasonicIntakeLeft = new AnalogInput(0);
    private static final AnalogInput ultrasonicIntakeRight = new AnalogInput(1);

    public Sensors() {
        robotMap = RobotMap.getRobotMap();
    }

    public static void periodic() {

        setLimelightMode();

        limelightPeriodic();

        getDriveDist();

        report();

    }

    public static void resetEncoders() {
        robotMap.climberLeft.setSelectedSensorPosition(0);
        robotMap.climberRight.setSelectedSensorPosition(0);
    }

    public static void getDriveDist() {
        Diagnostics.leftDriveTicks = robotMap.climberLeft.getSelectedSensorPosition(0);
        Diagnostics.rightDriveTicks = robotMap.climberRight.getSelectedSensorPosition(0);

        Diagnostics.rightDriveDist = (Diagnostics.rightDriveTicks / Constants.rightDriveRatio) * 2 * Math.PI * Constants.driveWheelRadius;
        Diagnostics.leftDriveDist = (Diagnostics.leftDriveTicks / Constants.leftDriveRatio) * 2 * Math.PI * Constants.driveWheelRadius;
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
        Diagnostics.skew = NetworkTableInstance.getDefault().getTable(Constants.limelightTable).getEntry("ts").getDouble(0);

        if(Diagnostics.skew >= -10) {
            Diagnostics.targets = NetworkTableInstance.getDefault().getTable(Constants.limelightTable).getEntry("tv").getDouble(0);
            Diagnostics.xOffset = NetworkTableInstance.getDefault().getTable(Constants.limelightTable).getEntry("tx").getDouble(0);
            Diagnostics.yOffset = NetworkTableInstance.getDefault().getTable(Constants.limelightTable).getEntry("ty").getDouble(0);
            Diagnostics.area = NetworkTableInstance.getDefault().getTable(Constants.limelightTable).getEntry("ta").getDouble(0);
            Diagnostics.distanceLL = (Constants.targetHeight - Constants.limelightHeight) / Math.tan((Constants.limelightAngle + Diagnostics.yOffset) * (Math.PI / 180));
        }
    }

    /**
     * Method for setting the mode for Limelight. Either vision or camera.
     * @param mode enum for limelight camera mode.
     */
    public static void setLimelightMode()  {

        if(robotMap.buttonX.get()) {
            NetworkTableInstance.getDefault().getTable(Constants.limelightTable).getEntry("pipeline").setNumber(2);
        } else if(robotMap.buttonB.get()) {
            NetworkTableInstance.getDefault().getTable(Constants.limelightTable).getEntry("pipeline").setNumber(0);
        }

    }

    //Ultrasonic
    /**
     * Getter method for getting raw voltage from ultrasonic.
     * @return Double raw voltage from ultrasonic, roughtly between 0 and 6V.
     */
    private static double getUltrasonicVoltage(AnalogInput ultrasonic) {
        return ultrasonic.getVoltage();
    }

    /**
     * Method for converting voltage to meters.
     * @return double distance in meters.
     */
    public static double getUltrasonicDistance(AnalogInput ultrasonic) {
        return getUltrasonicVoltage(ultrasonic) * Constants.ultrasonicVoltsToDistance;
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
        //SmartDashboard.putNumber("Ultrasonic Distance", getUltrasonicDistance());
        //SmartDashboard.putNumber("Ultrasonic Voltage", getUltrasonicVoltage());
        SmartDashboard.putNumber("Limelight targets", targets);
        SmartDashboard.putNumber("Limelight xOffset", xOffset);
        SmartDashboard.putNumber("Limelight yOffset", yOffset);
        SmartDashboard.putNumber("Limelight area", area);
        SmartDashboard.putNumber("Gyro Angle", getAngle());
        SmartDashboard.putBoolean("Shooter In Range", distance <= 11);

        SmartDashboard.putNumber("Ultrasonic Intake Left Dist", getUltrasonicDistance(ultrasonicIntakeLeft));
        SmartDashboard.putNumber("Ultrasonic Intake Right Dist", getUltrasonicDistance(ultrasonicIntakeRight));

        Diagnostics.leftIntakeDist = getUltrasonicDistance(ultrasonicIntakeLeft);
        Diagnostics.rightIntakeDist = getUltrasonicDistance(ultrasonicIntakeRight);

        SmartDashboard.putNumber("shooter stator current", robotMap.shooterBottom.getStatorCurrent()); //test could be used to count balls
        SmartDashboard.putNumber("shooter supply current", robotMap.shooterBottom.getSupplyCurrent());

        SmartDashboard.putNumber("touchless encoder", robotMap.touchlessAim.getVoltage());

        SmartDashboard.putNumber("left drive distance", Diagnostics.leftDriveDist);
        SmartDashboard.putNumber("right drive distance", Diagnostics.rightDriveDist);

        SmartDashboard.putNumber("skew", Diagnostics.skew);
    }
}