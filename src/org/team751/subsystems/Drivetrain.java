/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.RobotMap;
import org.team751.commands.drivetrain.DriveStraight;
import org.team751.commands.drivetrain.CheesyJoystickDrive;
import org.team751.utils.PolyMotorRobotDrive;
import org.team751.cheesy.CheesyDrive;
import org.team751.subsystems.drivetrain.LeftDriveTrainPID;
import org.team751.subsystems.drivetrain.RightDriveTrainPID;
import org.team751.utils.Logger;

/**
 *
 * @author sambaumgarten
 */
public class Drivetrain extends Subsystem {

    // Init Variables
    Jaguar leftDriveJaguar = RobotMap.leftDrivetrainJaguar;
    Jaguar rightDriveJaguar = RobotMap.rightDrivetrainJaguar;

    public final Encoder leftDriveEncoder = RobotMap.leftDriveEncoder;
    public final Encoder rightDriveEncoder = RobotMap.rightDriveEncoder;

    private final PolyMotorRobotDrive drive;
    private final LeftDriveTrainPID ldtpid;
    private final RightDriveTrainPID rdtpid;
    private final long lastRunTime = System.currentTimeMillis();

    public final double leftDriveSpeed = 0;
    public final double rightDriveSpeed = 0;
    
    private final boolean useCAN = false;

    /**
     * Keeps track of Cheesy Drive data
     */
    private final CheesyDrive cheeseDrive = new CheesyDrive();

    public Drivetrain() {
        if (useCAN) {
            drive = new PolyMotorRobotDrive(new SpeedController[]{RobotMap.leftDrivetrain1CANJaguar, RobotMap.leftDrivetrain2CANJaguar, RobotMap.leftDrivetrain3CANJaguar}, new SpeedController[]{RobotMap.rightDrivetrain1CANJaguar, RobotMap.rightDrivetrain2CANJaguar, RobotMap.rightDrivetrain3CANJaguar}); // CAN
        } else {
            drive = new PolyMotorRobotDrive(new SpeedController[]{leftDriveJaguar}, new SpeedController[]{rightDriveJaguar}); // PWM
        }
        
        ldtpid = new LeftDriveTrainPID();
        rdtpid = new RightDriveTrainPID();
        
//        if (SmartDashboard.getBoolean("DriveTrainPID Enabled", false)) {
//            ldtpid.enable();
//            rdtpid.enable();
//        }
            
            
    }

    public void initDefaultCommand() {
        setDefaultCommand(new CheesyJoystickDrive());
    }

    /**
     * Sets the left and right motor speeds from joysticks
     *
     * @param leftJoystick
     * @param rightJoystick
     */
    public void tankDrive(Joystick leftJoystick, Joystick rightJoystick) {
        setLeftSpeed(leftJoystick.getAxis(Joystick.AxisType.kY));
        setRightSpeed(rightJoystick.getAxis(Joystick.AxisType.kY));
    }

    /**
     * Sets the left and right motor speeds from doubles
     *
     * @param left
     * @param right
     */
    public void tankDrive(double left, double right) {
        setLeftSpeed(left);
        setRightSpeed(right);
    }

    /**
     * Drive the robot, arcade drive style
     *
     * @param moveValue Forward/back motion. +1 is full forwards, -1 is full
     * reverse
     * @param rotateValue Rotation. -1 is left, +1 is right
     */
    public void arcadeDrive(double moveValue, double rotateValue) {
        drive.arcadeDrive(moveValue, rotateValue);
    }

    /**
     * Drive the robot with the Cheesy Drive algorithm
     *
     * @param throttle Forward/back motion. +1 is full forwards, -1 is full
     * reverse
     * @param wheel Rotation. -1 is left, +1 is right
     * @param quickTurn If quick turn mode should be used
     */
    public void cheesyDrive(double throttle, double wheel, boolean quickTurn) {
        CheesyDrive.MotorOutputs outputs = cheeseDrive.cheesyDrive(throttle, wheel, quickTurn);
        //Invert the right motor output so that it will work
        
        if (outputs.right > 0) {
            drive.setLeftRightMotorOutputs(outputs.left, -outputs.right*SmartDashboard.getNumber("rightConst", 1));
        } else {
            drive.setLeftRightMotorOutputs(outputs.left, -outputs.right);
        }
         
    }

    /**
     * Drives the robot forward a set number of inches
     *
     * @param inches
     */
    public void drive(double inches) {
        DriveStraight command = new DriveStraight(inches * 0.0254);
        command.start();
    }

    /**
     * The speed of the left motors (what it should be)
     *
     * @return double speed
     */
    public double getLeftSpeed() {
        if (useCAN) {
            return 0;
        } else {
            return leftDriveJaguar.getSpeed();
        }
    }

    /**
     * The speed of the right motors (what it should be)
     *
     * @return speed
     */
    public double getRightSpeed() {
        if (useCAN) {
            return 0;
        } else {
            return leftDriveJaguar.getSpeed();
        }
    }

    /**
     * Set the speed that the left motors should go at in RPM
     *
     * @param rpm
     */
    public void setLeftRPM(double rpm) {
        ldtpid.setSetpoint(rpm);
    }

    /**
     * Set the speed that the right motors should go at in RPM
     *
     * @param rpm
     */
    public void setRightRPM(double rpm) {
        rdtpid.setSetpoint(rpm);
    }

    /**
     * Sets the left motor speed
     *
     * @param speed
     */
    public void setLeftSpeed(double speed) {
            leftDriveJaguar.set(speed);
    }

    /**
     * Sets the right motor speed
     *
     * @param speed
     */
    public void setRightSpeed(double speed) {
            rightDriveJaguar.set(speed);
    }

    /**
     * Sets the speed of both motors
     *
     * @param speed
     */
    public void setSpeed(double speed) {
        setLeftSpeed(speed);
        setRightSpeed(speed);
    }

    /**
     * Stops the motors and driving
     */
    public void stop() {
        this.setRightSpeed(0);
        this.setLeftSpeed(0);
    }
}
