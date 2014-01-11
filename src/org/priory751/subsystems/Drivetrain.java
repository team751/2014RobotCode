/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.priory751.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.priory751.base.RobotMap;
import org.priory751.commands.TankDrive;

/**
 *
 * @author sambaumgarten
 */
public class Drivetrain extends Subsystem {
   // Init Variables
    Jaguar leftDriveJaguar = RobotMap.leftDrivetrainJaguar;
    Jaguar rightDriveJaguar = RobotMap.rightDrivetrainJaguar;
    
    public void initDefaultCommand() {
        setDefaultCommand(new TankDrive());
    }
    
    public void tankDrive(Joystick leftJoystick, Joystick rightJoystick) {
        leftDriveJaguar.set(leftJoystick.getAxis(Joystick.AxisType.kY));
        rightDriveJaguar.set(rightJoystick.getAxis(Joystick.AxisType.kY));
    }
    
    public void setLeftSpeed(double speed) {
        leftDriveJaguar.set(speed);
    }
    
    public void setRightSpeed(double speed) {
        rightDriveJaguar.set(speed);
    }
    
    public void setSpeed(double speed) {
        setLeftSpeed(speed);
        setRightSpeed(speed);
    }
    
    public void stop() {
        leftDriveJaguar.set(0);
        rightDriveJaguar.set(0);
    }
}
