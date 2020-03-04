package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.PID.DrivePID;
import frc.robot.PID.LimelightTurnPID;
import frc.robot.PID.TurnPID;

public class Autonomous {

    RobotMap robotMap;
    DrivePID drivePID;
    TurnPID turnPID;
    LimelightTurnPID llturnPID;
    Timer autoTimer;
    SendableChooser autoChooser;
    boolean shooting, driving, turning, aiming, firstTurn, secondTurn, intaking;

    public Autonomous() { //Robot init
        robotMap = RobotMap.getRobotMap();

        drivePID = new DrivePID();
        turnPID = new TurnPID();
        llturnPID = new LimelightTurnPID();

        drivePID.DrivePIDInit();
        turnPID.turnPIDInit();
        llturnPID.LimelightTurnPIDInit();

        autoTimer = new Timer();
        autoTimer.stop();

        Diagnostics.shooterSpeed = Constants.autoShooterSpeed;

        autoChooser = new SendableChooser<>(); //mess, will be done by widget
        autoChooser.addOption("Shoot, drive, turn", "shootDriveTurn");
        autoChooser.addOption("Aim, shoot, drive, turn", "aimShootDriveTurn");
        autoChooser.addOption("Aim, shoot, turn, intake, shoot", "aimShootTurnIntakeShoot");

    }

    public void initCurrentAuto() { //autoChooser
        Robot.intake.moveIntake(true); //unfolds intake

        Diagnostics.autoMode = autoChooser.getSelected().toString(); //check returning values

        if(Diagnostics.autoMode.equals("shootDriveTurn")) {
            shooting = true;
            driving = false;
            turning = false;
            aiming = false;
            intaking = false;
        } else {
            shooting = false;
            driving = false;
            turning = false;
            aiming = true;
            intaking = false;
        }
    }

    public void shootDriveTurn() {

        if(autoTimer.get() <= 4 && shooting) {
            Robot.shooter.shoot();
            Robot.shooter.openShooter();
        }
        if(autoTimer.get() > 4 && shooting) {
            autoTimer.stop();
            autoTimer.reset();
            shooting = false;
            driving = true;
            Robot.sensors.resetEncoders();
        }
        if(driving && !drivePID.onTarget()) {
            drivePID.setTarget(-1); //tune the distance
            robotMap.drive.arcadeDrive(drivePID.pidGet(), 0);
        }
        if(driving && drivePID.onTarget()) {
            robotMap.drive.arcadeDrive(0, 0);
            driving = false;
            turning = true;
        }
        if(turning && !turnPID.onTarget()) {
            turnPID.setTarget(120);
            robotMap.drive.arcadeDrive(0, turnPID.pidGet());
        }
        if(turning && turnPID.onTarget()) {
            robotMap.drive.arcadeDrive(0, 0);
        }
        if(!shooting) {
            Robot.shooter.closeShooter();
            Robot.shooter.stop();
        }
        if(!intaking) {
            Robot.intake.stop();
        }

    }

    public void aimShootTurnIntakeShoot() {
        if(aiming && !llturnPID.onTarget()) {
            llturnPID.setTarget(0);
            robotMap.drive.arcadeDrive(0, llturnPID.pidGet());
        }
        if(aiming && llturnPID.onTarget()) {
            robotMap.drive.arcadeDrive(0, 0);
            aiming = false;
            shooting = true;
            firstTurn = true;
            autoTimer.reset();
            autoTimer.start();
        }
        if(shooting && autoTimer.get() <= 4) {
            Robot.shooter.shoot();
            Robot.shooter.openShooter();
        }
        if(shooting && autoTimer.get() > 4) {
            shooting = false;
            turning = true;
            autoTimer.stop();
            autoTimer.reset();
            turnPID.setTarget(180);

        }
        if(turning && firstTurn && !turnPID.onTarget()) {
            robotMap.drive.arcadeDrive(0, turnPID.pidGet());
        }
        if(turning && firstTurn && turnPID.onTarget()) {
            robotMap.drive.arcadeDrive(0, 0);
            firstTurn = false;
            turning = false;
            driving = true;
            intaking = true;
            drivePID.setTarget(8.3); //I think
            Robot.sensors.resetEncoders();
        }
        if(driving && !drivePID.onTarget()) {
            robotMap.drive.arcadeDrive(drivePID.pidGet(), 0);
            Robot.intake.spinIntake();
        }
        if(driving && drivePID.onTarget()) {
            robotMap.drive.arcadeDrive(0, 0);
            intaking = false;
            turning = true;
            driving = false;
            turnPID.setTarget(-16.25); //I think
        }
        if(turning && !firstTurn && !turnPID.onTarget()) {
            robotMap.drive.arcadeDrive(0, turnPID.pidGet());
        }
        if(turning && !firstTurn && turnPID.onTarget()) {
            robotMap.drive.arcadeDrive(0, 0);
            turning = false;
            shooting = true;
            Robot.shooter.setShooterSpeed();
            autoTimer.start();
        }
        if(!shooting) {
            Robot.shooter.closeShooter();
            Robot.shooter.stop();
        }
        if(!intaking) {
            Robot.intake.stop();
        }
    }

    

}