package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import com.revrobotics.ColorSensorV3;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogInput;

public class Diagnostics {

    RobotMap robotMap;
    Timer measure;
    ColorSensorV3 colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    String color = "";
    NetworkTable brightCamera;
    AnalogInput intakeCounter, shooterCounter;

    public Diagnostics(RobotMap robotMap) {
        this.robotMap = robotMap;
        brightCamera = NetworkTableInstance.getDefault().getTable(Constants.limelightName);
        intakeCounter = new AnalogInput(0);
        shooterCounter = new AnalogInput(1);
    }

    public void send() { //umisťuje různé hodnoty do Shuffleboardu
        SmartDashboard.putNumber("left drive ticks", Constants.leftDriveTicks);
        SmartDashboard.putNumber("right drive ticks", Constants.rightDriveTicks);
        SmartDashboard.putNumber("left drive distance", Constants.leftDriveDist);
        SmartDashboard.putNumber("right drive distance", Constants.rightDriveDist);
        SmartDashboard.putString("color sensor", color);
    }

    public void periodic() {
        getDriveTicks();
        calcDriveDist();
        setlimelightModePeriodic();
        getlimelightValues();

        send();
    }

    public void getlimelightValues() { //získává hodnoty z limelightu a posílá je do Constants
        Constants.tv = brightCamera.getEntry("tv").getDouble(0);
        Constants.tx = brightCamera.getEntry("tx").getDouble(0);
        Constants.ty = brightCamera.getEntry("ty").getDouble(0);
        Constants.ta = brightCamera.getEntry("ta").getDouble(0);
    }

    public void setlimelightModePeriodic() { //nastavuje mód limelightu (processing/driving) a 
        brightCamera.getEntry("pipeline").setNumber(Constants.limelightPipeline);
    }

    public void getBallSensorsData() { //měří vzálenosti u intaku a shooteru
        Constants.intakeUltraDist = intakeCounter.getVoltage() * Constants.ultrasonicVoltsToDistance;
        Constants.shooterUltraDist = shooterCounter.getVoltage() * Constants.ultrasonicVoltsToDistance;
    }

    public void countBalls() { //počítá počet míčů v robotovi na základě ultrasoniků
        if(Constants.intakeUltraDist <= 30) {
            Constants.ballCount++;
        }
        if(Constants.shooterUltraDist <= 30) {
            Constants.ballCount--;
        }
    }

    public void getColor() { //nastavuje barvu podle outputů - funguje pouze v určité vzdálenosti
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

    public void resetDriveEncoders() { //nastaví hodnotu drive encoderů na 0
        robotMap.leftFrontTalon.setSelectedSensorPosition(0);
        robotMap.rightFrontTalon.setSelectedSensorPosition(0);
    }

    public void getDriveTicks() { //ukládá hodnoty drive encoderů do Constants
        Constants.leftDriveTicks = robotMap.leftFrontTalon.getSelectedSensorPosition();
        Constants.rightDriveTicks = robotMap.rightFrontTalon.getSelectedSensorPosition();
    }

    public void calcDriveDist() { //počítá ujetou vzdálenost podle hodnot z drive encoderů
        Constants.leftDriveDist = (Constants.leftDriveTicks/Constants.leftDriveRatio) * 2 * Math.PI * Constants.driveWheelRadius;
        Constants.rightDriveDist = (Constants.rightDriveTicks/Constants.rightDriveRatio) * 2 * Math.PI * Constants.driveWheelRadius;
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
}