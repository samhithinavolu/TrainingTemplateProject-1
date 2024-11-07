// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsytems;


import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase{
  // --------- Declare any additional variables needed here ---------
  
  // 3 meters per second.
  public double kMaxSpeed = 4.0;
  // 1/2 rotation per second.
  public double kMaxAngularSpeed = Math.PI/2;


  // --------- You don't need to change anything until after the next comment ---------
  private static final double kTrackWidth = 0.381 * 2;
  private static final double kWheelRadius = 0.0508;
  private static final int kEncoderResolution = -4096;

  private final PWMSparkMax leftMotor = new PWMSparkMax(0);
  private final PWMSparkMax rightMotor = new PWMSparkMax(0);

  private final Encoder leftEncoder,rightEncoder;
  private final EncoderSim leftEncoderSim, rightEncoderSim;
  
  private final AnalogGyro gyro = new AnalogGyro(0);
  private final AnalogGyroSim gyroSim = new AnalogGyroSim(gyro);

  private final DifferentialDriveOdometry odometry;

  private final Field2d fieldSim = new Field2d();
  private final LinearSystem<N2, N2, N2> drivetrainSystem =
      LinearSystemId.identifyDrivetrainSystem(1.98, 0.2, 1.5, 0.3);
  private final DifferentialDrivetrainSim drivetrainSimulator =
      new DifferentialDrivetrainSim(
          drivetrainSystem, DCMotor.getCIM(1), 8, kTrackWidth, kWheelRadius, null);
  

  public Drivetrain(int leftEncA, int leftEncB, int rightEncA, int rightEncB) {

    leftEncoder = new Encoder(leftEncA,leftEncB);
    rightEncoder = new Encoder(rightEncA, rightEncB);

    odometry =
      new DifferentialDriveOdometry(
          gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());

    diffDrive = new DifferentialDrive(leftMotor, rightMotor);

    leftEncoderSim = new EncoderSim(leftEncoder);
    rightEncoderSim = new EncoderSim(rightEncoder);
   
    rightMotor.setInverted(true);
    leftEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);
    rightEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);

    leftEncoder.reset();
    rightEncoder.reset();

    SmartDashboard.putData("Field", fieldSim);
  }
  // --------- All of your other changes should be after this point ---------



  /**
   * Controls the robot using arcade drive.
   *
   * @param xSpeed the speed for the x axis from -1 to 1
   * @param rot the rotation from -1 to 1
   */
  public void drive(DoubleSupplier xSpeed, DoubleSupplier rot){
    diffDrive.arcadeDrive(xSpeed.getAsDouble()*kMaxSpeed, rot.getAsDouble()*kMaxAngularSpeed);
  }
  
  /** Update robot odometry. */
  public void updateOdometry() {
    odometry.update(
        gyro.getRotation2d(), leftEncoder.getDistance(), rightEncoder.getDistance());
  }

  // Resets robot odometry. 
  public void resetOdometry(Pose2d pose) {
    drivetrainSimulator.setPose(pose);
   // odometry.resetPosition(/*Add the appropriate arguments here.*/ );
  }

  /** Check the current robot pose. */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
    
  }


  /** Update our simulation. This should be run every robot loop in simulation. */
  public void simulationPeriodic() {
    // To update our simulation, we set motor voltage inputs, update the
    // simulation, and write the simulated positions and velocities to our
    // simulated encoder and gyro. We negate the right side so that positive
    // voltages make the right side move forward.
    drivetrainSimulator.setInputs(
        leftMotor.get() * RobotController.getInputVoltage(),
        rightMotor.get() * RobotController.getInputVoltage());
    drivetrainSimulator.update(0.02);

    leftEncoderSim.setDistance(drivetrainSimulator.getLeftPositionMeters());
    leftEncoderSim.setRate(drivetrainSimulator.getLeftVelocityMetersPerSecond());
    rightEncoderSim.setDistance(drivetrainSimulator.getRightPositionMeters());
    rightEncoderSim.setRate(drivetrainSimulator.getRightVelocityMetersPerSecond());
    gyroSim.setAngle(-drivetrainSimulator.getHeading().getDegrees());
  }

  /** Update odometry - this should be run every robot loop. */
  public void periodic() {
    updateOdometry();
    fieldSim.setRobotPose(odometry.getPoseMeters());
  }
}
