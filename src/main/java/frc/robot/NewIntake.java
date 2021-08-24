package frc.robot;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Timer;

public class NewIntake {

    //TODO put lifting victor in coast mode - if issues, change to brake when lifted

    RobotMap robotMap;
    boolean intakeDown;
    Timer buttonTimer, foldTimer;

    public NewIntake() {
        robotMap = RobotMap.getRobotMap();

        intakeDown = false;

        buttonTimer = new Timer();
        buttonTimer.start();

        foldTimer = new Timer();
        foldTimer.stop();

    }

    public void periodic() {

        if(Diagnostics.exhibitionMultiplier < 1) {
            stop();
        } else {

            if(robotMap.buttonY.get() && buttonTimer.get() >= 0.25 && intakeDown) {
                liftIntake();
                buttonTimer.reset();
            } else if(robotMap.buttonY.get() && buttonTimer.get() >= 0.25 && !intakeDown) {
                lowerIntake();
                buttonTimer.reset();
            }

            if(robotMap.getRightTrigger() >= 0.75) {
                intake();
            } else if(robotMap.rightBumper.get()) {
                releaseBall();
            } else {
                stop();
            }
        }

    }

    public void liftIntake() {
        if(intakeDown) {
            robotMap.intakeTurner.set(0.5); //TODO check the speed and direction
        } else {
            robotMap.intakeTurner.set(0);
        }

        if(robotMap.intakeCheck.get()) {
            intakeDown = false;
            changeSettings();
        }
    }

    public void lowerIntake() {
        if(!intakeDown) {
            foldTimer.start();
            changeSettings();
        }
        if(foldTimer.get() <= 0.5) {
            robotMap.intakeTurner.set(-0.5);
        } else {
            robotMap.intakeTurner.set(0);
        }
    }

    public void intake() {
        robotMap.intakeVictor.set(-0.5); //TODO check speed and direction
        robotMap.mixer.set(0.2);
    }

    public void releaseBall() {
        robotMap.intakeVictor.set(-1);
    }

    public void changeSettings() { //Use if needed
        if(!intakeDown) {
            robotMap.intakeTurner.setNeutralMode(NeutralMode.Brake);
        } else {
            robotMap.intakeTurner.setNeutralMode(NeutralMode.Coast);
        }
    }

    public void stop() {
        robotMap.intakeVictor.set(0);
        robotMap.mixer.set(0);
    }

}