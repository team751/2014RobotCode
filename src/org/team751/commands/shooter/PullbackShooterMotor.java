/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.shooter;

import org.team751.RobotMap;
import org.team751.commands.CommandBase;
import org.team751.subsystems.Shooter;
import org.team751.utils.Logger;

/**
 *
 * @author sambaumgarten
 */
public class PullbackShooterMotor extends CommandBase {
    double startDistance;
    boolean complete;

    public PullbackShooterMotor() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        // TODO: Set pullback point
//        startDistance = RobotMap.shooterPullbackEncoder.getDistance();
        startDistance = 0;
//        CommandBase.shooter.sppid.enable();
//        CommandBase.shooter.sppid.setAbsoluteTolerance(0.1);
//        CommandBase.shooter.sppid.setSetpoint(5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if ((RobotMap.shooterPullbackEncoder.getDistance()-startDistance) < 2 && CommandBase.shooter.state == Shooter.kStateRetracting) {
            RobotMap.shooterPullbackJaguar.set(.1);
        } else {
            complete = true;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return complete;
    }

    // Called once after isFinished returns true
    protected void end() {
        RobotMap.shooterPullbackJaguar.set(0);
        CommandBase.shooter.state = Shooter.kStateRetracted;
        Logger.staticPrintln("DONE", Logger.ANSI_CYAN);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
