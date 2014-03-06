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
public class GameStartNommer extends CommandBase {
    double startDistance;
    boolean complete;
    
    public GameStartNommer() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//        startDistance = RobotMap.nommerEncoder.getDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // TODO: set correct nommer height
//        double d = (RobotMap.nommerEncoder.getDistance()-startDistance);
//        if (d < 0) {
//            d = d*-1;
//        }
//        if (d < NommerConstants.nommerGameStartValue) {
//            if (RobotMap.nommerEncoder.getDistance() < NommerConstants.nommerGameStartValue) {
//                RobotMap.nommerLeftJaguar.set(0.25);
//                RobotMap.nommerRightJaguar.set(0.25);
//            } else {
//                RobotMap.nommerLeftJaguar.set(-0.25);
//                RobotMap.nommerRightJaguar.set(-0.25);
//            }
//        } else {
//            complete = true;
//        }
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
