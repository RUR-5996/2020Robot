package frc.robot.PID;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class LimelightTurn {

    PIDController limelightTurnController = new PIDController(Constants.turnKP, Constants.turnKI, Constants.turnKD);
    double setpoint;
    RobotMap robotMap;

    public LimelightTurn() {

    }

    public void setupPID() {
        limelightTurnController.setTolerance(0.5);
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }

    public double pidGet() {
        double speed = MathUtil.clamp(limelightTurnController.calculate(Constants.tx, setpoint), -1.0, 1.0);
        return speed;
    }
}   