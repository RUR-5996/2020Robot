package frc.robot;

public class Intake {

    RobotMap robotMap;

    public Intake(RobotMap robotMap) {
        this.robotMap = robotMap;
    }

    /**
     * Periodic function running all the commands based on joystick input
     */
    public void periodic() {

        if(robotMap.getRightTrigger() >= 0.75) {
            spinIntake();
        } else if(robotMap.rightBumper.get()) {
            releaseBall();
        } else {
            stop();
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

        if(robotMap.intakeLock.get() >= 0.9) {
            robotMap.intakeVictor.set(Constants.intakeFoldSpeed);
        } else {
            robotMap.intakeLock.set(1);
        }

    }

    /**
     * Folds the intake mechanism
     */
    public void foldIntake() {

        if(robotMap.intakeLock.get() >= 0.9) {
            robotMap.intakeVictor.set(-Constants.intakeFoldSpeed);
        } else {
            robotMap.intakeLock.set(1);
        }
        
    }

    /**
     * stops all motion
     */
    public void stop() {
        robotMap.intakeVictor.set(0);
    }

}