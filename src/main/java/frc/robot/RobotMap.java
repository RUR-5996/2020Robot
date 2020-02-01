package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SPI;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.DigitalInput;

public class RobotMap {

    private static RobotMap robotMap;
    public static final WPI_TalonSRX leftFrontTalon = new WPI_TalonSRX(7);
    public static final WPI_TalonSRX leftRearTalon = new WPI_TalonSRX(8);
    public static final WPI_TalonSRX rightFrontTalon = new WPI_TalonSRX(2);
    public static final WPI_TalonSRX rightRearTalon = new WPI_TalonSRX(3);
    public static final SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFrontTalon, leftRearTalon);
    public static final SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFrontTalon, rightRearTalon);
    public final DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);

    public static final WPI_VictorSPX intakeMech = new WPI_VictorSPX(4); //otačí celým intakem
    public static final WPI_VictorSPX intakeKapradi = new WPI_VictorSPX(5); //otačí kolečka na nabírání míčů

    public static final WPI_TalonSRX shooterTop = new WPI_TalonSRX(0);
    public static final WPI_TalonSRX shooterBottom = new WPI_TalonSRX(1);


    public final AHRS ahrs = new AHRS(SPI.Port.kMXP);

    public static final XboxController controller = new XboxController(0);

    public static final Button buttonA = new JoystickButton(controller, 0);
    public static final Button buttonB = new JoystickButton(controller, 1);
    public static final Button buttonX = new JoystickButton(controller, 2);
    public static final Button buttonY = new JoystickButton(controller, 3);
   
    public static final DigitalInput mechIsDown = new DigitalInput(0);
    public static final DigitalInput mechIsUp = new DigitalInput(1);

    private RobotMap() {  
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
        return -deadzone(controller.getX(GenericHID.Hand.kLeft));
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
        return -deadzone(controller.getX(GenericHID.Hand.kRight));
    }

    public double getLeftTrigger() {
        return -deadzone(controller.getTriggerAxis(GenericHID.Hand.kLeft));

    }

    public double getPOV() {
        return controller.getPOV();
    }


}