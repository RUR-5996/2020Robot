package frc.robot.PID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.Diagnostics;

public class DrivePID {

    private double setpoint;
    private PIDController driveController = new PIDController(Constants.driveKP, Constants.driveKI, Constants.driveKD);
    Timer targeting;
    boolean onTarget;

    public DrivePID() {
        targeting = new Timer();
        targeting.stop();
        targeting.reset();

        onTarget = false;
    }

    public void DrivePIDInit() {
        driveController.setTolerance(0.15);
    }

    public void setTarget(double setpoint) {
        this.setpoint = setpoint;
    }

    public double pidGet() {
        checkTarget();
        double avgDist = (Diagnostics.leftDriveDist + Diagnostics.rightDriveDist) / 2;
        double speed = MathUtil.clamp(driveController.calculate(avgDist, setpoint), -Constants.driveSpeed, Constants.driveSpeed);
        return speed;
    }

    public void checkTarget() {
        if(Math.abs(driveController.getPositionError()) <= 0.15 && targeting.get() == 0) {
            targeting.start();
            onTarget = false;
        } else if(Math.abs(driveController.getPositionError()) > 0.15 && targeting.get() < 0.5) {
            targeting.reset();
            onTarget = false;
        } else if(Math.abs(driveController.getPositionError()) <= 0.15 && targeting.get() < 0.5) {
            onTarget = false;
        } else if(Math.abs(driveController.getPositionError()) <= 0.15 && targeting.get() >= 0.5) {
            onTarget = true;
        }
    }

    public boolean onTarget() {
        return onTarget;
    }
}