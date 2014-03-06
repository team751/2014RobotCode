/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.nommer;

import org.team751.RobotMap;
import org.team751.commands.CommandBase;

/**
 *
 * @author sambaumgarten
 */
public class AutoLift extends CommandBase {
    
    double firstTriggerTime = -1;
    boolean firstSensorTriggered = false;
    
    double timeBetweenSensors = -1;
    double velocity = -1;
    
    public AutoLift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//        if (firstSensorTriggered && RobotMap.firstNommerSwitch.get() && !RobotMap.secondNommerSwitch.get()) {
//            firstSensorTriggered = false;
//            firstTriggerTime = -1;
//            timeBetweenSensors = -1;
//            velocity = -1;
//        }
//        if (RobotMap.secondNommerSwitch.get() && firstSensorTriggered) {
//            timeBetweenSensors = System.currentTimeMillis() - firstTriggerTime;
//        }
//        if (RobotMap.firstNommerSwitch.get()) {
//            firstSensorTriggered = true;
//            firstTriggerTime = System.currentTimeMillis();
//        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
