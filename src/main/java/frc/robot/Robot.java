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
  public static Diagnostics diagnostics;
  public static TransferData transferData;
  public static Limelight limelight;

  /**
   * Initialization code.
   * Runs at start up.
   */
  @Override
  public void robotInit() {
    robotMap = RobotMap.getRobotMap();
    limelight = Limelight.getLimelight();
    drive = new Drive(robotMap);
    diagnostics = new Diagnostics(robotMap);
    transferData = new TransferData();
    limelight.limelightInit();
  }

  /**
   * Called periodically during runtime.
   */
  @Override
  public void robotPeriodic() {
    diagnostics.periodic();
    diagnostics.measureDriveAcc();
    diagnostics.getDriveSpeed();
  }

  /**
   * Autonomous initialization code.
   */
  @Override
  public void autonomousInit() {
    diagnostics.resetDriveEncoders();
    transferData.transfer();
    drive.setupAuto();
  }

  /**
   * Periodic autonomous code.
   */
  @Override
  public void autonomousPeriodic() {
    drive.autoDrive();
  }

  @Override
  /**
   * Periodic code, runs during teleop.
   */
  public void teleopPeriodic() {
    drive.drive();
  }

  @Override
  public void testPeriodic() {

  }
}
