/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.priory751.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author sambaumgarten
 */
public class Autonomous extends CommandBase {
    
    public Autonomous() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CommandBase.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double timeDelay = 2;
        int steps = 4;
        for (int i = 0; i < steps; i++) {
            CommandBase.driveTrain.setSpeed((1.0/steps)*i);
            Timer.delay(timeDelay);
        }
        CommandBase.driveTrain.stop();
        Timer.delay(timeDelay);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        CommandBase.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
