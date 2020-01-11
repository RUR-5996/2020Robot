package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive {

    RobotMap robotMap;
    int i;
    double errorL, errorR, outputL, outputR, lastErrorL, lastErrorR;

    public Drive(RobotMap robotMap) {
        this.robotMap = robotMap;
    }

    public void setupAuto() {
        i = 0;
        lastErrorL = 0;
        lastErrorR = 0;
    }

    public void drive() {
        robotMap.drive.tankDrive(robotMap.getLeftY(), robotMap.getRightY());
    }

    //the magic happens here
    public void autoDrive() {
        if(i < Constants.leftPos.length)
        {
            errorL = Constants.leftPos[i] - Constants.leftDriveDist;
            outputL = Constants.autokP * errorL + Constants.autokD * ((errorL - lastErrorL) / Constants.leftVel[i]) + (Constants.autokV * Constants.leftVel[i] + Constants.autokA * Constants.leftAcc[i]);
            lastErrorL = errorL;

            errorR = Constants.rightPos[i] - Constants.rightDriveDist;
            outputR = Constants.autokP * errorR + Constants.autokD * ((errorR - lastErrorR) / Constants.rightVel[i]) + (Constants.autokV * Constants.rightVel[i] + Constants.autokA * Constants.rightAcc[i]);
            lastErrorR = errorR;
            i++;

            SmartDashboard.putNumber("leftOutput", outputL);
            SmartDashboard.putNumber("rightOutput", outputR);
        }
        else
        {
            System.out.println("finished or empty array");
            outputL = 0;
            outputR = 0;
        }
        robotMap.drive.tankDrive(outputL, outputR);
    }

    public void autoDriveBack() {
        if(i < Constants.leftPos.length)
        {
            errorL = Constants.leftPos[i] - Constants.leftDriveDist;
            outputL = Constants.autokP * errorL + Constants.autokD * ((errorL - lastErrorL) / Constants.leftVel[i]) + (Constants.autokV * Constants.leftVel[i] + Constants.autokA * Constants.leftAcc[i]);
            outputL = -outputL;
            lastErrorL = errorL;

            errorR = Constants.rightPos[i] - Constants.rightDriveDist;
            outputR = Constants.autokP * errorR + Constants.autokD * ((errorR - lastErrorR) / Constants.rightVel[i]) + (Constants.autokV * Constants.rightVel[i] + Constants.autokA * Constants.rightAcc[i]);
            outputR = -outputR;
            lastErrorR = errorR;
            i++;

            SmartDashboard.putNumber("leftOutput", outputL);
            SmartDashboard.putNumber("rightOutput", outputR);
        }
        else
        {
            System.out.println("finished or empty array");
            outputL = 0;
            outputR = 0;
        }
        robotMap.drive.tankDrive(outputL, outputR);
    }
}