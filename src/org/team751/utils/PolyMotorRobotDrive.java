package org.team751.utils;

import edu.wpi.first.wpilibj.SpeedController;
/**
 * A RobotDrive that works with any number of motors.
 *
 * When this class accesses a speed controller, it is synchronized. This allows
 * it to safely be used when other threads are also accessing them.
 *
 * @author Sam Crow
 */
public class PolyMotorRobotDrive {
    /**
     * The motors on the left side of the robot
     */
    protected SpeedController[] leftMotors;
    /**
     * The motors on the right side of the robot
     */
    protected SpeedController[] rightMotors;

    /**
     * Constructor
     *
     * @param leftMotors The motors on the left side of the robot
     * @param rightMotors The motors on the right side of the robot
     */
    public PolyMotorRobotDrive(SpeedController[] leftMotors, SpeedController[] rightMotors) {
        this.leftMotors = leftMotors;
        this.rightMotors = rightMotors;
    }

    /**
     * Drive the robot with arcade drive
     *
     * @param moveValue The degree to which the robot should turn left or right.
     * Full left is -1, full right is +1.
     * @param rotateValue The degree to which the robot should be moved
     * forward/back. Full forward is +1, full reverse is -1
     */
    public void arcadeDrive(double moveValue, double rotateValue) {
        //Based on http://www.chiefdelphi.com/media/papers/2661?langid=2

        double max = Math.abs(rotateValue);
        if (Math.abs(moveValue) > max) {
            max = Math.abs(moveValue);
        }
        double sum = rotateValue + moveValue;
        double difference = rotateValue - moveValue;

        double leftPower;
        double rightPower;

        if (rotateValue > 0) {
            if (moveValue >= 0) {
                leftPower = max;
                rightPower = difference;
            } else {
                leftPower = sum;
                rightPower = max;
            }
        } else {
            if (moveValue >= 0) {
                leftPower = sum;
                rightPower = -max;
            } else {
                leftPower = -max;
                rightPower = difference;
            }
        }

        setLeftRightMotorOutputs(leftPower, rightPower);
    }

    /**
     * Set the output of the left and right motors
     *
     * @param leftOutput
     * @param rightOutput
     */
    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        //Set the output of each left motor (they won't actually update
        //until later)
        for (int i = 0, max = leftMotors.length; i < max; i++) {

            SpeedController controller = leftMotors[i];

            synchronized (controller) {
                // Set the speed
                controller.set(leftOutput);
            }
        }

        for (int i = 0, max = rightMotors.length; i < max; i++) {

            SpeedController controller = rightMotors[i];

            synchronized (controller) {
                controller.set(rightOutput);
            }
        }
    }
}
