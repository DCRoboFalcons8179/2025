// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.calcVelocities.DistanceX;
import frc.robot.calcVelocities.DistanceY;
import frc.robot.subsystems.NewVision;
import frc.robot.subsystems.drive.Drive;

public class MaintainAll extends Command {
  // Subsystems
  Drive drive;
  NewVision visionSub;
  CommandXboxController commandXboxController;

  // Velocities
  double translationVelocity;
  double strafeVelocity;
  double omegaRadians;

  // Distance Objects
  DistanceX distanceX;
  DistanceY distanceY;

  /** Creates a new MaintainAll. */
  public MaintainAll(Drive drive, NewVision visionSub, CommandXboxController commandXboxController) {
    this.drive = drive;
    this.visionSub = visionSub;
    this.commandXboxController = commandXboxController;

    // Sets the distance objects to use the visionSub with the correct pointer
    distanceX = new DistanceX(visionSub);
    distanceY = new DistanceY(visionSub);

    addRequirements(drive, visionSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    translationVelocity = distanceX.getVelocity();
    strafeVelocity = distanceY.getVelocity();
    double yaw = visionSub.getFrontCameraYaw();

    ChassisSpeeds chassisSpeeds =
        new ChassisSpeeds() {
          {
            vxMetersPerSecond = -translationVelocity;
            vyMetersPerSecond = -strafeVelocity;
            if (yaw != 181) {
              omegaRadiansPerSecond =
                  yaw > Constants.VisionConstants.errorThreshHoldRadians
                      ? -0.3
                      : yaw < Constants.VisionConstants.errorThreshHoldRadians ? 0.3 : 0;
            } else {
              omegaRadiansPerSecond = 0;
            }
          }
        };

    drive.runVelocity(chassisSpeeds);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Distance has been maintained");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double yaw = visionSub.getFrontCameraYaw();

    return distanceX.isDone()
        && distanceY.isDone()
        && Math.abs(yaw) < Constants.VisionConstants.errorThreshHoldRadians;
  }
}
