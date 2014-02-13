/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems.drivetrain;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.PIDConstants;
import org.team751.RobotMap;
import org.team751.commands.CommandBase;

/**
 *
 * @author sambaumgarten
 */
public class LeftDriveTrainPID extends PIDSubsystem {
    // Initialize your subsystem here
    public LeftDriveTrainPID() {
        super("LeftDriveTrainPID", SmartDashboard.getNumber("\"P\" LeftDriveTrainPID", PIDConstants.LEFT_DRIVE_P), SmartDashboard.getNumber("\"I\" LeftDriveTrainPID", PIDConstants.LEFT_DRIVE_I), SmartDashboard.getNumber("\"D\" LeftDriveTrainPID", PIDConstants.LEFT_DRIVE_D));

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
        return RobotMap.leftDriveEncoder.getRate();
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
        CommandBase.driveTrain.setLeftSpeed(output);
    }
}
