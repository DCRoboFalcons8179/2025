// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.command.Drive;
import frc.robot.subsystem.DriveSub;
import frc.robot.subsystem.VisionSub;

public class RobotContainer {
  private final Joystick xboxController = new Joystick(0);

  /* Drive Controls */
  private final int leftX = XboxController.Axis.kLeftX.value;
  private final int rightY = XboxController.Axis.kRightY.value;

  // subsystems;
  DriveSub driveSub;
  VisionSub visionSub;

  public RobotContainer() {
    driveSub = new DriveSub();

    configureDefaults();
    configureBindings();
  }

  private void configureBindings() {}

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
