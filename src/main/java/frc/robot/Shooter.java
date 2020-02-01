package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;

public class Shooter {
    RobotMap robotMap;
    Boolean shooting = false;
    public Shooter(RobotMap robotMap){
        this.robotMap = robotMap;
    }

    public void shoot(){
        robotMap.shooterTop.set(-robotMap.getLeftTrigger());
        robotMap.shooterBottom.set(-robotMap.getLeftTrigger());

        if(Math.abs(robotMap.getLeftTrigger()) >= 0.75) {
            robotMap.shooterTop.set(Constants.shooterSpeed);
            robotMap.shooterBottom.set(Constants.shooterSpeed);
        } else {
            robotMap.shooterTop.set(0);
            robotMap.shooterTop.set(0);
        }

        /*if(robotMap.controller.getBumper(GenericHID.Hand.kLeft)){
            if(shooting == false){
                shooting = true;
               
                robotMap.shooterTop.set(Constants.shooterSpeed);
                robotMap.shooterBottom.set(Constants.shooterSpeed);



            }
            else if(shooting){
                shooting = false;
                robotMap.shooterTop.set(0);
                robotMap.shooterBottom.set(0);
            }
        
        }*/
    }

}