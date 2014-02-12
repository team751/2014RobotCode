package org.team751;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;
    
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
    
    // Connections
    
    // MOTORS
    /**
     * The PWM connector the left drivetrain motors are attached to
     */
    public static final int leftDrivePWM = 1;
    /**
     * The PWM connector the right drivetrain motors are attached to
     */
    public static final int rightDrivePWM = 2;
    /**
     * The PWM connector the shooter pullback motors are attached to
     */
    public static final int shooterPullbackPWM = 3;
    
    // RELAYS
    /**
     * The relay connector the compressor is attached to
     */
    public static final int compressorRelay = 3;
    
    // ENCODERS
    /**
     * The digital input the left drivetrain encoder A is attached to
     */
    public static final int leftDriveEncoderChannelA = 1;
    /**
     * The digital input the left drivetrain encoder B is attached to
     */
    public static final int leftDriveEncoderChannelB = 2;
    /**
     * The digital input the right drivetrain encoder A is attached to
     */
    public static final int rightDriveEncoderChannelA = 3;
    /**
     * The digital input the right drivetrain encoder B is attached to
     */
    public static final int rightDriveEncoderChannelB = 4;
    
    // LIMIT SWITCHES
    public static final int moverEngagedLimitSwitchChannel = 5;
    public static final int moverDisengagedLimitSwitchChannel = 6;
    
    // PNEUMATICS
    /**
     * The port the pressure switch is on
     */
    public static final int pressureSwitchInput = 2;
    /**
     * This is the port the mover solenoid is on (for forward)
     */
    public static final int moverSolenoidForwardChannel = 1;
    /**
     * This is the port the mover solenoid is on (for reverse)
     */
    public static final int moverSolenoidReverseChannel = 2;
    /**
     * This is the port the lock solenoid is on (for forward)
     */
    public static final int lockSolenoidForwardChannel = 1;
    /**
     * This is the port the lock solenoid is on (for reverse)
     */
    public static final int lockSolenoidReverseChannel = 2;
    
    // NAVIGATION
    /**
     * The digital inputs the gyro is attached to
     */
    public static final int gyroChannel = 1;
    /**
     * The analog input the ultrasonic sensor is attached to
     */
    public static final int ultrasonicChannel = 1;
    
    
    // Speed Controllers
    public static final Jaguar leftDrivetrainJaguar = new Jaguar(RobotMap.leftDrivePWM);
    public static final Jaguar rightDrivetrainJaguar = new Jaguar(RobotMap.rightDrivePWM);
    public static final Jaguar shooterPullbackJaguar = new Jaguar(RobotMap.shooterPullbackPWM);
    public static CANJaguar leftDrivetrain1CANJaguar;
    public static CANJaguar leftDrivetrain2CANJaguar;
    public static CANJaguar leftDrivetrain3CANJaguar;
    public static CANJaguar rightDrivetrain1CANJaguar;
    public static CANJaguar rightDrivetrain2CANJaguar;
    public static CANJaguar rightDrivetrain3CANJaguar;
    
    // Encoders
    public static final Encoder leftDriveEncoder = new Encoder(RobotMap.leftDriveEncoderChannelA, RobotMap.leftDriveEncoderChannelB);
    public static final Encoder rightDriveEncoder = new Encoder(RobotMap.rightDriveEncoderChannelA, RobotMap.rightDriveEncoderChannelB);
    
    // Limit Switches
    public static final DigitalInput moverEngagedLimitSwitch = new DigitalInput(RobotMap.moverEngagedLimitSwitchChannel);
    public static final DigitalInput moverDisengagedLimitSwitch = new DigitalInput(RobotMap.moverDisengagedLimitSwitchChannel);
    
    // Pneumatics
    public static final Compressor compressor = new Compressor(pressureSwitchInput, compressorRelay);
    public static final DoubleSolenoid moverSolenoid = new DoubleSolenoid(RobotMap.moverSolenoidForwardChannel, RobotMap.moverSolenoidReverseChannel);
    public static final DoubleSolenoid lockSolenoid = new DoubleSolenoid(RobotMap.lockSolenoidForwardChannel, RobotMap.lockSolenoidReverseChannel);

    
    public RobotMap() {
        if (SmartDashboard.getBoolean("CAN Enabled", true)) {
            try {
                if (leftDrivetrain1CANJaguar == null)
                    leftDrivetrain1CANJaguar = new CANJaguar(2);
                
                if (leftDrivetrain2CANJaguar == null)
                    leftDrivetrain2CANJaguar = new CANJaguar(3);
                
                if (leftDrivetrain3CANJaguar == null)
                    leftDrivetrain3CANJaguar = new CANJaguar(4);
                
                if (rightDrivetrain1CANJaguar == null)
                    rightDrivetrain1CANJaguar = new CANJaguar(5);
                
                if (rightDrivetrain2CANJaguar == null)
                    rightDrivetrain2CANJaguar = new CANJaguar(6);
                
                if (rightDrivetrain3CANJaguar == null)
                    rightDrivetrain3CANJaguar = new CANJaguar(7);
            } catch (CANTimeoutException e) {
                
            }
        }
    }
}
