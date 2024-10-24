// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.DutyCycleEncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Timer;

public class SimpleMotorSubsystem extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */

  PWMSparkMax motor = new PWMSparkMax(0);
  DutyCycleEncoder EncoderTracker = new DutyCycleEncoder(1);
  DutyCycleEncoderSim Encoder= new DutyCycleEncoderSim(EncoderTracker);
  Timer Timer = new Timer();
  int numSwitch;

  public SimpleMotorSubsystem() {
   numSwitch = 0;
  }

  public Command forward() {
    return new InstantCommand(()-> 
    {
      motor.set(0);
      Timer.restart();
      numSwitch++;
    });
  }

  public Command stop() {
    return new InstantCommand(()-> 
    {
      motor.set(0);
      Timer.stop();
      numSwitch++;
      //Encoder.setDistance(Encoder.getDistance()+timer.get()*speed);
    });
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    SmartDashboard.putNumber("Motor Speed", motor.get());
     double MotorOnOffCounter = 0;
    SmartDashboard.putNumber("MotorOnOffCounter", MotorOnOffCounter);
  }

  public static void SmartDashboard() {
   
  }

}
