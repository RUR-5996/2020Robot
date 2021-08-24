package frc.robot;

import edu.wpi.first.wpilibj.Timer;

//TODO this shit is too complex to be rewriten

public class Intake {

    RobotMap robotMap;
    boolean ratchetteEngaged, intakeDown;
    Timer buttonTimer, foldTimer;

    public Intake() {

        robotMap = RobotMap.getRobotMap();

        ratchetteEngaged = false;
        intakeDown = false;

        buttonTimer = new Timer();
        buttonTimer.start();

        foldTimer = new Timer();
        foldTimer.stop();
    }

    /**
     * Periodic function running all the commands based on joystick input
     */
    public void periodic() {

        if(robotMap.buttonY.get() && buttonTimer.get() > 0.2) {
            moveIntake(true);
        }
        
        if(!ratchetteEngaged) {

            if(robotMap.getRightTrigger() >= 0.75) {
                spinIntake();
            } else if(robotMap.rightBumper.get()) {
                releaseBall();
            } else {
                stop();
            }

        } else {
            moveIntake(false);
        }

    }
    
    /**
     * Spins intake at a constant speed to intake balls
     */
    public void spinIntake() {
        robotMap.intakeVictor.set(Constants.intakeSpeed);

        /*if(robotMap.intakeLock.get() <= 0.1) {
            robotMap.intakeVictor.set(Constants.intakeSpeed);
        } else {
            robotMap.intakeLock.set(1);
        }*/
    }

    /**
     * Spins out a ball if it gets stuck
     */
    public void releaseBall() {
        robotMap.intakeVictor.set(-1);
    }

    /**
     * Unfolds the intake mechanism
     */
    public void unfoldIntake() {

        if(robotMap.intakeLock.getAngle() >= 70) {
            robotMap.intakeVictor.set(Constants.intakeFoldSpeed/2);
        } else {
            robotMap.intakeLock.setAngle(75);
        }

    }

    /**
     * Folds the intake mechanism
     */
    public void foldIntake() {

        boolean intakeDownLocal = robotMap.intakeCheck.get(); //I deleted !

        robotMap.intakeLock.setAngle(75);

        if(robotMap.intakeLock.getAngle() >= 70 && intakeDownLocal) { //I deleted !
            robotMap.intakeVictor.set(-Constants.intakeFoldSpeed);
        } else if(!intakeDownLocal) { //I deleted !
            robotMap.intakeVictor.set(0);
            releaseServo();
        } else {
            robotMap.intakeLock.setAngle(75);
        }
        
    }

    public void moveIntake(boolean start) {

        if(start) {
            foldTimer.reset();
            foldTimer.start();
            ratchetteEngaged = true;
            intakeDown = robotMap.intakeCheck.get();
        }

        if((!intakeDown)&&foldTimer.get() <= 1&&ratchetteEngaged) {
            unfoldIntake();
            Diagnostics.intakeDown = true;
        } else if(intakeDown&&robotMap.intakeCheck.get()&&ratchetteEngaged) {
            foldIntake();
            Diagnostics.intakeDown = false;
        } else if(!intakeDown&&foldTimer.get() > 0.125){
            releaseServo();
        } else {
            releaseServo();
            stop();
        }
    
    }

    /**
     * releases servo
     */
    public void releaseServo() {
        if(robotMap.intakeLock.getAngle() <= 30) {
            ratchetteEngaged = false;
            robotMap.intakeLock.set(robotMap.intakeLock.get());
            System.out.println(robotMap.intakeLock.getAngle());
        } else {
            robotMap.intakeLock.setAngle(30);
        }
    }

    /**
     * stops all motion
     */
    public void stop() {
        robotMap.intakeVictor.set(0);
    }

}