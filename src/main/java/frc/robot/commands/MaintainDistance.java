// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.units.measure.LinearVelocity;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.VisionSub;
import frc.robot.subsystems.drive.Drive;

public class MaintainDistance extends Command {
  VisionSub visionSub;
  Drive drive;
  double forwardSpeed;
  double strafeSpeed;

  // PID for the forward speed loop
  final double P_GAIN = 4;
  final double D_GAIN = 0.25;
  PIDController controller = new PIDController(P_GAIN, 0, D_GAIN);
  
  int desiredDistanceMeters = 2;

  /** Creates a new MaintainDistance. */
  public MaintainDistance(VisionSub photonVision, Drive driveSubsystem) {
    this.visionSub = photonVision;
    this.drive = driveSubsystem;
    addRequirements(photonVision, driveSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double range = visionSub.getDistance();
    forwardSpeed = range != 0 ? controller.calculate(range, 2) : 0;
    strafeSpeed = 0;

    ChassisSpeeds speeds =
        new ChassisSpeeds(
               
        );

    // drive.runVelocity(new Translation2d(-forwardSpeed, strafeSpeed), 0, true, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Ended");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double range = visionSub.getDistance();
    if (Math.abs(range - desiredDistanceMeters) < Constants.Vision.errorThreshHoldMeters) {
      return true;
    } else {
      return false;
    }
  }
}