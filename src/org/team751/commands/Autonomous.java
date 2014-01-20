/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands;

import edu.wpi.first.wpilibj.Timer;
import org.team751.base.Robot;
import org.team751.vision.VisionAngleCalculations;
import org.team751.vision.VisionDistanceCalculations;
import org.team751.vision.VisionNetworkTableCommunication;

/**
 *
 * @author sambaumgarten
 */
public class Autonomous extends CommandBase {
    Timer autonomousTimer;
    
    VisionNetworkTableCommunication vntc = new VisionNetworkTableCommunication();
    
    double desiredDistance;
    double desiredDistanceEpsilon;
    double slowDownDistance;
    double maxAngleToGoal;
    double maxForwardSpeed;
    double minSpeed;
    double maxAngleSpeed;
    
    boolean LIVE_MODE = true;
    boolean SHOOT_MODE = true;
    boolean STRAFE_MODE = false;
    boolean AT_FIRING_DISTANCE = false;
    double firingDistanceTime = -1;
    boolean FIRED = false;
    
    public Autonomous() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CommandBase.driveTrain);
        
        autonomousTimer = new Timer();
        
        vntc.setDesiredDistance(95); // Inches
        vntc.setDesiredDistanceEpsilon(6);
        vntc.setSlowDownDistance(30);
        vntc.setMaxAngle(5);
        vntc.setMaxForwardSpeed(.4);
        vntc.setMinForwardSpeed(.1);
        vntc.setMaxAngleSpeed(.2);
        vntc.setLiveMode(true);
        vntc.setShootMode(true);
        vntc.setStrafeMode(false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        autonomousTimer.reset();
        autonomousTimer.start();
        FIRED = false;
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // Drive forward 5 feet
//        CommandBase.driveTrain.drive(60);
                
        double distanceToGoal = VisionDistanceCalculations.getDistanceToGoal(VisionDistanceCalculations.RoboRealmVision);
        double angleToGoal = VisionAngleCalculations.getAngleToGoal(Robot.lastTarget);
        
        if (autonomousTimer.get() < 0.5) {
            // Start nommer
        } else {
            // Disable nommer
        }
        
        // Check if distance exists and is valid
        if (distanceToGoal > 0.0) {
            // Calculalte the delta
            double deltaDistance = distanceToGoal - vntc.getDesiredDistance();
            // Check if it is at the correct distance to fire
            if (Math.abs(deltaDistance) < vntc.getDesiredDistanceEpsilon()) {                
                // If the robot is live, stop the drivetrain
                if (LIVE_MODE) {
                    CommandBase.driveTrain.tankDrive(0, 0);
                }
                
                // Wait half a second
                Timer.delay(.5);
                
                // If the autonomous timer is greater than 5 seconds (because
                // if we are in the last 5 seconds and the target's not hot,
                // it means we missed the first one) OR the target is hot
                if (autonomousTimer.get() > 5.0 || Robot.lastTarget.Hot) {
                    // Fire
                    System.out.println("Ready to fire");
                    // If not at the correct distance
                    if (!AT_FIRING_DISTANCE) {
                        // Setup some variables in order to figure out if the
                        // robot should shoot
                        firingDistanceTime = autonomousTimer.get();
                        AT_FIRING_DISTANCE = true;
                    }
                    
                    // Check if we have time to fire
                    if (autonomousTimer.get() > 2.0 && firingDistanceTime > 0 &&
                            autonomousTimer.get() > firingDistanceTime + 1.0) {
                        System.out.println("Firing");
                        // Check if we should shoot
                        if (SHOOT_MODE) {
                            // TODO: Add shooting code here
                        }
                        FIRED = true;
                    }
                } else {
                    
                }
            // Check if we are running out of space to slowdown
            } else if (Math.abs(deltaDistance) < vntc.getSlowDownDistance()) {
                double scale = deltaDistance / vntc.getSlowDownDistance(); // between -1 and 1
                double range = vntc.getMaxForwardSpeed() - vntc.getMinForwardSpeed();
                double desiredSpeed = (minSpeed + range) * scale;
                if (LIVE_MODE) {
                    // TODO: add code to drive at a specific speed
                }
            // Calculate correct angle
            } else {
                // Calculate the angle to drive at
                double sign = deltaDistance > 0 ? 1 : -1;
                double angleSign = 0;
                if (Math.abs(angleToGoal) > maxAngleToGoal) {
                    // Turn towards the goal so it stays in view
                    angleSign = angleToGoal > 0 ? 1 : -1;
                }
                
                if (LIVE_MODE) {
                    if (STRAFE_MODE) {
                        // TODO: add correct driving code
                        //chassisDrive.tankDrive(sign * maxSpeed, sign * maxSpeed);
//                        CommandBase.driveTrain.cheesyDrive(angleSign, 1.0, false);
                    } else {
                        // Add correct driving code
//                        CommandBase.driveTrain.cheesyDrive(angleSign, 1.0, false);
                    }
                }
                System.out.println("Driving towards: " + sign * maxForwardSpeed + " Angle: " + angleSign * maxAngleSpeed);
            }
        }
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
