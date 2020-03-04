/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Shooter {

    RobotMap robotMap;
    Timer shooterStartUp;
    private double servoAngle = 0;

    public Shooter() {
        this.robotMap = RobotMap.getRobotMap();

        //Do we need the following line?
        //servoAngle = robotMap.ballStop.getAngle();

        shooterStartUp = new Timer();
        shooterStartUp.stop();
    }

    /**
     * Periodic function running all the commands based on joystick input
     */
    public void periodic() {

        setShooterSpeed();

        if(robotMap.getLeftTrigger() >= 0.75 || robotMap.logitechTrigger.get()) {
            shoot();
            openShooter();
        } else if(robotMap.leftBumper.get() || robotMap.logitechTwo.get()) {
            releaseBall();
            openShooter();
        } else {
            stop();
            closeShooter();
        }

    }

    /**
     * Shoots the ball
     */
    public void shoot() {
        if(shooterStartUp.get() <= 0.01) {
            shooterStartUp.start();
        }
        robotMap.shooterTop.set(Diagnostics.shooterSpeed);
        robotMap.shooterBottom.set(1.25*Diagnostics.shooterSpeed);
    }

    /**
     * Spins shooter at full speed to release a stuck ball
     */
    public void releaseBall() {
        robotMap.shooterGroup.set(1);
    }

    /**
     * stops all motion
     */
    public void stop() {
        if(shooterStartUp.get() > 0.1) {
            shooterStartUp.reset();
            shooterStartUp.stop();
        }
        robotMap.shooterGroup.set(0);
    }

    /**
     * Sets shooter speed according to distance from target
     */
    public void setShooterSpeed() {
        Diagnostics.shooterSpeed = SmartDashboard.getNumber("Set shooter speed", 0.45); //TODO switch back
        SmartDashboard.putNumber("Set shooter speed", Diagnostics.shooterSpeed);

        /*if(Diagnostics.skew >= -15) { //could be around -90, test
            Diagnostics.shooterSpeed = Diagnostics.distanceLL * Constants.shooterSpeedRatio;
        }*/
    }

    public void openShooter() {

        if(shooterStartUp.get() >= 1.75) {
            robotMap.ballStop.setAngle(0);
        }

    }

    public void closeShooter() {
        robotMap.ballStop.setAngle(55);
    }
}