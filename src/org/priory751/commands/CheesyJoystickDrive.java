package org.priory751.commands;

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
        double x = oi.leftJoystick.getX();
        double y = oi.leftJoystick.getY();
        
	// Use simple, non-cheesy drive code if button 3 is pressed
        boolean simpleDrive = oi.leftJoystick.getRawButton(3);
        
	// If the robot should only be allowed to drive straight
        boolean straight = oi.leftJoystick.getRawButton(2);
	if (straight) {
            // disable turning
            x = 0;
	}
        
        //For the joystick Y axis, forward is negative.
        //This changes it back so that forward is positive.
        y = -y;
        
	if(simpleDrive) {
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
