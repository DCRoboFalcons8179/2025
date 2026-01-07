// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.calcVelocities.DistanceX;
import frc.robot.calcVelocities.DistanceY;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.Vision;

public class FakeAlignToTag extends Command {
  // Subsystems
  Drive drive;
  Vision visionSub;
  CommandXboxController commandXboxController;

  // Velocities
  double translationVelocity;
  double strafeVelocity;
  double omegaRadians;

  // Distance Objects
  DistanceX distanceX;
  DistanceY distanceY;

  double finalPoseX;
  double finalPoseY;

  DistanceX initialDistanceX;

  // Desired Positions
  double desiredXTagDistanceMeters;
  double desiredYTagDistanceMeters;

  double desiredYaw;

  PIDController alignThreshold = new PIDController(0.75, 0.25, 0.2);

  /** Creates a new MaintainAll. */
  public FakeAlignToTag(
      Drive drive,
      Vision visionSub,
      CommandXboxController commandXboxController,
      double desiredXTagDistanceMeters,
      double desiredYTagDistanceMeters,
      double desiredYaw) {
    this.drive = drive;
    this.visionSub = visionSub;
    this.desiredYaw = desiredYaw;
    this.commandXboxController = commandXboxController;
    this.desiredXTagDistanceMeters = desiredXTagDistanceMeters;
    this.desiredYTagDistanceMeters = desiredYTagDistanceMeters;

    // Sets the distance objects to use the visionSub with the correct pointer
    distanceX = new DistanceX(visionSub, desiredXTagDistanceMeters);
    distanceY = new DistanceY(visionSub, desiredYTagDistanceMeters);

    addRequirements(drive, visionSub);
  }

  public FakeAlignToTag(
      Drive drive,
      Vision visionSub,
      CommandXboxController commandXboxController,
      double desiredXTagDistanceMeters,
      double desiredYTagDistanceMeters) {
    this(
        drive,
        visionSub,
        commandXboxController,
        desiredXTagDistanceMeters,
        desiredYTagDistanceMeters,
        0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

    finalPoseX = drive.getPose().getX() + visionSub.getDistanceX() - this.desiredXTagDistanceMeters;
    finalPoseY = drive.getPose().getY() + visionSub.getDistanceY() - this.desiredYTagDistanceMeters;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    visionSub.update();

    translationVelocity =
        distanceX.getVelocity() == 0 ? commandXboxController.getLeftY() : distanceX.getVelocity();
    strafeVelocity =
        distanceY.getVelocity() == 0 ? commandXboxController.getLeftX() : distanceY.getVelocity();
    double yaw = visionSub.getCameraYaw();

    SmartDashboard.putNumber(
        "Distance Fake Tag X", visionSub.getDistanceX() - this.desiredXTagDistanceMeters);
    SmartDashboard.putNumber(
        "Distance Fake Tag Y", visionSub.getDistanceY() - this.desiredYTagDistanceMeters);

    SmartDashboard.putNumber("Robot Final X", finalPoseX);
    SmartDashboard.putNumber("Robot Final Y", finalPoseY);

    SmartDashboard.putBoolean("Has Fake X", distanceX.isDone());
    SmartDashboard.putBoolean("Has Fake Y", distanceY.isDone());
    SmartDashboard.putBoolean(
        "Has Yaw", Math.abs(yaw) < Constants.VisionConstants.errorThreshHoldRadians);

    ChassisSpeeds chassisSpeeds =
        new ChassisSpeeds() {
          {
            vxMetersPerSecond = -translationVelocity;
            vyMetersPerSecond = -strafeVelocity;
            if (yaw != 181) {
              omegaRadiansPerSecond = alignThreshold.calculate(yaw, desiredYaw);
              // omegaRadiansPerSecond =
              // yaw > Constants.VisionConstants.errorThreshHoldRadians + desiredYaw
              // ? -0.15
              // : yaw < Constants.VisionConstants.errorThreshHoldRadians - desiredYaw
              // ? 0.15
              // : 0;
            } else {
              omegaRadiansPerSecond = 0;
            }
          }
        };

    drive.runVelocity(chassisSpeeds);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double yaw = visionSub.getCameraYaw();

    return distanceX.isDone()
        && distanceY.isDone()
        && Math.abs(yaw) < Constants.VisionConstants.errorThreshHoldRadians;
  }
}
