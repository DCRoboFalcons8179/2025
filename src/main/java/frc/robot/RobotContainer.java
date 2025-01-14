// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drive;
import frc.robot.commands.Elevator;
import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.VisionSub;

public class RobotContainer {
  private final Joystick xboxController = new Joystick(0);

  /* Drive Controls */
  private final int leftX = XboxController.Axis.kLeftX.value;
  private final int rightY = XboxController.Axis.kRightY.value;

  //elevator buttons
  //up
  private final JoystickButton xButton = new JoystickButton(xboxController, XboxController.Button.kX.value);
  //down
  private final JoystickButton yButton = new JoystickButton(xboxController, XboxController.Button.kY.value);


  // subsystems;
  DriveSub driveSub;
  VisionSub visionSub;

  public RobotContainer() {
    driveSub = new DriveSub();

    configureDefaults();
    configureBindings();
  }

  private void configureBindings() {

    //y button (elevator up)
    yButton.whileTrue(new InstantCommand(() -> new Elevator(() -> -5.00)));
    yButton.whileFalse(new InstantCommand(() -> new Elevator(() -> 0.00)));
    //x button (elevator down)
    xButton.whileTrue(new InstantCommand(() -> new Elevator(() -> 5.00)));
    xButton.whileFalse(new InstantCommand(() -> new Elevator(() -> 0.00)));
  }

  public void updateCameras() {
    visionSub.setFrontResults();
  }

  private void configureDefaults() {
    driveSub.setDefaultCommand(
        new Drive(driveSub, () -> xboxController.getRawAxis(leftX), () -> xboxController.getRawAxis(rightY)));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
