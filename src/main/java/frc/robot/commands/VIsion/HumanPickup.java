// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.VIsion;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.TopCamera;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class HumanPickup extends Command {
  TopCamera frontCamera;
  Drive drive;
  CommandXboxController commandXboxController;
  /** Creates a new HumanPickup. */
  public HumanPickup(
      TopCamera frontCamera, Drive drive, CommandXboxController commandXboxController) {
    this.frontCamera = frontCamera;
    this.drive = drive;
    this.commandXboxController = commandXboxController;
    addRequirements(frontCamera);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    new AlignToTag(
        drive,
        frontCamera,
        commandXboxController,
        Constants.SetPoints.HumanPickup.desiredXTagDistanceMeters,
        Constants.SetPoints.HumanPickup.desiredYTagDistanceMeters);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
