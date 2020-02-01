package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import com.revrobotics.ColorSensorV3;

public class Diagnostics {

    RobotMap robotMap;
    Timer measure, pov;
    ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    String color = "";

    public Diagnostics(RobotMap robotMap) {
        this.robotMap = robotMap;
    }

    public void send() {
        SmartDashboard.putNumber("left drive ticks", Constants.leftDriveTicks);
        SmartDashboard.putNumber("right drive ticks", Constants.rightDriveTicks);
        SmartDashboard.putNumber("left drive distance", Constants.leftDriveDist);
        SmartDashboard.putNumber("right drive distance", Constants.rightDriveDist);
        SmartDashboard.putString("color sensor", color);
        SmartDashboard.putNumber("shooter speed", Constants.shooterSpeed);
    }

    public void periodic() {
        getDriveTicks();
        calcDriveDist();
        //setShooterSpeed();
        send();
    }

    public void getDriveSpeed() { // toto neberte v úvahu, poutze pro testovací účely
        boolean timer = false;
        double measureTime = 0;
        resetDriveEncoders();
        if(robotMap.buttonX.get()&&!timer) {
            timer = true;
            measure.start();
        }
        if(robotMap.buttonX.get()&&timer&&measure.get() >= 0.25) {
            timer = false;
            measureTime = measure.get();
            measure.reset();
            measure.stop();
            
            double velL = Constants.leftDriveDist / measureTime;
            double velR = Constants.rightDriveDist / measureTime;
            SmartDashboard.putNumber("velocity L", velL);
            SmartDashboard.putNumber("velocity R", velR);
        }

    }

    public void measureDriveAcc() { // toto neberte v úvahu, poutze pro testovací účely
        resetDriveEncoders();
        boolean timer = false;
        double measureTime;
        if(robotMap.buttonY.get()&&!timer) {
            timer = true;
            measure.start();
        }
        if(timer&&measure.get() >= 0.75) {
            timer = false;
            measureTime = measure.get();
            measure.reset();
            measure.stop();

            double accL = Constants.leftDriveDist / (measureTime * measureTime);
            double accR = Constants.rightDriveDist / (measureTime * measureTime);
            SmartDashboard.putNumber("acceleration L", accL);
            SmartDashboard.putNumber("acceleration R", accR);
        }
    }

    public void getColor() {
        if(colorSensor.getColor().toString().substring(37, 38).equals("4")) {
            color = "blue";
        } else if(colorSensor.getColor().toString().substring(37, 38).equals("6")) {
            color = "green";
        } else if(colorSensor.getColor().toString().substring(37, 38).equals("c")) {
            color = "red";
        } else if(colorSensor.getColor().toString().substring(37, 38).equals("9")) {
            color = "yellow";
        }
    }

    public void resetDriveEncoders() {
        robotMap.leftFrontTalon.setSelectedSensorPosition(0);
        robotMap.rightFrontTalon.setSelectedSensorPosition(0);
    }

    public void getDriveTicks() {
        Constants.leftDriveTicks = robotMap.leftFrontTalon.getSelectedSensorPosition();
        Constants.rightDriveTicks = robotMap.rightFrontTalon.getSelectedSensorPosition();
    }

    public void calcDriveDist() {
        Constants.leftDriveDist = (Constants.leftDriveTicks/Constants.leftDriveRatio) * 2 * Math.PI * Constants.driveWheelRadius;
        Constants.rightDriveDist = (Constants.rightDriveTicks/Constants.rightDriveRatio) * 2 * Math.PI * Constants.driveWheelRadius;
    }

    public void setShooterSpeed() {
        boolean released = true;
        if(robotMap.getPOV() == 0 && released) {
            pov.start();
            released = false;
            Constants.shooterSpeed += 0.05;
        }
        if(robotMap.getPOV() == 180 && released) {
            pov.start();
            released = false;
            Constants.shooterSpeed -= 0.05;
        }
        if(pov.get() >= 0.75) {
            released = true;
            pov.reset();
            pov.stop();
        }
    }

}