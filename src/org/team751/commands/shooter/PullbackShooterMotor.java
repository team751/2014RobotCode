/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.shooter;

import org.team751.RobotMap;
import org.team751.commands.CommandBase;
import org.team751.subsystems.ShooterPullbackPID;

/**
 *
 * @author sambaumgarten
 */
public class PullbackShooterMotor extends CommandBase {

    public PullbackShooterMotor() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        // TODO: Set pullback point
        CommandBase.shooter.sppid.enable();
        CommandBase.shooter.sppid.setSetpoint(2);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return CommandBase.shooter.sppid.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
        CommandBase.shooter.sppid.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
