package org.team751;

/**
 * Stores PID constants that are frequently used.
 * @author Sam Crow
 */
public class PIDConstants {
    
    /**
     * PID constant for rotating the drivetrain around in place.
     * Input is degrees of rotation, output is motor power ratio.
     * Proportional constant
     */
    public static final double DRIVE_ROTATE_P = 0.005;
    /**
     * PID constant for rotating the drivetrain around in place.
     * Input is degrees of rotation, output is motor power ratio.
     * Integral constant
     */
    public static final double DRIVE_ROTATE_I = 0.00002;
    /**
     * PID constant for rotating the drivetrain around in place.
     * Input is degrees of rotation, output is motor power ratio.
     * Derivative constant
     */
    public static final double DRIVE_ROTATE_D = 0;
    
    
    
    /**
     * PID constant for moving the robot forwards and back.
     * Input is meters, output is motor power ratio.
     * Proportional constant
     */
     public static final double DRIVE_MOVE_P = 0.08;
    /**
     * PID constant for moving the robot forwards and back.
     * Input is meters, output is motor power ratio.
     * Integral constant
     */
    public static final double DRIVE_MOVE_I = 0;
    /**
     * PID constant for moving the robot forwards and back.
     * Input is meters, output is motor power ratio.
     * Derivative constant
     */
    public static final double DRIVE_MOVE_D = 0.1;
    
    /**
     * PID constant for the left encoder
     */
     public static final double LEFT_DRIVE_P = .08;
    /**
     * PID constant for the left encoder
     */
    public static final double LEFT_DRIVE_I = 0;
    /**
     * PID constant for the left encoder
     */
    public static final double LEFT_DRIVE_D = 0.1;
    
    /**
     * PID constant for the right encoder
     */
     public static final double RIGHT_DRIVE_P = 0.08;
    /**
     * PID constant for the right encoder
     */
    public static final double RIGHT_DRIVE_I = 0;
    /**
     * PID constant for the right encoder
     */
    public static final double RIGHT_DRIVE_D = 0.1;
    
    /**
     * PID constant for the shooter encoder
     */
     public static final double SHOOTER_P = 0;
    /**
     * PID constant for the shooter encoder
     */
    public static final double SHOOTER_I = 0;
    /**
     * PID constant for the shooter encoder
     */
    public static final double SHOOTER_D = 0;
    
    //prevent the class from being instantiated
    private PIDConstants() {}
}
