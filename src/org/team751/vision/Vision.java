/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.vision;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.NIVision;
import org.team751.vision.utils.TargetReport;
import org.team751.vision.VisionConstants;

/**
 *
 * @author sambaumgarten
 */
public class Vision extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    // Define Variables
    VisionNetworkTableCommunication vntc;
    cRIOVision cv;
            
    boolean useNetTable = false;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void inititalizeVision() {
        // Create a Network Table Communcations Manager
        vntc = new VisionNetworkTableCommunication();
        
        // Check if we are able to connect
        if (vntc.isConnectionAvailable()) {
            // If we are set the variable to true
            useNetTable = true;
        } else {
            // If not set it to false
            useNetTable = false;
            // Because we cannot connect, we will use the cRIO for processing
            cv = new cRIOVision();
        }
    }
    
    
}
