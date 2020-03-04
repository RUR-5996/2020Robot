/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static RobotMap robotMap;
  public static Drive drive;
  public static Shooter shooter;
  public static Intake intake;
  public static Climber climber;
  public static Sensors sensors;
  public static Diagnostics diagnostics;
  public static TransferData transferData;

  /**
   * Initialization code.
   * Runs at start up.
   */
  @Override
  public void robotInit() {

    robotMap = RobotMap.getRobotMap();

    drive = new Drive();

    intake = new Intake();

    climber = new Climber();

    shooter = new Shooter();

    sensors = new Sensors();

    transferData = new TransferData();

    sensors.gyroInit();

    sensors.resetEncoders();

  }

  /**
   * Called periodically during runtime.
   */
  @Override
  public void robotPeriodic() {

    sensors.periodic();

  }

  /**
   * Autonomous initialization code.
   */
  @Override
  public void autonomousInit() {
    
    sensors.resetEncoders();

  }

  /**
   * Periodic autonomous code.
   */
  @Override
  public void autonomousPeriodic() {


    
  }

  /**
   * Periodic code, runs during teleop.
   */
  @Override
  public void teleopPeriodic() {

    drive.periodic();

    shooter.periodic();

    intake.periodic();

    climber.periodic();
    
  }

  @Override
  public void testPeriodic() {

  }
}
