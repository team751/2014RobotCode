/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.RobotMap;
import org.team751.commands.nommer.NommerControl;

/**
 *
 * @author sambaumgarten
 */
public class Nommer extends Subsystem {
    
    public double currentSetValue;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new NommerControl());
    }
    
    public double getLoweredValue() {
        return SmartDashboard.getNumber("nommerLoweredValue", 0);
    }
    
    public double getRaisedValue() {
        return SmartDashboard.getNumber("nommerRaisedValue", 100);
    }
    
    public double getGameStartValue() {
        return SmartDashboard.getNumber("nommerGameStartValue");
    }
    
    public double getNommerSpeed() {
        return SmartDashboard.getNumber("nommerSpeed", 0.1);
    }
    
    public void setSpeed(double speed) {
        RobotMap.nommerLeftJaguar.set(speed);
        RobotMap.nommerRightJaguar.set(speed);
    }
}
