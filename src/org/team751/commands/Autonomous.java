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
        VisionNetworkTableCommunication.setDesiredDistance(95);
        VisionNetworkTableCommunication.setDesiredDistanceEpsilon(6);
        VisionNetworkTableCommunication.setSlowDownDistance(30);
        VisionNetworkTableCommunication.setMaxAngle(5);
        VisionNetworkTableCommunication.setMaxForwardSpeed(.4);
        VisionNetworkTableCommunication.setMinForwardSpeed(.1);
        VisionNetworkTableCommunication.setMaxAngleSpeed(.2);
        VisionNetworkTableCommunication.setLiveMode(true);
        VisionNetworkTableCommunication.setShootMode(true);
        VisionNetworkTableCommunication.setStrafeMode(false);
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
        
        VisionDistanceCalculations vdc = new VisionDistanceCalculations(VisionDistanceCalculations.RoboRealmVision);
        VisionAngleCalculations vac = new VisionAngleCalculations();
        
        double distanceToGoal = vdc.getDistanceToGoal();
        double angleToGoal = vac.getAngleToGoal(Robot.lastTarget);
        
        if (autonomousTimer.get() < 0.5) {
            // Start nommer
        } else {
            // Disable nommer
        }
        
        if (distanceToGoal > 0.0) { // if we have a valid distance
            double deltaDistance = distanceToGoal - VisionNetworkTableCommunication.getDesiredDistance();
            // drive to goal
            if (Math.abs(deltaDistance) < VisionNetworkTableCommunication.getDesiredDistanceEpsilon()) {
                // in range, FIRE!
                
                if (LIVE_MODE)
                    CommandBase.driveTrain.tankDrive(0, 0);
                
                Timer.delay(.5);
                
                if (autonomousTimer.get() > 5.0 || Robot.lastTarget.Hot) {
                    // fire
                    System.out.println("in firing position");
                    if (!AT_FIRING_DISTANCE) {
                        firingDistanceTime = autonomousTimer.get();
                        AT_FIRING_DISTANCE = true;
                    }
                    if (autonomousTimer.get() > 2.0 && firingDistanceTime > 0 &&
                            autonomousTimer.get() > firingDistanceTime + 1.0) {
                        System.out.println("fire!!!");
                        if (SHOOT_MODE) {
                            // Add shooting code here
                        }
                        FIRED = true;
                    }
                }
                else {
                    
                }
            }
            else if (Math.abs(deltaDistance) < VisionNetworkTableCommunication.getSlowDownDistance()) {
                double scale = deltaDistance / VisionNetworkTableCommunication.getSlowDownDistance(); // between -1 and 1
                double range = VisionNetworkTableCommunication.getMaxForwardSpeed() - VisionNetworkTableCommunication.getMinForwardSpeed();
                double desiredSpeed = (minSpeed + range) * scale;
                if (LIVE_MODE)
                    CommandBase.driveTrain.tankDrive(1, 1);
            }
            else {
                double sign = deltaDistance > 0 ? 1 : -1;
                double angleSign = 0;
                if (Math.abs(angleToGoal) > maxAngleToGoal) {
                    // Turn towards the goal so it stays in view
                    angleSign = angleToGoal > 0 ? 1 : -1;
                }
                //chassisDrive.tankDrive(sign * maxSpeed, sign * maxSpeed);
                if (LIVE_MODE) {
                    if (STRAFE_MODE) {
                        CommandBase.driveTrain.cheesyDrive(angleSign, 1.0, false);
                    }
                    else {
                        CommandBase.driveTrain.cheesyDrive(angleSign, 1.0, false);
                    }
                }
                System.out.println("driving towards position: " + sign * maxForwardSpeed + " angle: " + angleSign * maxAngleSpeed);
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
