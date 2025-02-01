// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.lib.math.Utils;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.VisionSub;

public class MaintainDistance extends Command {
  VisionSub visionSub;
  Swerve swerve;
  double strafeSpeed;

  // PID for the forward speed loop
  final double P_GAIN = 1.5;
  final double I_GAIN = 0;
  final double D_GAIN = 0;
  PIDController controller = new PIDController(P_GAIN, I_GAIN, D_GAIN);
  private double previousTranslationSpeed = 0;
  
  int desiredDistanceMeters = 2;

  /** Creates a new MaintainDistance. */
  public MaintainDistance(VisionSub photonVision, Swerve driveSubsystem) {
    this.visionSub = photonVision;
    this.swerve = driveSubsystem;
    addRequirements(photonVision, driveSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double distance = visionSub.getDistance();
    // forwardSpeed = range != 0 ? controller.calculate(range, 2) : previousTranslationSpeed;

    double forwardTranslationSpeed;

    if (distance < Constants.Vision.errorThreshHoldMeters) {
      forwardTranslationSpeed = controller.calculate(distance, 2);
    } else if (distance != 0) {
      forwardTranslationSpeed = distance != 0 ? (Constants.Vision.maxAutonSpeedMPS / Utils.threshHold(distance, Constants.Vision.threshHoldDistanceMeters, 0)) * distance : previousTranslationSpeed;
    } else {
      forwardTranslationSpeed = previousTranslationSpeed;
    }
    previousTranslationSpeed = forwardTranslationSpeed;

    strafeSpeed = 0;
    swerve.drive(new Translation2d(-forwardTranslationSpeed, strafeSpeed), 0, true, false);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    swerve.drive(new Translation2d(0, 0), 0, true, false);
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