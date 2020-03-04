package frc.robot.PID;

import frc.robot.Constants;
import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;

public class TurnPID {

    RobotMap robotMap;
    private double setpoint;
    private PIDController turnController = new PIDController(Constants.turnKP, Constants.turnKI, Constants.turnKD);
    Timer targeting;
    boolean onTarget;

    public TurnPID() {
        robotMap = RobotMap.getRobotMap();

        targeting = new Timer();
        targeting.stop();
        targeting.reset();

        onTarget = false;
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
        checkTarget();
        double speed = MathUtil.clamp(turnController.calculate(robotMap.ahrs.getAngle(), setpoint), -Constants.turnSpeed, Constants.turnSpeed);
        return speed;
    }

    public void checkTarget() {
        if(Math.abs(turnController.getPositionError()) <= 0.15 && targeting.get() == 0) {
            targeting.start();
            onTarget = false;
        } else if(Math.abs(turnController.getPositionError()) > 0.15 && targeting.get() < 0.5) {
            targeting.reset();
            onTarget = false;
        } else if(Math.abs(turnController.getPositionError()) <= 0.15 && targeting.get() < 0.5) {
            onTarget = false;
        } else if(Math.abs(turnController.getPositionError()) <= 0.15 && targeting.get() >= 0.5) {
            onTarget = true;
        }
    }

    public boolean onTarget() {
        return onTarget;
    }

}