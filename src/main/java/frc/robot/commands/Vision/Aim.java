// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.Vision;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Aim extends Command {
  private final Vision vision;
  private final Drive drive;
  private final ElevatorSub elevatorSub;
  private final CommandXboxController commandXboxController;

  private static Pose2d robotTargetPose;

  PIDController aimController = new PIDController(0.2, 0, 0);
  /** Creates a new Aim. */
  public Aim(
      Vision vision,
      Drive drive,
      ElevatorSub elevatorSub,
      CommandXboxController commandXboxController) {
    this.vision = vision;
    this.drive = drive;
    this.elevatorSub = elevatorSub;
    this.commandXboxController = commandXboxController;
    addRequirements(vision, drive, elevatorSub);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  public void robotTargetPoseToTag() {
    // robotTargetPose = Vision.
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ChassisSpeeds chassisSpeeds =
        new ChassisSpeeds() {
          {
            vyMetersPerSecond = commandXboxController.getLeftX();
            vxMetersPerSecond = commandXboxController.getLeftY();
            omegaRadiansPerSecond = aimController.calculate(vision.getTargetX(0).getRadians());
          }
        };

    drive.runVelocity(chassisSpeeds);
    // DriveCommands.joystickDrive(
    //     drive,
    //     elevatorSub,
    //     () -> commandXboxController.getLeftX(),
    //     () -> commandXboxController.getLeftY(),
    //     () -> aimController.calculate(vision.getTargetX(0).getRadians()));
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
