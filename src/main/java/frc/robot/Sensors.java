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

    public Sensors() {
        robotMap = RobotMap.getRobotMap();
    }

    public void periodic() {

        setLimelightMode();

        limelightPeriodic();

        getDriveDist();

        reportAngle();

        report();

    }

    public void resetEncoders() {
        robotMap.climberLeft.setSelectedSensorPosition(0);
        robotMap.climberRight.setSelectedSensorPosition(0);
    }

    public void getDriveDist() {
        Diagnostics.leftDriveTicks = robotMap.climberLeft.getSelectedSensorPosition(0);
        Diagnostics.rightDriveTicks = robotMap.climberRight.getSelectedSensorPosition(0);

        Diagnostics.rightDriveDist = (Diagnostics.rightDriveTicks / Constants.rightDriveRatio) * 2 * Math.PI * Constants.driveWheelRadius;
        Diagnostics.leftDriveDist = (Diagnostics.leftDriveTicks / Constants.leftDriveRatio) * 2 * Math.PI * Constants.driveWheelRadius;
    }

    public void getTurretTicks() {
        Diagnostics.turretTicks = robotMap.turretEncoder.get();
    }

    public void resetTurretEncoder() {
        robotMap.turretEncoder.reset();
    }

    /**
     * Gyroscope initialization code.
     */
    public void gyroInit() {

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
    private void limelightPeriodic() {
        Diagnostics.skew = NetworkTableInstance.getDefault().getTable(Constants.limelightTable).getEntry("ts").getDouble(0);

        if(Diagnostics.skew >= -10) { //TODO find out what is this???
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
    public void setLimelightMode()  {

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
    private double getUltrasonicVoltage(AnalogInput ultrasonic) {
        return ultrasonic.getVoltage();
    }

    /**
     * Method for converting voltage to meters.
     * @return double distance in meters.
     */
    public double getUltrasonicDistance(AnalogInput ultrasonic) {
        return getUltrasonicVoltage(ultrasonic) * Constants.ultrasonicVoltsToDistance;
    }

    //Gyro
     /**
     * Getter method for accesing the current angle
     * @return angle in degrees
     */
    public double getAngle() {
        return ahrs.getAngle();
    }

    public void reportAngle() {
        Diagnostics.gyroAngle = ahrs.getAngle();
    }

    /**
     * Method for resetting the default angle on the gyro.
     * Sets current angle to 0.
     */
    public void resetGyro() {
        ahrs.reset();
    }


    /**
     * Method for specifying the speed multiplier of the robot.
     * If the Z axis of logitech controller is lowered, it is safe to let other people handle the robot
     */
    public void getExhibitionMode() {
        if(robotMap.getLogitechZ() >= 0.5) {
            Diagnostics.exhibitionMultiplier = 1;
        } else {
            Diagnostics.exhibitionMultiplier = 0.5;
        }
    }

    /**
     * Method for reporting to smart dashboard.
     */
    private void report() { //TODO put here the LL stream - pops up automatically??
        //SmartDashboard.putNumber("Ultrasonic Distance", getUltrasonicDistance());
        //SmartDashboard.putNumber("Ultrasonic Voltage", getUltrasonicVoltage());
        SmartDashboard.putNumber("Limelight targets", Diagnostics.targets);
        SmartDashboard.putNumber("Limelight xOffset", Diagnostics.xOffset);
        SmartDashboard.putNumber("Limelight yOffset", Diagnostics.yOffset);
        SmartDashboard.putNumber("Limelight area", Diagnostics.area);
        SmartDashboard.putNumber("Limelight skew", Diagnostics.skew);

        SmartDashboard.putNumber("Gyro Angle", getAngle());

        SmartDashboard.putNumber("Ultrasonic Intake Left Dist", getUltrasonicDistance(robotMap.ultrasonicIntakeLeft));
        SmartDashboard.putNumber("Ultrasonic Intake Right Dist", getUltrasonicDistance(robotMap.ultrasonicIntakeRight));

        Diagnostics.leftIntakeDist = getUltrasonicDistance(robotMap.ultrasonicIntakeLeft);
        Diagnostics.rightIntakeDist = getUltrasonicDistance(robotMap.ultrasonicIntakeRight);

        SmartDashboard.putNumber("shooter stator current", robotMap.shooterBottom.getStatorCurrent()); //test could be used to count balls
        SmartDashboard.putNumber("shooter supply current", robotMap.shooterBottom.getSupplyCurrent());

        SmartDashboard.putNumber("left drive distance", Diagnostics.leftDriveDist);
        SmartDashboard.putNumber("right drive distance", Diagnostics.rightDriveDist);

        SmartDashboard.putNumber("turret encoder ticks", Diagnostics.turretTicks);

        SmartDashboard.putBoolean("turret home", robotMap.turretHome.get());
        SmartDashboard.putBoolean("turret limit", robotMap.turretLimit.get());
        SmartDashboard.putBoolean("intake limit", robotMap.intakeCheck.get());

    }
}