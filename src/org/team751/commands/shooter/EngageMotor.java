/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.shooter;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.team751.RobotMap;
import org.team751.commands.CommandBase;

/**
 *
 * @author sambaumgarten
 */
public class EngageMotor extends CommandBase {
    
    public EngageMotor() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        RobotMap.moverSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return RobotMap.moverEngagedLimitSwitch.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
