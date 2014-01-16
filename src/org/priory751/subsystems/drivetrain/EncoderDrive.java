/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.priory751.subsystems.drivetrain;

import org.priory751.base.RobotMap;
import org.priory751.subsystems.Drivetrain;

/**
 *
 * @author sambaumgarten
 */
public class EncoderDrive implements Runnable {
    Drivetrain drivetrain;
    double inches;
    
    public EncoderDrive(Drivetrain drivetrainTemp, double inchesTemp) {
        drivetrain = drivetrainTemp;
        inches = inchesTemp;
    }
    
    public void run() {
        // Reset the encoders
        RobotMap.leftDriveEncoder.reset();
        RobotMap.rightDriveEncoder.reset();
        
        // While the average distance is less than the number of inches, keep going
        while(((drivetrain.leftDriveEncoder.getDistance()+drivetrain.rightDriveEncoder.getDistance())/2) < inches) {
            drivetrain.tankDrive(1, 1);
            
            // If the drivetrain sets a stop, stop the method
            if (drivetrain.shouldStop) {
                break;
            }
        }
        
        // Reset the encoders
        RobotMap.leftDriveEncoder.reset();
        RobotMap.rightDriveEncoder.reset();
    }
    
}
