package frc.robot;

public class Climber {

    RobotMap robotMap;

    public Climber() {
        robotMap = RobotMap.getRobotMap();
    }

    public void periodic() {
        if(robotMap.getLogitechY() > 0.75) {
            climbUp();
        } else if(robotMap.getLogitechY() < -0.75) {
            climbDown();
        } else {
            stop();
        }
    }

    public void aim() { //silenced not to cause harm
        /*if(robotMap.touchlessAim.getVoltage() > 4 || Diagnostics.climberDown) {
            climbUp();
        } else if(robotMap.touchlessAim.getVoltage() < 0.5) {
            stop();
            Diagnostics.climberAiming = true;
        } else {
            stop();
        }*/
    }

    public void aimReset() { //silenced for now
        /*if(Diagnostics.climberAiming || robotMap.touchlessAim.getVoltage() > 4) {
            climbDown();
        } else if(robotMap.touchlessAim.getVoltage() < 0.5) {
            stop();
            Diagnostics.climberAiming = false;
        } else {
            stop();
        }*/
    }

    public void climbDown() {
        robotMap.climberLeft.set(Constants.climberSpeed);
        robotMap.climberRight.set(-Constants.climberSpeed);
    }

    public void climbUp() {
        robotMap.climberLeft.set(-Constants.climberSpeed);
        robotMap.climberRight.set(Constants.climberSpeed);
    }

    public void stop() {
        robotMap.climberLeft.set(0);
        robotMap.climberRight.set(0);
    }

}