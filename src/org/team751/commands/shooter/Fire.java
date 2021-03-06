/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.team751.commands.shooter;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team751.commands.CommandBase;
import org.team751.subsystems.Shooter;

/**
 *
 * @author sambaumgarten
 */
public class Fire extends CommandGroup {
    
    public Fire() {
        CommandBase.shooter.state = Shooter.kStateRetracted;
        addSequential(new UnlockShooter());
        
        CommandBase.shooter.state = Shooter.kStateInactive;
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
