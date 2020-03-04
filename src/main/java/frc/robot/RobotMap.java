package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Servo;

public class RobotMap {

    private static RobotMap robotMap;
    public static final WPI_VictorSPX leftFrontVictor = new WPI_VictorSPX(0);
    public static final WPI_VictorSPX leftBackVictor = new WPI_VictorSPX(1);
    public static final WPI_VictorSPX leftCenterVictor = new WPI_VictorSPX(2);
    public static final WPI_VictorSPX rightFrontVictor = new WPI_VictorSPX(3);
    public static final WPI_VictorSPX rightBackVictor = new WPI_VictorSPX(4);
    public static final WPI_VictorSPX rightCenterVictor = new WPI_VictorSPX(5);
    public static final SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFrontVictor, leftBackVictor, leftCenterVictor);
    public static final SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFrontVictor, rightBackVictor, rightCenterVictor);
    public static final DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);

    public static final WPI_TalonSRX climberLeft = new WPI_TalonSRX(4);
    public static final WPI_TalonSRX climberRight = new WPI_TalonSRX(3);

    public static final WPI_VictorSPX intakeVictor = new WPI_VictorSPX(6);
    public static final Servo intakeLock = new Servo(1);
    public static final DigitalInput intakeCheck = new DigitalInput(0);
    
    public static final WPI_TalonSRX shooterTop = new WPI_TalonSRX(1);
    public static final WPI_TalonSRX shooterBottom = new WPI_TalonSRX(2);
    public static final SpeedControllerGroup shooterGroup = new SpeedControllerGroup(shooterTop, shooterBottom);
    public static final Servo ballStop = new Servo(0);

    public static final AnalogInput touchlessAim = new AnalogInput(3); //change port

    public final AHRS ahrs = new AHRS(SPI.Port.kMXP);

    public static final XboxController controller = new XboxController(0);
    public static final Joystick logitech = new Joystick(1);

    public static final Button buttonA = new JoystickButton(controller, 1);
    public static final Button buttonB = new JoystickButton(controller, 2);
    public static final Button buttonX = new JoystickButton(controller, 3);
    public static final Button buttonY = new JoystickButton(controller, 4);
    public static final Button leftBumper = new JoystickButton(controller, 5);
    public static final Button rightBumper = new JoystickButton(controller, 6);
    public static final Button stop = new JoystickButton(controller, 7);
    public static final Button start = new JoystickButton(controller, 8);
    public static final Button leftJoystick = new JoystickButton(controller, 9);
    public static final Button rightJoystick = new JoystickButton(controller, 10);

    public static final Button logitechTrigger = new JoystickButton(logitech, 1);
    public static final Button logitechTwo = new JoystickButton(logitech, 2);
    public static final Button logitechFour = new JoystickButton(logitech, 4);
    public static final Button logitechFive = new JoystickButton(logitech, 5);

    private RobotMap() {  
        setupDrive(leftFrontVictor);
        setupDrive(leftCenterVictor);
        setupDrive(leftBackVictor);
        setupDrive(rightFrontVictor);
        setupDrive(rightCenterVictor);
        setupDrive(rightBackVictor);

        setupVictor(intakeVictor);

        setupShooter(shooterBottom);
        setupShooter(shooterTop);

        setupClimber(climberLeft);
        setupClimber(climberRight);
    }

    private void setupShooter(WPI_TalonSRX talon) {
        talon.configNominalOutputForward(0, Constants.timeoutMs);
        talon.configNominalOutputReverse(0, Constants.timeoutMs);
        talon.configPeakOutputForward(1, Constants.timeoutMs);
        talon.configPeakOutputReverse(-1, Constants.timeoutMs);
        talon.configAllowableClosedloopError(0, 0, Constants.timeoutMs);
        talon.configNeutralDeadband(0.05, Constants.timeoutMs);
        talon.setNeutralMode(NeutralMode.Coast);
        talon.enableCurrentLimit(true);
        talon.configContinuousCurrentLimit(30, Constants.timeoutMs);
        talon.configPeakCurrentLimit(30, Constants.timeoutMs);
        talon.configPeakCurrentDuration(200, Constants.timeoutMs);
        talon.configOpenloopRamp(0.25);
        talon.configOpenloopRamp(0.25);
    }

    private void setupVictor(WPI_VictorSPX victor) {
        victor.configNominalOutputForward(0, Constants.timeoutMs);
        victor.configNominalOutputReverse(0, Constants.timeoutMs);
        victor.configPeakOutputForward(1, Constants.timeoutMs);
        victor.configPeakOutputReverse(-1, Constants.timeoutMs);
        victor.configAllowableClosedloopError(0, 0, Constants.timeoutMs);
        victor.configNeutralDeadband(0.05, Constants.timeoutMs);
        victor.setNeutralMode(NeutralMode.Brake);
    }

    private void setupDrive(WPI_VictorSPX driveVictor) {
        driveVictor.configNominalOutputForward(0, Constants.timeoutMs);
        driveVictor.configNominalOutputReverse(0, Constants.timeoutMs);
        driveVictor.configPeakOutputForward(1, Constants.timeoutMs);
        driveVictor.configPeakOutputReverse(-1, Constants.timeoutMs);
        driveVictor.configAllowableClosedloopError(0, 0, Constants.timeoutMs);
        driveVictor.configNeutralDeadband(0.05, Constants.timeoutMs);
        driveVictor.configOpenloopRamp(0.25);
        driveVictor.setNeutralMode(NeutralMode.Brake);
    }

    private void setupClimber(WPI_TalonSRX talon) {
        talon.configNominalOutputForward(0, Constants.timeoutMs);
        talon.configNominalOutputReverse(0, Constants.timeoutMs);
        talon.configPeakOutputForward(1, Constants.timeoutMs);
        talon.configPeakOutputReverse(-1, Constants.timeoutMs);
        talon.configAllowableClosedloopError(0, 0, Constants.timeoutMs);
        talon.configNeutralDeadband(0.05, Constants.timeoutMs);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.enableCurrentLimit(true);
        talon.configContinuousCurrentLimit(30, Constants.timeoutMs);
        talon.configPeakCurrentLimit(30, Constants.timeoutMs);
        talon.configPeakCurrentDuration(200, Constants.timeoutMs);
        talon.configOpenloopRamp(0.25);
        talon.configOpenloopRamp(0.25);
    }

    /**
     * Initializes and accesses robotMap object.
     * Used to pass bindings throughout the code.
     * @return robotMap object - accessing bindings.
     */
    public static RobotMap getRobotMap() {
        if(robotMap == null) {
            robotMap = new RobotMap();
        }
        return robotMap;
    }

    /**
     * Method for disregarding input if lower than deadzone threshold.
     * @param input double deadzone threshold.
     * @return double output is larger than deadzone, if lower then 0.
     */
    public double deadzone(double input) {
        if(Math.abs(input) > Constants.deadzone) {
            return input;
        }
        else {
            return 0;
        }
    }

    /**
     * Method for accessing Y axis of left joystick.
     * @return double Y axis.
     */
    public double getLeftY() {
        return -deadzone(controller.getY(GenericHID.Hand.kLeft));
    }

    /**
     * Method for accessing X axis of left joystick.
     * @return double X axis.
     */
    public double getLeftX() {
        return deadzone(controller.getX(GenericHID.Hand.kLeft));
    }

    /**
     * Method for accessing Y axis of right joystick.
     * @return double Y axis.
     */
    public double getRightY() {
        return -deadzone(controller.getY(GenericHID.Hand.kRight));
    }

    /**
     * Method for accessing X axis of right joystick.
     * @return double X axis.
     */
    public double getRightX() {
        return deadzone(controller.getX(GenericHID.Hand.kRight));
    }

    /**
     * Method for accessing right trigger axis of the controller
     * @return double right trigger axis 0 to 1
     */
    public double getRightTrigger() {
        return Math.abs(deadzone(controller.getTriggerAxis(GenericHID.Hand.kRight)));
    }

    /**
     * Method for accessing left trigger axis of the controller
     * @return double left trigger axis 0 to 1
     */
    public double getLeftTrigger() {
        return Math.abs(deadzone(controller.getTriggerAxis(GenericHID.Hand.kLeft)));
    }

    public double getLogitechY() {
        return -deadzone(logitech.getY());
    }

    public double getLogitechX() {
        return deadzone(logitech.getX());
    }

}