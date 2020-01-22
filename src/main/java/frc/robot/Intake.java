package frc.robot;

public class Intake {

    RobotMap robotMap;
    boolean kapradiIsSpinning = false;

    public Intake(RobotMap robotMap) {
        this.robotMap = robotMap;
    }

    public void spinMech() {
        if (robotMap.buttonX.get()){
            
        }
    }  

    public void spinKapradi() {
        if (robotMap.buttonA.get()) {
            if (kapradiIsSpinning) {
                 robotMap.intakeKapradi.set(1);
                kapradiIsSpinning = true;
            }
            else {
                robotMap.intakeKapradi.set(0);
                kapradiIsSpinning = false;
            }

        
        }
    }



}


