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
import frc.robot.PID.TurretTurnPID;
import frc.robot.PID.LimelightTurnPID;

/**
 * Add your docs here.
 */
public class Shooter {

    RobotMap robotMap;
    Timer shooterStartUp, buttonTimer;
    boolean turretHomed = false;
    boolean turretCalibrated = false;
    boolean turned = false;
    boolean override = false;

    TurretTurnPID turretTurn = new TurretTurnPID();
    LimelightTurnPID limelightTurn = new LimelightTurnPID();

    String turretMode = "encoder";
    //private double servoAngle = 0;

    public Shooter() {
        this.robotMap = RobotMap.getRobotMap();

        //Do we need the following line? - probably no
        //servoAngle = robotMap.ballStop.getAngle();

        turretTurn.turretTurnInit();
        limelightTurn.LimelightTurnPIDInit();

        shooterStartUp = new Timer();
        shooterStartUp.stop();

        buttonTimer = new Timer();
        buttonTimer.start();
    }

    /**
     * Periodic function running all the commands based on joystick input
     */
    public void periodic() {
        
        /*if(!turretHomed) {
            homeTurret();
        } else if(!turretCalibrated) {
            calibrateTurret();
        } else {
            setShooterSpeed();
            setTurretMode();

            if(turretMode.equals("limelight")) {
                setTurret();
            } else if(robotMap.controller.getPOV() != -1 || override){
                override = true;
                overrideTurret();
            } else {
                idleTurret();
            }*/

            manualTurret();

            if(robotMap.getLeftTrigger() >= 0.75 || robotMap.logitechTrigger.get()) {
                shoot();
                openShooter();
                mixBalls();
            } else if(robotMap.leftBumper.get() || robotMap.logitechTwo.get()) {
                releaseBall();
                //openShooter();
            } else {
                stopShooter();
                stopFeeder();
                //closeShooter();
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
        robotMap.shooterBottom.set(-Diagnostics.shooterSpeed); //Very important to keep the signs opposite to each other
    }

    /**
     * Spins shooter at full speed to release a stuck ball
     */
    public void releaseBall() {
        robotMap.shooterGroup.set(1);
    }

    /**
     * stops all motion on the shooter
     */
    public void stopShooter() {
        if(shooterStartUp.get() > 0.1) {
            shooterStartUp.reset();
            shooterStartUp.stop();
        }
        robotMap.shooterGroup.set(0);
        robotMap.mixer.set(0);
    }

    public void stopFeeder() {
        robotMap.feeder.set(0); //TODO consider a feeding sequence, available encoder
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

        /*if(shooterStartUp.get() >= 1.75) {
            robotMap.ballStop.setAngle(0);
        }*/

        if(shooterStartUp.get() >= 0.75) {
            robotMap.feeder.set(0.8); //TODO test ideal feeding speed and timing
        }

    }

    public void manualTurret() {
        robotMap.turretTurner.set(0.4 * robotMap.getLogitechX());
    }

    public void closeShooter() { //might not be needed
        //robotMap.ballStop.setAngle(68);
    }

    /**
     * Homes the turret to the limit switch and resets its encoder
     */
    public void homeTurret() {
        if(!robotMap.turretHome.get()) {
            robotMap.turretTurner.set(0.5);
        } else {
            robotMap.turretTurner.set(0);
            turretHomed = true;
            Robot.sensors.resetTurretEncoder();
        }
    }

    /**
     * Calibrates precisely the turret
     */
    public void calibrateTurret() { //TODO check the signs or u will break something! Also check the encoder steps
        turretTurn.setTarget(Constants.turretCalibrationPosition);
        if((!turretTurn.onTarget)&&(!turned)) {
            robotMap.turretTurner.set(turretTurn.pidGet());
        } else if(!robotMap.turretHome.get()) {
            turned = true;
            robotMap.turretTurner.set(0.25);
        } else if(robotMap.turretHome.get()&&turned) {
            robotMap.turretTurner.set(0);
            turretCalibrated = true;
            Robot.sensors.resetTurretEncoder();
        }
    }

    public void setTurret() {
        if(robotMap.turretHome.get() && limelightTurn.pidGet() < 0) { //TODO check the signs on this one
            robotMap.turretTurner.set(0);
        } else if(robotMap.turretLimit.get() && limelightTurn.pidGet() > 0) {
            robotMap.turretTurner.set(0);
        } else if(Diagnostics.targets > 0 && (!limelightTurn.onTarget())) {
            robotMap.turretTurner.set(limelightTurn.pidGet()); //TODO might need to get inverted
        } else {
            robotMap.turretTurner.set(0);
        }
    }

    public void idleTurret() {
        turretTurn.setTarget((Constants.maxTurretTicks / Constants.maxTurretAngle) * 90); //TODO rewrite the angle, assumes the starting position is -90 degs
        if(robotMap.turretHome.get() && turretTurn.pidGet() < 0) { //TODO check the signs on this one
            robotMap.turretTurner.set(0);
        } else if(robotMap.turretLimit.get() && turretTurn.pidGet() > 0) {
            robotMap.turretTurner.set(0);
        } else if(!turretTurn.onTarget) {
            robotMap.turretTurner.set(turretTurn.pidGet());
        } else {
            robotMap.turretTurner.set(0);
        }
    }

    public void overrideTurret() {
        turretTurn.setTarget((Constants.maxTurretTicks / Constants.maxTurretAngle) * (robotMap.controller.getPOV(0) + 90)); //TODO the 0 in getPOV might be unnecessary and check the angles
        if(robotMap.turretHome.get() && turretTurn.pidGet() < 0) { //TODO check the signs on this one
            robotMap.turretTurner.set(0);
        } else if(robotMap.turretLimit.get() && turretTurn.pidGet() > 0) {
            robotMap.turretTurner.set(0);
        } else if(!turretTurn.onTarget) {
            robotMap.turretTurner.set(turretTurn.pidGet());
        } else {
            robotMap.turretTurner.set(0);
        }
    }

    public void setTurretMode() {
        if(robotMap.buttonB.get() && buttonTimer.get() >= 0.5) {
            turretMode = "limelight";
            limelightTurn.setTarget(0);
            buttonTimer.reset();
            buttonTimer.start();
            override = false;
        } else if(robotMap.buttonX.get() && buttonTimer.get() >= 0.5) {
            turretMode = "encoder";
            buttonTimer.reset();
            buttonTimer.start();
            override = false;
        }
    }

    public void mixBalls() {
        robotMap.mixer.set(0.2); //TODO make sure that this thing doesnt't fly off!!!
    }
}