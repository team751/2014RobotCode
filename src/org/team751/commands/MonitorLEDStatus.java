/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import org.team751.subsystems.Shooter;

/**
 *
 * @author sambaumgarten
 */
public class MonitorLEDStatus extends CommandBase {
    
    DriverStation ds;
    
    public MonitorLEDStatus() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        ds = DriverStation.getInstance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (CommandBase.shooter.state == Shooter.kStateRetracting) {
            sendI2CCommand(5);
        }
        
        // Check FMS Connected
        if (!ds.isFMSAttached()) {
            // Send no connect command
            sendI2CCommand(4);
            return;
        }
        
        // Based on alliance
        DriverStation.Alliance alliance = ds.getAlliance();
        int allianceValue = alliance.value;
        if (allianceValue == 0) {
            // Send red command
            sendI2CCommand(1);
        } else if (allianceValue == 1) {
            // Send blue command
            sendI2CCommand(2);
        }  else if (allianceValue == 2) {
            // Send invalid alliance command
            sendI2CCommand(3);
        } else {
            // flash red because something is wrong
            sendI2CCommand(4);
        }
    }
    
    public void sendI2CCommand(int data) {
        // 0: off
        // 1: red
        // 2: blue
        // 3: fading between red and blue
        // 4: flashing red
        // 5: orange/yellow fade
        byte[] command = new byte[1];
        
        command[0] = Byte.parseByte(String.valueOf(data));
        
        DigitalModule.getInstance(1).getI2C(1).setCompatabilityMode(true);
//        DigitalModule.getInstance(1).getI2C(1).write(4, data);
        DigitalModule.getInstance(1).getI2C(1).transaction(command, 1, null, 0);
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
    }
}
