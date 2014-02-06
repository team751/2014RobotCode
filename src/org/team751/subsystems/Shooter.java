/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author sambaumgarten
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public final static int kStateRetracted = 0;
    public final static int kStateRetracting = 1;
    public final static int kStateInactive = 2;
    
    public int state = Shooter.kStateInactive;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    
}
