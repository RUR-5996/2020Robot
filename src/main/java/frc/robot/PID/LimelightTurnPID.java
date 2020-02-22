package frc.robot.PID;

import frc.robot.Sensors;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

public class LimelightTurnPID {
    private double setpoint;
    private PIDController turnController = new PIDController(Constants.turnKPLL, Constants.turnKILL, Constants.turnKDLL);

    public LimelightTurnPID() {

    }

    public void LimelightTurnInit() {
        turnController.setTolerance(0.5);
    }

    public void setTarget(double setpoint) {
        this.setpoint = setpoint;
    }

    public double pidGet() {
        double speed = MathUtil.clamp(turnController.calculate(Constants.xOffset, setpoint), -Constants.turnSpeedLL, Constants.turnSpeedLL);
        return speed;
    }
}