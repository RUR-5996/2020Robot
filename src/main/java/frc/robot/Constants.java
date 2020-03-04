package frc.robot;

public class Constants {

    public static final int timeoutMs = 10;

    //Ultrasonic constants
    public static final double ultrasonicVoltsToDistance = 100;
    public static final double ultrasonicDistanceRatio = 0.976; //volts per meter

    //Limelight Constants
    public static final String limelightTable = "limelight";

    public enum limelightMode {
        visionProcessingPower,
        visionProcessingLoading,
        camera
    }

    public static final double limelightHeight = 0.54; //meters
    public static final double targetHeight = 2.5; //meters
    public static final double limelightAngle = 10; //degrees

    //Shooter constants
    public static final double shooterSpeedRatio = 0; //converts distance values from LL into shooter speed
    public static final double autoShooterSpeed = 45;

    //Intake constants
    public static final double intakeFoldSpeed = 0.2;
    public static final double intakeSpeed = 0.75;
    
    //Climber constants
    public static final double climberSpeed = 0.3;

    //Servo constants
    public static final int servoInputChannel = 1;
    public static final double servoPosition = 0; //sets the initial position for the servo
    public static final double servoAngle = 0; //sets the initial angle for the servo

    //DO NOT TOUCH UNLESS YOU KNOW WHAT R U DOING
    public static final double deadzone = 0.15;//could be 0.2
    public static final double leftDriveRatio = 0; //ticks per rot
    public static final double rightDriveRatio = 0; //ticks per rot
    public static final double driveWheelRadius = 0.1524; //meters

    public static final double turnKP = 0.0;
    public static final double turnKI = 0.0;
    public static final double turnKD = 0.0;
    public static final double turnSpeed = 0.65; //motor output multiplier of turn PID

    public static final double turnKPLL = 0.075;
    public static final double turnKILL = 0.0;
    public static final double turnKDLL = 0.0;
    public static final double turnSpeedLL = 0.5; //motor output multiplier of turn PID

    public static final double driveKP = 0.08;
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveSpeed = 0.5; //motor output multiplier of turn PID

    public static final double autokP = 1.23;
    public static final double autokI = 0; //not being used
    public static final double autokD = 0.001; //minimal difference
    public static final double autokV = 1/1.3; //1/maxspeed
    public static final double autokA = 0; //0.12
    
}