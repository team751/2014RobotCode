/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.PIDConstants;
import org.team751.RobotMap;

/**
 *
 * @author sambaumgarten
 */
public class ShooterPullbackPID extends PIDSubsystem {
    // Initialize your subsystem here
    public ShooterPullbackPID() {
        super("ShooterPullbackPID", SmartDashboard.getNumber("\"P\" ShooterPullbackPID", PIDConstants.SHOOTER_P), SmartDashboard.getNumber("\"I\" ShooterPullbackPID", PIDConstants.SHOOTER_I), SmartDashboard.getNumber("\"D\" ShooterPullbackPID", PIDConstants.SHOOTER_D));

        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
        return RobotMap.shooterPullbackEncoder.getRate();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        RobotMap.shooterPullbackJaguar.set(output);
    }
}
