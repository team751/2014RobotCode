/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.subsystems;

import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.networktables2.util.List;
import org.team751.RobotMap;

/**
 *
 * @author sambaumgarten
 */
public class Status extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void updateStatus() throws CANTimeoutException {
        NetworkTable jags = NetworkTable.getTable("diagnosticStatus");

        double temperatureL1 = RobotMap.leftDrivetrain1CANJaguar.getTemperature();
        double busVoltageL1 = RobotMap.leftDrivetrain1CANJaguar.getBusVoltage(); // input voltage
        double outputCurrentL1 = RobotMap.leftDrivetrain1CANJaguar.getOutputCurrent();
        double outputVoltageL1 = RobotMap.leftDrivetrain1CANJaguar.getOutputVoltage();
        int firmwareVersionL1 = RobotMap.leftDrivetrain1CANJaguar.getFirmwareVersion();
        byte hardwareVersionL1 = RobotMap.leftDrivetrain1CANJaguar.getHardwareVersion();
        short faultsL1 = RobotMap.leftDrivetrain1CANJaguar.getFaults();
        String[] jagL1 = {String.valueOf(temperatureL1), String.valueOf(busVoltageL1), String.valueOf(outputCurrentL1), String.valueOf(outputVoltageL1), String.valueOf(firmwareVersionL1), String.valueOf(hardwareVersionL1), String.valueOf(faultsL1)};
        
        jags.putValue("1", jagL1);
        
        double temperatureL2 = RobotMap.leftDrivetrain2CANJaguar.getTemperature();
        double busVoltageL2 = RobotMap.leftDrivetrain2CANJaguar.getBusVoltage(); // input voltage
        double outputCurrentL2 = RobotMap.leftDrivetrain2CANJaguar.getOutputCurrent();
        double outputVoltageL2 = RobotMap.leftDrivetrain2CANJaguar.getOutputVoltage();
        int firmwareVersionL2 = RobotMap.leftDrivetrain2CANJaguar.getFirmwareVersion();
        byte hardwareVersionL2 = RobotMap.leftDrivetrain2CANJaguar.getHardwareVersion();
        short faultsL2 = RobotMap.leftDrivetrain2CANJaguar.getFaults();
        String[] jagL2 = {String.valueOf(temperatureL2), String.valueOf(busVoltageL2), String.valueOf(outputCurrentL2), String.valueOf(outputVoltageL2), String.valueOf(firmwareVersionL2), String.valueOf(hardwareVersionL2), String.valueOf(faultsL2)};
        
        jags.putValue("2", jagL2);
    }
}
