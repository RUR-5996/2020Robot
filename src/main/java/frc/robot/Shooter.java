/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Shooter {

    RobotMap robotMap;

    public Shooter(RobotMap robotMap) {
        this.robotMap = robotMap;
    }

    /**
     * Periodic function running all the commands based on joystick input
     */
    public void periodic() {

        if(robotMap.getLeftTrigger() >= 0.75) {
            shoot();
        } else if(robotMap.leftBumper.get()) {
            releaseBall();
        } else {
            stop();
        }

    }

    /**
     * Shoots the ball
     */
    public void shoot() {
        robotMap.shooterGroup.set(Constants.shooterSpeed);
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
        robotMap.shooterGroup.set(0);
    }

    /**
     * Sets shooter speed according to distance from target
     */
    public void setShooterSpeed() {
        Constants.shooterSpeed = Constants.distanceLL * Constants.shooterSpeedRatio;
    }
}