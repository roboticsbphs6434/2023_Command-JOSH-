// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.Arm.*;
import frc.robot.commands.Drivetrain.*;
import frc.robot.commands.Intake.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class RobotContainer {

  public XboxController xboxController;
  public Intake intake;
  public Arm arm;
  public Drivetrain drivetrain;
  public Vision vision;
  public Limelight limelight;

  public RobotContainer() {
    
    intake = new Intake();
    arm = new Arm();
    drivetrain = new Drivetrain();
    vision = new Vision();
    limelight = new Limelight();

    xboxController = new XboxController(0);
    arm.setDefaultCommand(new Arm_Movement(arm, "Home"));
    drivetrain.setDefaultCommand(new mainCommand(drivetrain, ()->xboxController.getLeftX(), ()->xboxController.getLeftY(), ()->xboxController.getRightX(), ()->xboxController.getRightY(), ()->xboxController.getLeftStickButton(), ()->xboxController.getRightStickButton()));
    configureBindings();
  }

  private void configureBindings() {
    final JoystickButton button_A = new JoystickButton(xboxController, 1);
    final JoystickButton button_B = new JoystickButton(xboxController, 2);
    final JoystickButton button_X = new JoystickButton(xboxController, 3);
    final JoystickButton button_Y = new JoystickButton(xboxController, 4);
  
    final JoystickButton button_LeftBumper = new JoystickButton(xboxController, 5);
    final JoystickButton button_RightBumper = new JoystickButton(xboxController, 6);


    
    final JoystickButton button_StartButton = new JoystickButton(xboxController, 10);


    button_A.onTrue(new Arm_Movement(arm, "Home"));
    button_B.onTrue(new Arm_Movement(arm, "Delivery"));
    button_X.onTrue(new Arm_Movement(arm, "Ground"));
    button_Y.onTrue(new Arm_Movement(arm, "PickUp"));

    //button_LeftBumper.onTrue(new Drivetrain_DecrementDriveMode(drivetrain));
    //button_RightBumper.onTrue(new Drivetrain_IncrementDriveMode(drivetrain));

    button_StartButton.onTrue(new Drivetrain_DriveSlowToggle(drivetrain));

    button_RightBumper.onTrue(new Intake_Toggle(intake));

  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
