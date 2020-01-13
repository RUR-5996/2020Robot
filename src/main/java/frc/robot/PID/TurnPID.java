package frc.robot.PID;

import frc.robot.Constants;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

public class TurnPID {

    PIDController turnController = new PIDController(Constants.turnKP, Constants.turnKI, Constants.turnKD);
    double setpoint;
    RobotMap robotMap;

    public TurnPID() {

    }

    public void setTarget(double setpoint) {
        this.setpoint = setpoint;
    }

    public void setupPID() {
        turnController.setTolerance(2);
        turnController.enableContinuousInput(-180, 180);
    }

    public double pidGet() {
        double speed = MathUtil.clamp(turnController.calculate(robotMap.ahrs.getAngle(), setpoint), -Constants.turnspeed, Constants.turnspeed);
        return speed;
    }

}