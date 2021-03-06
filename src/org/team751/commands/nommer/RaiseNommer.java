/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.nommer;

import org.team751.RobotMap;
import org.team751.commands.CommandBase;
import org.team751.subsystems.Shooter;
import org.team751.utils.Logger;

/**
 *
 * @author sambaumgarten
 */
public class RaiseNommer extends CommandBase {
    double startDistance;
    boolean complete;
    
    public RaiseNommer() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//        startDistance = RobotMap.nommerEncoder.getDistance();
        
        CommandBase.nommer.currentSetValue = CommandBase.nommer.getRaisedValue();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double delta = Math.abs(RobotMap.nommerEncoder.getDistance()-CommandBase.nommer.getRaisedValue());
        if (delta < 2) {
            complete = true;
        } else if (RobotMap.nommerEncoder.getDistance() > CommandBase.nommer.getRaisedValue()) {
            CommandBase.nommer.setSpeed(-1*CommandBase.nommer.getNommerSpeed());
        } else if (RobotMap.nommerEncoder.getDistance() < CommandBase.nommer.getRaisedValue()) {
            CommandBase.nommer.setSpeed(CommandBase.nommer.getNommerSpeed());
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return complete;
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.nommerLeftJaguar.set(0);
        RobotMap.nommerRightJaguar.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
