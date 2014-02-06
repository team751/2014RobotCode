/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.drivetrain;

import org.team751.base.Robot;
import org.team751.commands.CommandBase;
import org.team751.vision.VisionAngleCalculations;
import org.team751.vision.VisionNetworkTableCommunication;

/**
 *
 * @author sambaumgarten
 */
public class HoldAngle extends CommandBase {   
    public double lastAngle = 0;
    public boolean rotateLeft = false;
    
    private final double angleToHold;
    
    public HoldAngle(double angleToHoldTemp) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CommandBase.driveTrain);
        
        angleToHold = angleToHoldTemp;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        VisionNetworkTableCommunication vntc = new VisionNetworkTableCommunication();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double angle = VisionAngleCalculations.getAngleToGoal(Robot.lastTarget);

        
        // If the direction is oscillating, just stop the robot
        if ((angle > angleToHold && lastAngle < angleToHold) || (angle < angleToHold && lastAngle > angleToHold)) {
            CommandBase.driveTrain.tankDrive(0.0, 0.0);
            lastAngle = angle;
            return;
        }
        
        // Any angle within 2º is fine (also helps prevent oscillation)
        float delta = (float)(angleToHold - angle);
        if (Math.abs(delta) < 2) {
            CommandBase.driveTrain.tankDrive(0.0, 0.0);
            lastAngle = angle;
            return;
        }
        
        // Determine if the robot is turning the correct direction
        if (angle > angleToHold+1) {
            if (angle > lastAngle) {
                rotateLeft = !rotateLeft;
            }
        } else if (angle < angleToHold-1) {
            if (angle < lastAngle) {
                rotateLeft = !rotateLeft;
            }
        }
        
        // Rotate the robot in the correct direction
        CommandBase.driveTrain.tankDrive(.5*(rotateLeft == true ? -1 : 1), .5*(rotateLeft == false ? -1 : 1));
        /* This is the same as:
        if (rotateLeft) {
            CommandBase.driveTrain.tankDrive(-.5, .5);
        } else {
            CommandBase.driveTrain.tankDrive(-.5, .5);
        }
        */
        
        // Set the angle
        lastAngle = angle;
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
        end();
    }
}
