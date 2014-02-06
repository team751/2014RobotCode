package org.team751;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import org.team751.subsystems.Drivetrain;

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
    
    // PNEUMATICS
    /**
     * The port the pressure switch is on
     */
    public static final int pressureSwitchInput = 2;
    
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
    
    // Encoders
    public static final Encoder leftDriveEncoder = new Encoder(RobotMap.leftDriveEncoderChannelA, RobotMap.leftDriveEncoderChannelB);
    public static final Encoder rightDriveEncoder = new Encoder(RobotMap.rightDriveEncoderChannelA, RobotMap.rightDriveEncoderChannelB);
    
    // Pneumatics
    public static final Compressor compressor = new Compressor(pressureSwitchInput, compressorRelay);
}
