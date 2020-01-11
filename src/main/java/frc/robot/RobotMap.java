package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Joystick;

public class RobotMap {

    private static RobotMap robotMap;
    public static final WPI_TalonSRX leftFrontTalon = new WPI_TalonSRX(0);
    public static final WPI_TalonSRX leftRearTalon = new WPI_TalonSRX(1);
    public static final WPI_TalonSRX rightFrontTalon = new WPI_TalonSRX(2);
    public static final WPI_TalonSRX rightRearTalon = new WPI_TalonSRX(3);
    public static final SpeedControllerGroup leftGroup = new SpeedControllerGroup(leftFrontTalon, leftRearTalon);
    public static final SpeedControllerGroup rightGroup = new SpeedControllerGroup(rightFrontTalon, rightRearTalon);
    public final DifferentialDrive drive = new DifferentialDrive(leftGroup, rightGroup);

    public static final Joystick leftStick = new Joystick(0);
    public static final Joystick rightStick = new Joystick(1);
    public static final Button buttonL1 = new JoystickButton(leftStick, 1);
    public static final Button buttonL2 = new JoystickButton(leftStick, 2);
    public static final Button buttonL3 = new JoystickButton(leftStick, 3);
    public static final Button buttonL4 = new JoystickButton(leftStick, 4);
    public static final Button buttonL5 = new JoystickButton(leftStick, 5);

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
        return -deadzone(leftStick.getY());
    }

    /**
     * Method for accessing Y axis of right joystick.
     * @return double Y axis.
     */
    public double getRightY() {
        return -deadzone(rightStick.getY());
    }

}