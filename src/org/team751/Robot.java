/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team751;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team751.commands.Autonomous;
import org.team751.commands.CommandBase;
import org.team751.commands.calibration.CalibrateNommer;
import org.team751.commands.calibration.CalibrateShooter;
import org.team751.utils.Diagnostic;
import org.team751.utils.Logger;
import org.team751.vision.utils.TargetReport;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    Command autonomousCommand;
    public static TargetReport lastTarget;
    
//    Relay relay = new Relay(1,3);

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Run diagnostics
        Diagnostic.runAllDiagnosticTests();
        
        // instantiate the command used for the autonomous period
        autonomousCommand = new Autonomous();
        lastTarget = new TargetReport();
        
        SmartDashboard.putNumber("autonomousMode", 0);
        
        SmartDashboard.putData("Calibrate Shooter", new CalibrateShooter());
        SmartDashboard.putData("Calibrate Nommer", new CalibrateNommer());

        // Start compressor
//        RobotMap.compressor.setRelayValue(Relay.Value.kForward);
        

        // Initialize all subsystems
        CommandBase.init();
    }

    /**
     * Called when autonomous mode starts
     */
    public void autonomousInit() {
        // schedule the autonomous command (example)
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        
        CommandBase.navigator.run();
    }

    /**
     * Called when teleop mode starts
     */
    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        autonomousCommand.cancel();
        
//        relay.set(Relay.Value.kOn);
        RobotMap.compressor.start();

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        CommandBase.navigator.run();
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    public void disabledInit() {
        
    }
    
    public void disabledPeriodic() {
        CommandBase.navigator.run();
    }
}
