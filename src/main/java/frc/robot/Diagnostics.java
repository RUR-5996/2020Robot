package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;

public class Diagnostics {

    RobotMap robotMap;
    Timer measure;

    public Diagnostics(RobotMap robotMap) {
        this.robotMap = robotMap;
    }

    public void send() {
        SmartDashboard.putNumber("left drive ticks", Constants.leftDriveTicks);
        SmartDashboard.putNumber("right drive ticks", Constants.rightDriveTicks);
        SmartDashboard.putNumber("left drive distance", Constants.leftDriveDist);
        SmartDashboard.putNumber("right drive distance", Constants.rightDriveDist);
    }

    public void periodic() {
        getDriveTicks();
        calcDriveDist();
        send();
    }

    public void getDriveSpeed() {
        boolean timer = false;
        double measureTime = 0;
        resetDriveEncoders();
        if(robotMap.buttonL5.get()&&!timer) {
            timer = true;
            measure.start();
        }
        if(robotMap.buttonL5.get()&&timer&&measure.get() >= 0.25) {
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

    public void measureDriveAcc() {
        resetDriveEncoders();
        boolean timer = false;
        double measureTime;
        if(robotMap.buttonL2.get()&&!timer) {
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

}