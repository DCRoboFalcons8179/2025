// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.VisionSub;
import frc.robot.subsystems.drive.Drive;

public class MaintainDistance extends Command {
  VisionSub visionSub;
  Drive drive;
  double forwardSpeed;
  double strafeSpeed = 0;
  double omegaSpeed = 0;

  /** How many times we failed to find the tag */
  int counter;

  // PID for the forward speed loop
  final double P_GAIN = 2.25;
  final double I_GAIN = 0;
  final double D_GAIN = 0.25;
  PIDController controller = new PIDController(P_GAIN, I_GAIN, D_GAIN);

  // PID for the strafe speed loop
  final double STRAFE_P_GAIN = 0.75;
  final double STRAFE_I_GAIN = 0;
  final double STRAFE_D_GAIN = 0.25;
  PIDController strafeController = new PIDController(STRAFE_P_GAIN, STRAFE_I_GAIN, STRAFE_D_GAIN);

  final double ANGULAR_P_GAIN = 0.05;
  final double ANGULAR_I_GAIN = 0;
  final double ANGULAR_D_GAIN = 0;
  PIDController angularController =
      new PIDController(ANGULAR_P_GAIN, ANGULAR_I_GAIN, ANGULAR_P_GAIN);

  int desiredDistanceMeters = 2;

  /** Creates a new MaintainDistance. */
  public MaintainDistance(Drive driveSubsystem, VisionSub visionSub) {
    this.visionSub = visionSub;
    this.drive = driveSubsystem;
    addRequirements(visionSub, driveSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double distanceX = visionSub.getDistanceX();

    // If range is equal to -1, add 1 to the counter, otherwise add 0
    counter += distanceX == -1 ? 1 : 0;

    // if (yaw < Math.PI - Constants.Vision.errorThreshHoldRadians && yaw > 0) {
    //   omegaSpeed = 0.2;
    // } else if (yaw > -Math.PI + Constants.Vision.errorThreshHoldRadians && yaw < 0) {
    //   omegaSpeed = -0.2;
    // } else {
    //   omegaSpeed = 0;
    // }

    if (distanceX > 1.5) {
      forwardSpeed = -2;
      counter = 0;
    } else if (distanceX != -1) {
      forwardSpeed = controller.calculate(distanceX, 1);
      counter = 0;
    } else if (counter >= Constants.Vision.tagFindingTries) {
      System.out.println(counter);
      forwardSpeed = 0;
    }

    ChassisSpeeds speeds = new ChassisSpeeds();

    speeds.vxMetersPerSecond = -forwardSpeed;
    speeds.vyMetersPerSecond = -strafeSpeed;
    speeds.omegaRadiansPerSecond = 0;

    drive.runVelocity(speeds);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Ended Distance Control");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double distanceX = visionSub.getDistanceX();
    if (Math.abs(distanceX) < Constants.Vision.errorThreshHoldMeters) {
      return true;
    } else {
      return false;
    }
  }
}
