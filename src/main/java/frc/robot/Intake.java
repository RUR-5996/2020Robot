package frc.robot;

public class Intake {

    RobotMap robotMap;
    boolean kapradiIsSpinning = false;
    int spinMechStage = 1;


    public Intake(RobotMap robotMap) {
        this.robotMap = robotMap;
    }


    public void spinMech() {
        if (robotMap.buttonX.get()){
            if(robotMap.mechIsUp.get()){
                switch (spinMechStage) {
                    case 1:
                            robotMap.intakeMech.set(1);
        
                            if (robotMap.mechIsDown.get()){
                                spinMechStage = 2;
                            }
                            break;
                    case 2:
                            robotMap.intakeMech.set(0);
        
                            spinMechStage = 1;
                            break;
        
                }
            }

            if(robotMap.mechIsDown.get()){
                switch (spinMechStage) {
                    case 1:
                            robotMap.intakeMech.set(-1);
        
                            if (robotMap.mechIsUp.get()){
                                spinMechStage = 2;
                            }
                            break;
                    case 2:
                            robotMap.intakeMech.set(0);
        
                            spinMechStage = 1;
                            break;
        
                }
            }
            
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


