package org.team751.utils;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import org.team751.RobotMap;

/**
 * This subsystem-like class keeps track of the robot's location relative to its
 * starting point.
 *
 * The coordinate system is defined based on the initial location of the robot
 * when it is started, or when {@link #reset()} was last called. The origin of
 * this coordinate system is at the robot's initial location. It does not rotate
 * when the robot turns.
 *
 * @author Sam Crow
 */
public class Navigator extends PeriodicTask implements Sendable,
													   LiveWindowSendable {

	//Constants for measuring movement
	/**
	 * The number of encoder counts for every wheel revolution
	 */
	private static final int COUNTS_PER_REVOLUTION = 250;

	/**
	 * The diameter of the wheel, in meters
	 */
	private static final double WHEEL_DIAMETER = 0.17145;

	/**
	 * The distance, in meters, that the robot moves for each encoder count
	 */
	private static final double ROBOT_DISTANCE_PER_COUNT = (1 / (double) COUNTS_PER_REVOLUTION) * WHEEL_DIAMETER * Math.PI;
//	private ADXL345_I2C accel;

//	private Gyro gyro;
	//encoders

	Encoder leftEncoder;

	Encoder rightEncoder;

	/**
	 * The heading of the robot, in degrees to the right of its initial heading
	 */
	private volatile double heading = 0;

	/**
	 * The velocity of the robot, in meters/second
	 */
	private volatile Vec2 velocity = new Vec2();

	/**
	 * The location of the robot, in meters
	 */
	private volatile Vec2 location = new Vec2();

	/**
	 * The distance in meters that the robot has traveled forward, according to
	 * the drivetrain encoders. This is the average of the left encoder distance
	 * and the right encoder distance. Note: Each encoder must have been
	 * configured with the correct distance per pulse in meters.
	 */
	private volatile double encoderDistance = 0;

	/**
	 * The timestamp, in milliseconds, at which processing started for the
	 * previous call to {@link #run()}. This timing is used to calculate
	 * velocity and position from acceleration.
	 */
	private long lastProcessingTime = System.currentTimeMillis();

	//Differentiators, used to calculate velocity and acceleration
	private Differentiator linearVelocityDiff = new Differentiator();

	private Differentiator linearAccelerationDiff = new Differentiator();

	private Differentiator rotationalVelocityDiff = new Differentiator();

	private Differentiator rotationalAccelerationDiff = new Differentiator();

	/** Linear velocity, meters/second */
	private double linearVelocity = 0;

	/** Linear acceleration, meters/second squared */
	private double linearAcceleration = 0;

	/** Rotational velocity, degrees/second */
	private double rotationalVelocity = 0;

	/** Rotational acceleration, degrees/second squared */
	private double rotationalAcceleration = 0;
        
//        public MaxbotixUltrasonic ultrasonic = new MaxbotixUltrasonic(RobotMap.ultrasonicChannel);

	public Navigator() {
		Logger.staticPrintln("Navigator constructor called");



		Logger.staticPrintln("Starting gyro init");
		SmartDashboard.putBoolean("Gyro init", true);
//		gyro = new Gyro(RobotMap.gyroChannel);
		SmartDashboard.putBoolean("Gyro init", false);
		Logger.staticPrintln("Gyro init done");

		Logger.staticPrintln("Starting encoder init");
		leftEncoder = RobotMap.leftDriveEncoder;
		rightEncoder = RobotMap.rightDriveEncoder;
		Logger.staticPrintln("Encoder init done");

		//Set the periodic task to run this 10 times/second
		setTaskTime(0.1);

		//Configure encoders
		leftEncoder.setDistancePerPulse(ROBOT_DISTANCE_PER_COUNT);
		rightEncoder.setDistancePerPulse(ROBOT_DISTANCE_PER_COUNT);
                RobotMap.shooterPullbackEncoder.setDistancePerPulse(.1);
//                RobotMap.nommerEncoder.setDistancePerPulse(.1);
		//Reverse the right side encoder so that forward will give a positive value for both encoders
		leftEncoder.setReverseDirection(true);

		//Start counting encoder pulses
		leftEncoder.start();
		rightEncoder.start();
                RobotMap.shooterPullbackEncoder.start();         
//                RobotMap.nommerEncoder.start();
	}

	/**
	 * Set the heading, velocity, and location to zero
	 */
	public synchronized void reset() {
		heading = 0;
		velocity = new Vec2();
		location = new Vec2();
	}

	public void run() {
			long newTime = System.currentTimeMillis();
			//Get the time in seconds since processing was last done
			double timeSeconds = (newTime - lastProcessingTime) / 1000.0;

			lastProcessingTime = newTime;

			//Get the Y-axis (local to the robot, longitudinal) acceleration and convert
			//it from Gs to m/s^2
//			double accelY = accel.getAcceleration(ADXL345_I2C.Axes.kY) / 9.8;

			//update the heading
//			heading = gyro.getAngle();
//
//			//Append the velocity with the change in velocity over the last time step
//			//90 degrees is added to the heading because Navigator uses forward for 0
//			//and Vec2 uses +X for 0.
//			velocity = velocity.add(Vec2.fromAngle(heading + 90,
//												   accelY * timeSeconds));
//
//			//Append the position
//			location = location.add(velocity.multiply(timeSeconds));
//
//			//Optimization: Correct for accelerometer drift by setting velocity
//			//to zero if the encoders say that it is zero
//			if (leftEncoder.getStopped() && rightEncoder.getStopped()) {
//				velocity = new Vec2(0, 0);
//			}

			//Update the encoder distance
			encoderDistance = (leftEncoder.getDistance() + rightEncoder.
					getDistance()) / 2.0;

			//Differentiate to get the speeds/velocities
			linearVelocity = linearVelocityDiff.getDerivative(encoderDistance);
                        
			linearAcceleration = linearAccelerationDiff.getDerivative(
					linearVelocity);
			rotationalVelocity = rotationalVelocityDiff.getDerivative(
					heading);
			rotationalAcceleration = rotationalAccelerationDiff.getDerivative(
					rotationalVelocity);


			//Debug

			if (DriverStation.getInstance().isOperatorControl()) {
				SmartDashboard.putNumber("Encoder distance", encoderDistance);
			}
			//Limit heading heading to [0, 360] degrees
			double dashboardHeading = heading % 360;
			if (dashboardHeading < 0) {
				dashboardHeading += 360;
			}

			if (DriverStation.getInstance().isOperatorControl()) {
				SmartDashboard.putNumber("Heading", dashboardHeading);
			}
	}

	//Methods to access location information
	/**
	 * Get the robot heading
	 *
	 * @return the heading, in degrees
	 */
	public synchronized double getHeading() {
		return heading;
	}

	/**
	 * Get the X location of the robot
	 *
	 * @return the location, in meters
	 */
	public synchronized double getX() {
		return location.getX();
	}

	/**
	 * Get the Y location of the robot
	 *
	 * @return the location, in meters
	 */
	public synchronized double getY() {
		return location.getY();
	}

	/**
	 * Get the distance, in meters, that the robot has moved forwards or
	 * backwards since the last call to {@link #resetEncoderDistance()}.
	 *
	 * @return The distance in meters
	 */
	public synchronized double getEncoderDistance() {
		return encoderDistance;
	}

	public double getLinearAcceleration() {
		return linearAcceleration;
	}

	public double getLinearVelocity() {
		return linearVelocity;
	}

	public double getRotationalAcceleration() {
		return rotationalAcceleration;
	}

	public double getRotationalVelocity() {
		return rotationalVelocity;
	}

	/**
	 * Reset the encoder distance, returned by {@link #getEncoderDistance()}, to
	 * zero.
	 */
	public synchronized void resetEncoderDistance() {
		leftEncoder.reset();
		rightEncoder.reset();
	}
	//SmartDashboard/Live Window support section
	/**
	 * The table used to send data
	 */
	private ITable table;

	public String getName() {
		return "navigator";
	}

	public void initTable(ITable itable) {
		table = itable;
		updateTable();
	}

	public ITable getTable() {
		return table;
	}

	public String getSmartDashboardType() {
		return "navigator";
	}

	public void updateTable() {
		if (table != null) {
			table.putNumber("speed", velocity.getMagnitude());
			table.putNumber("heading", heading);
			table.putNumber("X location", location.getX());
			table.putNumber("Y location", location.getY());
		}
	}

	public void startLiveWindowMode() {
	}

	public void stopLiveWindowMode() {
	}

	/**
	 * Reset the gyroscope sensor. This should be done while the robot is not
	 * moving and will block for about 1 second.
	 */
	public synchronized void initializeGyro() {
		SmartDashboard.putBoolean("Gyro init", true);
		DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6,
											   1, "Initializing gyro");
		DriverStationLCD.getInstance().updateLCD();
//		gyro.free();
//		gyro = null;
//		gyro = new Gyro(RobotMap.gyroChannel);
		SmartDashboard.putBoolean("Gyro init", false);
		DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser6,
											   1, "Gyro done");
		DriverStationLCD.getInstance().updateLCD();
	}
	//PID sources
	/**
	 * A PID source that returns the heading, in degrees
	 */
	public final PIDSource headingPidSource = new PIDSource() {

		public double pidGet() {
			synchronized (Navigator.this) {
				return getHeading();
			}
		}
	};
        
        public final PIDSource ultrasonicPidSource = new PIDSource() {

		public double pidGet() {
			synchronized (Navigator.this) {
//				return ultrasonic.GetRangeInInches();
                            return 0;
			}
		}
	};

	/**
	 * A PID source that returns the distance that the robot has moved, as
	 * returned by {@link #getEncoderDistance() }.
	 */
	public final PIDSource movementPidSource = new PIDSource() {

		public double pidGet() {
			synchronized (Navigator.this) {
				return getEncoderDistance();
			}
		}
	};

}