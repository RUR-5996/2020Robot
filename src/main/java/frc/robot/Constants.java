package frc.robot;

public class Constants {

    //Limelight Constants
    public enum limelightMode {
        visionProcessing,
        camera
    }

    public static double ballCount = 3;

    //Encoder constants
    public static double leftDriveTicks = 0;
    public static double rightDriveTicks = 0;
    public static double leftDriveDist = 0;
    public static double rightDriveDist = 0;
    
    //ultrasonic constants
    public static double ultrasonicVoltsToDistance = 0.976; // volts/meter

    //limelight values
    public static double tx = 0;
    public static double ty = 0;
    public static double ta = 0;
    public static double tv = 0;
    public static double limelightPipeline;

    public static double intakeUltraDist = 0;
    public static double shooterUltraDist = 0;

    //autonomous drive stuff
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

    public static final String limelightName = "limelight-rur";

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