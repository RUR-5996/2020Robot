package frc.robot.PID;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpiutil.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Diagnostics;

public class TurretTurnPID {

    RobotMap robotMap;
    private double setpoint;
    private PIDController turretController = new PIDController(Constants.turretKP, Constants.turretKI, Constants.turretKD);
    Timer targeting;
    public boolean onTarget;

    public TurretTurnPID() {
        targeting = new Timer();
        targeting.stop();
        targeting.reset();

        onTarget = false;
    }

    public void turretTurnInit() {
        turretController.setTolerance(50); //encoder ticks, TODO measure
    }

    public void setTarget(double setpoint) {
        this.setpoint = setpoint;
    }

    public double pidGet() {
        checkTarget();
        double speed = MathUtil.clamp(turretController.calculate(Diagnostics.turretTicks, setpoint), -Constants.turretSpeed, Constants.turretSpeed);
        return speed;
    }

    public void checkTarget() { //TODO check the tick error, no clue what the revs will be
        if(Math.abs(turretController.getPositionError()) <= 50 && targeting.get() == 0) {
            targeting.start();
            onTarget = false;
        } else if(Math.abs(turretController.getPositionError()) > 50 && targeting.get() < 0.5) {
            targeting.reset();
            onTarget = false;
        } else if(Math.abs(turretController.getPositionError()) <= 50 && targeting.get() < 0.5) {
            onTarget = false;
        } else if(Math.abs(turretController.getPositionError()) <= 50 && targeting.get() >= 0.5) {
            onTarget = true;
        }
    }

}