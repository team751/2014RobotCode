/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.RobotMap;

/**
 *
 * @author sambaumgarten
 */
public class Nommer extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public double getLoweredValue() {
        return SmartDashboard.getNumber("nommerLoweredValue");
    }
    
    public double getRaisedValue() {
        return SmartDashboard.getNumber("nommerRaisedValue");
    }
    
    public double getGameStartValue() {
        return SmartDashboard.getNumber("nommerGameStartValue");
    }
    
    public double getNommerSpeed() {
        return SmartDashboard.getNumber("nommerSpeed");
    }
    
    public void setSpeed(double speed) {
        RobotMap.nommerLeftJaguar.set(speed);
        RobotMap.nommerRightJaguar.set(speed);
    }
}
