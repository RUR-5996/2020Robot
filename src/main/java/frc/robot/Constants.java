package frc.robot;

public class Constants {

    //Limelight Constants
    public static double limelightDefault = 0;
    public enum limelightMode {
        visionProcessing,
        camera
    }

    //Encoder constants
    public static double leftDriveTicks = 0;
    public static double rightDriveTicks = 0;
    public static double leftDriveDist = 0;
    public static double rightDriveDist = 0;
    
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

    public static final double autokP = 1.23;
    public static final double autokI = 0; //not being used
    public static final double autokD = 0.001; //minimal difference
    public static final double autokV = 1/1.3; //1/maxspeed
    public static final double autokA = 0; //0.12
    
}