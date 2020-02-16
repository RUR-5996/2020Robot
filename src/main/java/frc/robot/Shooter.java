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

    public Shooter() {

    }

    public void shoot() {
        RobotMap.shooterGroup.set(0.6);
    }

    public void stop() {
        RobotMap.shooterGroup.set(0);
    }
}