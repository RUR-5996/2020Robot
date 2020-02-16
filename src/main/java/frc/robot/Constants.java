package frc.robot;

public class Constants {

    //Ultrasonic constants
    public static double ultrasonicVoltsToDistance = 100;
    public static int ultrasonicChannel = 0;

    //Limelight Constants
    public static double limelightDefault = 0;
    public enum limelightMode {
        visionProcessingPower,
        visionProcessingLoading,
        camera
    }

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
    public static final double turnspeed = 1; //motor output multiplier of turn PID

    public static final double autokP = 1.23;
    public static final double autokI = 0; //not being used
    public static final double autokD = 0.001; //minimal difference
    public static final double autokV = 1/1.3; //1/maxspeed
    public static final double autokA = 0; //0.12
    
}