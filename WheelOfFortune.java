package frc.robot;

import edu.wpi.first.wpilibj.Servo;
import com.revrobotics.ColorSensorV3;

public class WheelOfFortune {
    private static final Servo servo = new Servo(Constants.servoInputChannel);
    public int numberOfHalfRotations;

        servo.set(Constants.servoPosition); //sets the beginning value
        servo.setAngle(Constants.servoAngle); //sets the beginning servo angle

        public void tiltManipulator() {
            if(servo.getAngle()<90 && RobotMap.buttonA.get()){//the motor will run till the manipulator gets into desired position
                ////insert code to: start running motor 
            }
        }
        public void tiltManipulatorBack() {
            if(servo.getAngle()>0 && RobotMap.buttonB.get()){//the motor will run till the manipulator gets back into starting position
                ////insert code to: start running motor in opposite direction
            }
        }
        
        public void spinWheel() {
                String initialColor;
                String currentColor;
                initialColor = Constants.color; //sets the initialColor value to the color that is presently on the wheel
                if(numberOfHalfRotations < 6){//number of half rotations is lower than 6, because the wheel should rotate 3 to 5 times
                //insert code to: spin Wheel
                if(Constants.color == initialColor && currentColor != initialColor) {/*this code will run when the color sensor detects the initial color
                    the second condition guarantees that it runs only once*/
                    numberOfHalfRotations ++;
                    currentColor = Constants.color;//sets the currentColor to the current (initial) color, so this block only runs once
                }
                currentColor = Constants.color;/*once the color sensor detects a color other than the initial one, the currentColor variable will change
                so that the previous block of code can run*/
                };

            }
        }
}