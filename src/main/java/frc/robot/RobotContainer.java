// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.Drive;
import frc.robot.Commands.FireAlgae;
import frc.robot.Subsystems.AlgaeSub;
import frc.robot.Subsystems.DriveSub;
import frc.robot.Subsystems.VisionSub;

public class RobotContainer {
  // Subsystems and commands
    private final AlgaeSub algaeSub = new AlgaeSub();
  // If needed   private final FireAlgae fireAlgae = new FireAlgae(() -> 0.5, algaeSub);

  // Controller bindings
  private final Joystick xboxController = new Joystick(0);

  /* Drive Controls */
  private final int leftX = XboxController.Axis.kLeftX.value;
  private final int rightY = XboxController.Axis.kRightY.value;
  private final JoystickButton leftBumper = new JoystickButton(xboxController, XboxController.Button.kLeftBumper.value);
  private final JoystickButton rightBumper = new JoystickButton(xboxController, XboxController.Button.kRightBumper.value);
  
  Trigger algaeTrigger = new Trigger(() -> (leftBumper.getAsBoolean() || rightBumper.getAsBoolean()));
  // Subsystems;
  DriveSub driveSub;
  VisionSub visionSub;

  public RobotContainer() {
    driveSub = new DriveSub();

    configureDefaults();
    configureBindings();
  }

  private void configureBindings() {
    // Left Bumper Algea
    algaeTrigger.whileTrue(new FireAlgae(() -> 0.5, algaeSub));
    algaeTrigger.whileFalse(new FireAlgae( ()-> 0, algaeSub));
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
