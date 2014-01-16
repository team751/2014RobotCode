/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.priory751.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.priory751.base.RobotMap;
import org.priory751.commands.TankDrive;
import org.priory751.subsystems.drivetrain.EncoderDrive;

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
    
    public boolean shouldStop = false;
    
    /**
     *
     */
    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }
    
    /**
     * Sets the left and right motor speeds from joysticks
     * @param leftJoystick
     * @param rightJoystick
     */
    public void tankDrive(Joystick leftJoystick, Joystick rightJoystick) {
        setLeftSpeed(leftJoystick.getAxis(Joystick.AxisType.kY));
        setRightSpeed(rightJoystick.getAxis(Joystick.AxisType.kY));
    }
    
    /**
     * Sets the left and right motor speeds from doubles
     * @param left
     * @param right
     */
    public void tankDrive(double left, double right) {
        setLeftSpeed(left);
        setRightSpeed(right);
    }
    
    /**
     * Drives the robot forward a set number of inches
     * @param inches
     */
    
    public void drive(double inches) {
        EncoderDrive ed = new EncoderDrive(this, inches);
        new Thread(ed).start();
    }
    
    /**
     * Sets the left motor speed
     * @param speed
     */
    public void setLeftSpeed(double speed) {
        leftDriveJaguar.set(speed);
    }
    
    /**
     * Sets the right motor speed
     * @param speed
     */
    public void setRightSpeed(double speed) {
        rightDriveJaguar.set(speed);
    }
    
    /**
     * Sets the speed of both motors
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
        leftDriveJaguar.set(0);
        rightDriveJaguar.set(0);
        shouldStop = true;
    }
}
