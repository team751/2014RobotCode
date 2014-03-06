package org.team751.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.OI;
import org.team751.subsystems.Drivetrain;
import org.team751.subsystems.Nommer;
import org.team751.subsystems.Shooter;
import org.team751.utils.Navigator;

/**
 * The base for all commands. All atomic commands should subclass CommandBase.
 * CommandBase stores creates and stores each control system. To access a
 * subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 * @author sambaumgarten
 */
public abstract class CommandBase extends Command {

    public static OI oi;
    // Create a single static instance of all of your subsystems
    public static Drivetrain driveTrain = new Drivetrain();
    public static Shooter shooter = new Shooter();
    public static Nommer nommer = new Nommer();
    //Periodic tasks here (these are not subsystems)
    public static Navigator navigator = new Navigator();

    public static void init() {
        // This MUST be here. If the OI creates Commands (which it very likely
        // will), constructing it during the construction of CommandBase (from
        // which commands extend), subsystems are not guaranteed to be
        // yet. Thus, their requires() statements may grab null pointers. Bad
        // news. Don't move it.
        oi = new OI();
        
//        RobotMap.leftDriveEncoder.setDistancePerPulse(((6.75/2)*Math.PI*2)/1429.0);
//        RobotMap.rightDriveEncoder.setDistancePerPulse(((6.75/2)*Math.PI*2)/1429.0);

        // Show what command your subsystem is running on the SmartDashboard
        SmartDashboard.putData(driveTrain);
        
//        navigator.start();
    }

    public CommandBase(String name) {
        super(name);
    }

    public CommandBase() {
        super();
    }
}
