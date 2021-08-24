package frc.robot;

public class Diagnostics {

    public static double exhibitionMultiplier = 1;

    //Intake
    public static boolean intakeDown = false;

    //LimeLight
    public static double targets = 0;
    public static double xOffset = 0;
    public static double yOffset = 0;
    public static double area = 0;
    public static double skew = 0;
    public static double distanceLL = 0;

    //Shooter
    public static double shooterSpeed = 0.92; //Should be in constants, ignore it for now
    public static int turretTicks = 0;

    //Drive
    public static double leftDriveTicks = 0;
    public static double rightDriveTicks = 0;
    public static double leftDriveDist = 0;
    public static double rightDriveDist = 0;
    public static String driveMode = "manual"; //manual or assisted
    public static double gyroAngle = 0;

    //Climber
    public static boolean climberUp = false;
    public static boolean climberDown = true;
    public static boolean climberAiming = false;

    //Color sensor
    public static String color = "";

    //Ultrasonic
    public static double leftIntakeDist = 0; //will be moved to Diagnostics
    public static double rightIntakeDist = 0; //will be moved to Diagnostics

    //Autonomous
    public static String autoMode = "";

    //pathfinder
    public static double[] leftPos = {};
    public static double[] leftVel = {};
    public static double[] leftAcc = {};
    public static double[] rightPos = {};
    public static double[] rightVel = {};
    public static double[] rightAcc = {};

}