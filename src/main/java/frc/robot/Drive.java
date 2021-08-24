package frc.robot;

import edu.wpi.first.wpilibj.Timer;
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

        //Dropped the intake ultrasonics, MUST BE CAREFUL AROUND WALLS

        /*if((Diagnostics.leftIntakeDist <= 40 || Diagnostics.rightIntakeDist <= 40) && Diagnostics.intakeDown) { //test if the resolution is good enough to see 20cm

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

        } else {*/

            if(Diagnostics.driveMode.equals("manual")) {

                if(robotMap.leftJoystick.get() || robotMap.rightJoystick.get()) {
                    fasterDrive();
                } else {
                    drive();
                }

            } else if (Diagnostics.driveMode.equals("assisted")) { //gutted the assisted drive thing, turning is done by turret, buttoning remains
                if(robotMap.leftJoystick.get() || robotMap.rightJoystick.get()) {
                    fasterDrive();
                } else {
                    drive();
                }
            } else {
                System.out.println("Drive mode error");
            }

        //}
        
    }

    /**
     * Switches drive modes between manual and vision-assisted
     */
    public void setDriveMode() {

        if((robotMap.buttonB.get() || robotMap.logitechFour.get()) && buttonPress.get() >= 0.25) {
                Diagnostics.driveMode = "assisted";
                limelightTurn.setTarget(0); //keep!

                buttonPress.reset();
                buttonPress.start();
                //Robot.climber.aim();
        }else if(robotMap.buttonX.get() && buttonPress.get() >= 0.25) {
                Diagnostics.driveMode = "manual";
                //Robot.climber.aimReset();

                buttonPress.reset();
                buttonPress.start();
        }
          
    }

    /**
     * slower driving for regular operations
     */
    public void drive() {
        robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), 0.65 * robotMap.getRightX()); //might get slower with people around!
    }

    /**
     * faster driving for full-court driving
     */
    public void fasterDrive() {
        robotMap.drive.arcadeDrive(0.8 * robotMap.getLeftY(), 0.8 * robotMap.getRightX()); //Not more than 0.85!
    }

    public void intakeProtectDrive() { //not in use ATM
        if(robotMap.getLeftY() > 0) {
            robotMap.drive.arcadeDrive(0, 0.65 * robotMap.getRightX());
        } else {
            robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), 0.65 * robotMap.getRightX());
        }
    }

    public void intakeProtectFasterDrive() { //not in use ATM
        if(robotMap.getLeftY() > 0) {
            robotMap.drive.arcadeDrive(0, 0.8 * robotMap.getRightX());
        } else {
            robotMap.drive.arcadeDrive(0.8 * robotMap.getLeftY(), 0.8 * robotMap.getRightX());
        }
    }

    public void intakeProtectAssistedDrive() { //not in use ATM
        if(robotMap.getLeftY() > 0) {
            robotMap.drive.arcadeDrive(0, -limelightTurn.pidGet());
        } else {
            robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), -limelightTurn.pidGet());
        }
    }

    /**
     * vision-assisted drive: Driver can operate fw/bw, turning is done by LL
     */
    public void assistedDrive() { //not in use ATM
        robotMap.drive.arcadeDrive(0.65 * robotMap.getLeftY(), -limelightTurn.pidGet());
    }

}