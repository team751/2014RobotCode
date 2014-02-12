/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.shooter;

import org.team751.commands.CommandBase;
import org.team751.subsystems.Shooter;

/**
 *
 * @author sambaumgarten
 */
public class CancelPullback extends CommandBase {
    
    public CancelPullback() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        CommandBase.shooter.sppid.disable();
        double position = CommandBase.shooter.sppid.getPosition();
        if (position > 0) {
            CommandBase.shooter.state = Shooter.kStateRetracted;
        } else {
            CommandBase.shooter.state = Shooter.kStateInactive;
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }
 
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
