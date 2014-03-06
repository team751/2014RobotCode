/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.shooter;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team751.RobotMap;
import org.team751.commands.CommandBase;
import org.team751.subsystems.Shooter;
import org.team751.utils.Logger;

/**
 *
 * @author sambaumgarten
 */
public class RetractShooter extends CommandGroup {
    
    public RetractShooter() {
        Logger.staticPrintln("CommandBase.shooter.state: " + CommandBase.shooter.state);
        if (CommandBase.shooter.state == Shooter.kStateInactive) {
            // Set the state
            CommandBase.shooter.state = Shooter.kStateRetracting;
            
            Logger.staticPrintln("unlocking shooter");
            // Ensure the lock is released
            addSequential(new UnlockShooter());
            
            Logger.staticPrintln("engaging motor");
            // Engage the motor gear
            addSequential(new EngageMotor());
            
            // Pullback the shooter
            addSequential(new PullbackShooterMotor());
            
            Logger.staticPrintln("locking shooter");
            // Lock the shooter in place
            addSequential(new LockShooter());
            
//            Timer.delay(2);
            
            Logger.staticPrintln("disengaging motor shooter");
            // Disengage the motor
            addSequential(new DisengageMotor());
            
            // Set the state
            CommandBase.shooter.state = Shooter.kStateRetracted;
        }
        
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
