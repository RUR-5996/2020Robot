package frc.robot.PID;

import frc.robot.Constants;
import frc.robot.Diagnostics;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

//TODO změnit a implementovat tak, aby otáčelo turretu

public class LimelightTurnPID {

    private double setpoint;
    private PIDController turnController = new PIDController(Constants.turnKPLL, Constants.turnKILL, Constants.turnKDLL);
    boolean onTarget;
    Timer targeting;

    public LimelightTurnPID() {
        targeting = new Timer();
        targeting.stop();
        targeting.reset();

        onTarget = false;
    }

    public void LimelightTurnPIDInit() {
        turnController.setTolerance(1);
    }

    public void setTarget(double setpoint) {
        this.setpoint = setpoint;
    }

    public double pidGet() { 

        checkTarget();
        double speed = MathUtil.clamp(turnController.calculate(Diagnostics.xOffset, setpoint), -Constants.turnSpeedLL, Constants.turnSpeedLL);
        return speed;
    }

    public void checkTarget() {
        if(Math.abs(turnController.getPositionError()) <= 1 && targeting.get() == 0) {
            targeting.start();
            onTarget = false;
        } else if(Math.abs(turnController.getPositionError()) > 1 && targeting.get() < 0.5) {
            targeting.reset();
            onTarget = false;
        } else if(Math.abs(turnController.getPositionError()) <= 1 && targeting.get() < 0.5) {
            onTarget = false;
        } else if(Math.abs(turnController.getPositionError()) <= 1 && targeting.get() >= 0.5) {
            onTarget = true;
        }
    }

    public boolean onTarget() {
        return onTarget;
    }
}