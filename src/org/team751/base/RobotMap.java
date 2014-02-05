package org.team751.base;

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
    public static final int leftDrivePWM = 1;
    public static final int rightDrivePWM = 2;
    public static final int leftDriveEncoderChannelA = 1;
    public static final int leftDriveEncoderChannelB = 2;
    public static final int rightDriveEncoderChannelA = 3;
    public static final int rightDriveEncoderChannelB = 4;
    public static final int gyroChannel = 1;
    public static final int ultrasonicPingChannel = 2;
    public static final int ultrasonicEchoChannel = 3;
    
    // Speed Controllers
    public static final Jaguar leftDrivetrainJaguar = new Jaguar(RobotMap.leftDrivePWM);
    public static final Jaguar rightDrivetrainJaguar = new Jaguar(RobotMap.rightDrivePWM);
    
    // Encoders
    public static final Encoder leftDriveEncoder = new Encoder(RobotMap.leftDriveEncoderChannelA, RobotMap.leftDriveEncoderChannelB);
    public static final Encoder rightDriveEncoder = new Encoder(RobotMap.rightDriveEncoderChannelA, RobotMap.rightDriveEncoderChannelB);
}
