package org.team751.commands.drivetrain;

import edu.wpi.first.wpilibj.Relay;
import org.team751.RobotMap;
import org.team751.commands.CommandBase;

/**
 *
 * @author samcrow
 */
public class CheesyJoystickDrive extends CommandBase {
    
    public CheesyJoystickDrive() {
        requires(driveTrain);
    }
    
    protected void initialize() {
        
    }

    protected void execute() {
        RobotMap.compressor.start();
        
        
        
        double x = oi.driverJoystick.getX();
        double y = oi.driverJoystick.getY();
        
	// Use simple, non-cheesy drive code if button 3 is pressed
        boolean simpleDrive = oi.driverJoystick.getRawButton(3);
        
	// If the robot should only be allowed to drive straight
        boolean straight = oi.driverJoystick.getRawButton(2);
	if (straight) {
            // disable turning
            x = 0;
	}
        
        //For the joystick Y axis, forward is negative.
        //This changes it back so that forward is positive.
        y = -y;
        
	if (simpleDrive) {
		driveTrain.arcadeDrive(y, x);
	} else {
            // quickTurn seems to rotate back once the joystick is released,
            // so don't use it
            driveTrain.cheesyDrive(y, x, false);
        }
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        //Stop the drivetrain
        driveTrain.arcadeDrive(0, 0);
    }

    protected void interrupted() {
        end();
    }
    
}
