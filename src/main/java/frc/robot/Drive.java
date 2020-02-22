package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.PID.LimelightTurnPID;

public class Drive {

    int autoStep;
    RobotMap robotMap;
    Timer buttonPress;
    LimelightTurnPID limelightTurn;
    double errorL, errorR, outputL, outputR, lastErrorL, lastErrorR;

    public Drive(RobotMap robotMap) {
        this.robotMap = robotMap;
        limelightTurn = new LimelightTurnPID();
        buttonPress =  new Timer();
        buttonPress.start();
    }

    /**
     * 
     */
    public void periodic() {

        setDriveMode();

        if(Constants.driveMode.equals("manual")) {

            if(robotMap.leftJoystick.get() || robotMap.rightJoystick.get()) {
                fasterDrive();
            } else {
                drive();
            }

        } else if (Constants.driveMode.equals("assisted")) {
            assistedDrive();
        } else {
            System.out.println("Drive mode error");
        }
        
    }

    /**
     * Switches drive modes between manual and vision-assisted
     */
    public void setDriveMode() {

        if(robotMap.buttonB.get() && buttonPress.get() >= 0.25) {

            if(Constants.driveMode.equals("manual")) {
                Constants.driveMode = "assisted";
                //sensors.setLimelightMode(Constants.limelightMode.visionProcessingPower);
                limelightTurn.setTarget(0);
            } else {
                Constants.driveMode = "manual";
                //sensors.setLimelightMode(Constants.limelightMode.camera);
            }

            buttonPress.reset();

        }

    }

    /**
     * slower driving for regular operations
     */
    public void drive() {
        robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), 0.65 * robotMap.getRightX());
    }

    /**
     * faster driving for full-court driving
     */
    public void fasterDrive() {
        robotMap.drive.arcadeDrive(0.8 * robotMap.getLeftY(), 0.8 * robotMap.getRightX()); //Not more than 0.85!
    }

    /**
     * vision-assisted drive: Driver can operate fw/bw, turning is done by LL
     */
    public void assistedDrive() {
        robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), limelightTurn.pidGet());
    }

    //the magic happens here - autonomous driving, will be commented later after testing

    public void setupAuto() {
        autoStep = 0;
        lastErrorL = 0;
        lastErrorR = 0;
    }

    public void autoDrive() { 

        if(autoStep < Constants.leftPos.length)
        {
            errorL = Constants.leftPos[autoStep] - Constants.leftDriveDist;
            outputL = Constants.autokP * errorL + Constants.autokD * ((errorL - lastErrorL) / Constants.leftVel[autoStep]) + (Constants.autokV * Constants.leftVel[autoStep] + Constants.autokA * Constants.leftAcc[autoStep]);
            lastErrorL = errorL;

            errorR = Constants.rightPos[autoStep] - Constants.rightDriveDist;
            outputR = Constants.autokP * errorR + Constants.autokD * ((errorR - lastErrorR) / Constants.rightVel[autoStep]) + (Constants.autokV * Constants.rightVel[autoStep] + Constants.autokA * Constants.rightAcc[autoStep]);
            lastErrorR = errorR;
            autoStep++;

            SmartDashboard.putNumber("leftOutput", outputL);
            SmartDashboard.putNumber("rightOutput", outputR);
        }
        else
        {
            System.out.println("finished or empty array");
            outputL = 0;
            outputR = 0;
        }
        robotMap.drive.tankDrive(outputL, outputR);

    }

    public void autoDriveBack() {

        if(autoStep < Constants.leftPos.length)
        {
            errorL = Constants.leftPos[autoStep] - Constants.leftDriveDist;
            outputL = Constants.autokP * errorL + Constants.autokD * ((errorL - lastErrorL) / Constants.leftVel[autoStep]) + (Constants.autokV * Constants.leftVel[autoStep] + Constants.autokA * Constants.leftAcc[autoStep]);
            outputL = -outputL;
            lastErrorL = errorL;

            errorR = Constants.rightPos[autoStep] - Constants.rightDriveDist;
            outputR = Constants.autokP * errorR + Constants.autokD * ((errorR - lastErrorR) / Constants.rightVel[autoStep]) + (Constants.autokV * Constants.rightVel[autoStep] + Constants.autokA * Constants.rightAcc[autoStep]);
            outputR = -outputR;
            lastErrorR = errorR;
            autoStep++;

            SmartDashboard.putNumber("leftOutput", outputL);
            SmartDashboard.putNumber("rightOutput", outputR);
        }
        else
        {
            System.out.println("finished or empty array");
            outputL = 0;
            outputR = 0;
        }

        robotMap.drive.tankDrive(outputL, outputR);

    }
}