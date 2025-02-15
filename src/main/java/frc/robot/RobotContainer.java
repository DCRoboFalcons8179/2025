// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.Drive;
import frc.robot.Constants.Controllers;
import frc.robot.commands.CoralGrab;
import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.VisionSub;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.subsystems.SubCoral;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.robot.commands.Wrist;

public class RobotContainer {
  private final Joystick xboxController = new Joystick(0);
  private final SubCoral subCoral = new SubCoral();

  /* Drive Controls */
  private final int leftX = XboxController.Axis.kLeftX.value;
  private final int rightY = XboxController.Axis.kRightY.value;

  // Controller Bindings
private final Joystick m_driverController = 
  new Joystick(Controllers.xboxController);
  private final JoystickButton aButton = new JoystickButton(m_driverController, XboxController.Button.kA.value);
  private final JoystickButton bButton = new JoystickButton(m_driverController, XboxController.Button.kB.value);
  private final JoystickButton xButton = new JoystickButton(m_driverController, XboxController.Button.kX.value);
  private final JoystickButton yButton = new JoystickButton(m_driverController, XboxController.Button.kY.value);
  // subsystems;
  DriveSub driveSub;
  VisionSub visionSub;

  public RobotContainer() {
    driveSub = new DriveSub();
    configureDefaults();
    configureBindings();
  }

  private void configureBindings() {
    //coral grab keybinds
      
    aButton.whileTrue(new CoralGrab(() -> 0.2, subCoral));
    aButton.whileFalse(new CoralGrab(() -> 0, subCoral));
    bButton.whileTrue(new CoralGrab(() -> -0.2, subCoral));
    bButton.whileFalse(new CoralGrab(() -> 0, subCoral));
     xButton.whileTrue(new Wrist(() -> 0.2, subCoral));
    xButton.whileFalse(new Wrist(()-> 0, subCoral));
    yButton.whileTrue(new Wrist(() -> -0.2, subCoral));
    yButton.whileFalse(new Wrist(() -> 0, subCoral));
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
