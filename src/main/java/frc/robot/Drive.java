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

    public Drive() {

        robotMap = RobotMap.getRobotMap();

        limelightTurn = new LimelightTurnPID();

        limelightTurn.LimelightTurnPIDInit();

        buttonPress =  new Timer();
        buttonPress.start();

    }

    /**
     * 
     */
    public void periodic() {

        setDriveMode();

        if((Diagnostics.leftIntakeDist <= 40 || Diagnostics.rightIntakeDist <= 40) && Diagnostics.intakeDown) { //test if the resolution is good enough to see 20cm

            if(Diagnostics.driveMode.equals("manual")) {

                if(robotMap.leftJoystick.get() || robotMap.rightJoystick.get()) {
                    intakeProtectFasterDrive();
                } else {
                    intakeProtectDrive();
                }

            } else if (Diagnostics.driveMode.equals("assisted")) {
                intakeProtectAssistedDrive();
            } else {
                System.out.println("Drive mode error");
            }

        } else {

            if(Diagnostics.driveMode.equals("manual")) {

                if(robotMap.leftJoystick.get() || robotMap.rightJoystick.get()) {
                    fasterDrive();
                } else {
                    drive();
                }

            } else if (Diagnostics.driveMode.equals("assisted")) {
                assistedDrive();
            } else {
                System.out.println("Drive mode error");
            }

        }
        
    }

    /**
     * Switches drive modes between manual and vision-assisted
     */
    public void setDriveMode() {

        if(( robotMap.buttonB.get() || robotMap.logitechFour.get() || robotMap.buttonX.get() ) && buttonPress.get() >= 0.25) {

            if(Diagnostics.driveMode.equals("manual")) {
                Diagnostics.driveMode = "assisted";
                limelightTurn.setTarget(0);
                //Robot.climber.aim();
            } else {
                Diagnostics.driveMode = "manual";
                //Robot.climber.aimReset();
            }

            buttonPress.reset();
            buttonPress.start();

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

    public void intakeProtectDrive() {
        if(robotMap.getLeftY() > 0) {
            robotMap.drive.arcadeDrive(0, 0.65 * robotMap.getRightX());
        } else {
            robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), 0.65 * robotMap.getRightX());
        }
    }

    public void intakeProtectFasterDrive() {
        if(robotMap.getLeftY() > 0) {
            robotMap.drive.arcadeDrive(0, 0.8 * robotMap.getRightX());
        } else {
            robotMap.drive.arcadeDrive(0.8 * robotMap.getLeftY(), 0.8 * robotMap.getRightX());
        }
    }

    public void intakeProtectAssistedDrive() {
        if(robotMap.getLeftY() > 0) {
            robotMap.drive.arcadeDrive(0, -limelightTurn.pidGet());
        } else {
            robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), -limelightTurn.pidGet());
        }
    }

    /**
     * vision-assisted drive: Driver can operate fw/bw, turning is done by LL
     */
    public void assistedDrive() {
        robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), -limelightTurn.pidGet());
    }




    //the magic happens here - autonomous driving, will be commented later after testing

    public void setupAuto() {
        autoStep = 0;
        lastErrorL = 0;
        lastErrorR = 0;
    }

    public void autoDrive() { 

        if(autoStep < Diagnostics.leftPos.length)
        {
            errorL = Diagnostics.leftPos[autoStep] - Diagnostics.leftDriveDist;
            outputL = Constants.autokP * errorL + Constants.autokD * ((errorL - lastErrorL) / Diagnostics.leftVel[autoStep]) + (Constants.autokV * Diagnostics.leftVel[autoStep] + Constants.autokA * Diagnostics.leftAcc[autoStep]);
            lastErrorL = errorL;

            errorR = Diagnostics.rightPos[autoStep] - Diagnostics.rightDriveDist;
            outputR = Constants.autokP * errorR + Constants.autokD * ((errorR - lastErrorR) / Diagnostics.rightVel[autoStep]) + (Constants.autokV * Diagnostics.rightVel[autoStep] + Constants.autokA * Diagnostics.rightAcc[autoStep]);
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

        if(autoStep < Diagnostics.leftPos.length)
        {
            errorL = Diagnostics.leftPos[autoStep] - Diagnostics.leftDriveDist;
            outputL = Constants.autokP * errorL + Constants.autokD * ((errorL - lastErrorL) / Diagnostics.leftVel[autoStep]) + (Constants.autokV * Diagnostics.leftVel[autoStep] + Constants.autokA * Diagnostics.leftAcc[autoStep]);
            outputL = -outputL;
            lastErrorL = errorL;

            errorR = Diagnostics.rightPos[autoStep] - Diagnostics.rightDriveDist;
            outputR = Constants.autokP * errorR + Constants.autokD * ((errorR - lastErrorR) / Diagnostics.rightVel[autoStep]) + (Constants.autokV * Diagnostics.rightVel[autoStep] + Constants.autokA * Diagnostics.rightAcc[autoStep]);
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