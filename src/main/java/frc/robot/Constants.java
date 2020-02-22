package frc.robot;

public class Constants {

    public static final int timeoutMs = 10;

    //Ultrasonic constants
    public static double ultrasonicVoltsToDistance = 100;
    public static int ultrasonicChannel = 0;

    //Drive constants
    public static String driveMode = "manual"; //manual or assisted

    //Limelight Constants
    public static double limelightDefault = 0;
    
    public static final String limelightTable = "limelight";

    public enum limelightMode {
        visionProcessingPower,
        visionProcessingLoading,
        camera
    }

    public static final double limelightHeight = 0.54; //meters
    public static final double targetHeight = 2.5; //meters
    public static final double limelightAngle = 10; //degrees

    public static double targets = 0;
    public static double xOffset = 0;
    public static double yOffset = 0;
    public static double area = 0;
    public static double distanceLL = 0;

    //shooter constants
    public static double shooterSpeed = 0.45;
    public static double shooterSpeedRatio = 0; //converts distance values from LL into shooter speed

    //intake constants
    public static double intakeSpeed = 0.75;
    public static double intakeFoldSpeed = 0.1;

    //Encoder constants
    public static double leftDriveTicks = 0;
    public static double rightDriveTicks = 0;
    public static double leftDriveDist = 0;
    public static double rightDriveDist = 0;
    
    //Servo constants
    public static final int servoInputChannel = 1;
    public static final double servoPosition = 0; //sets the initial position for the servo
    public static final double servoAngle = 0; //sets the initial angle for the servo

    //color constants
    public static String color = "";

    //pathfinder constants
    public static double[] leftPos = {};
    public static double[] leftVel = {};
    public static double[] leftAcc = {};
    public static double[] rightPos = {};
    public static double[] rightVel = {};
    public static double[] rightAcc = {};

    //DO NOT TOUCH UNLESS YOU KNOW WHAT R U DOING
    public static final double deadzone = 0.15;//could be 0.2
    public static final double leftDriveRatio = 0; //ticks per rot
    public static final double rightDriveRatio = 0; //ticks per rot
    public static final double driveWheelRadius = 0.1524; //meters

    public static final double turnKP = 0.0;
    public static final double turnKI = 0.0;
    public static final double turnKD = 0.0;
    public static final double turnSpeed = 0.65; //motor output multiplier of turn PID

    public static final double turnKPLL = 0.05;
    public static final double turnKILL = 0.0;
    public static final double turnKDLL = 0.0;
    public static final double turnSpeedLL = 0.5; //motor output multiplier of turn PID

    public static final double driveKP = 0.075;
    public static final double driveKI = 0.0;
    public static final double driveKD = 0.0;
    public static final double driveSpeedLL = 0.5; //motor output multiplier of turn PID

    public static final double autokP = 1.23;
    public static final double autokI = 0; //not being used
    public static final double autokD = 0.001; //minimal difference
    public static final double autokV = 1/1.3; //1/maxspeed
    public static final double autokA = 0; //0.12
    
}