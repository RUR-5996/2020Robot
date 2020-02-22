package frc.robot.PID;

import frc.robot.Constants;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

public class TurnPID {

    RobotMap robotMap;
    private double setpoint;
    private PIDController turnController = new PIDController(Constants.turnKP, Constants.turnKI, Constants.turnKD);

    public TurnPID(RobotMap robotMap) {
        this.robotMap = robotMap;
    }

    /**
     * Method for initializing the PID for turning.
     */
    public void turnPIDInit() {
        turnController.setTolerance(2);
        turnController.enableContinuousInput(-180, 180);
    }

    /**
     * Setter method for setting target degree to turn to.
     * @param setpoint - double between -180 and 180.
     */
    public void setTarget(double setpoint) {
        this.setpoint = setpoint;
    }

    /**
     * Method for getting PID input
     * @return double for motor speed, between -1 and 1.
     */
    public double pidGet() {
        double speed = MathUtil.clamp(turnController.calculate(robotMap.ahrs.getAngle(), setpoint), -Constants.turnSpeed, Constants.turnSpeed);
        return speed;
    }

}