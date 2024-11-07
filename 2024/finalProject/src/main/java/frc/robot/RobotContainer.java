// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj.PneumaticsBase;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsytems.Drivetrain;
import frc.robot.subsytems.Intake;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  
// The robot's subsystems and commands are defined here...
  CommandXboxController controller = new CommandXboxController(0);
  Drivetrain drivetrain = new Drivetrain(0,1,2,3);
  PneumaticsControlModule pbase = new PneumaticsControlModule();
  Intake intake = new Intake();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via 
   * the named factories for {@link CommandXboxController Xbox}.
   */
  private void configureBindings() {
    // Drives with the WASD Keys
    drivetrain.setDefaultCommand(new InstantCommand(()-> drivetrain.drive(()->controller.getLeftY(), ()->controller.getLeftX()),drivetrain));
    
    // The 1 key resets the robot location to the starting position in the lower left corner
    controller.a().onTrue(new InstantCommand(()->drivetrain.resetOdometry(new Pose2d()))); 
  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return null;
  }

}
