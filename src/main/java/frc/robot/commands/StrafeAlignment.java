// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.VisionSub;
import frc.robot.subsystems.drive.Drive;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class StrafeAlignment extends Command {
  Drive drive;
  VisionSub visionSub;
  double strafeSpeed = 0;
  int counter = 0;

  // PID for the strafe speed loop
  final double P_GAIN = 0.75;
  final double I_GAIN = 0;
  final double D_GAIN = 0.25;
  PIDController controller = new PIDController(P_GAIN, I_GAIN, D_GAIN);

  /** Creates a new StrafeAlignment. */
  public StrafeAlignment(Drive drive, VisionSub visionSub) {
    this.drive = drive;
    this.visionSub = visionSub;
    addRequirements(drive, visionSub);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double distanceY = visionSub.getDistanceY();

    // if (distanceY > 1.5) {
    //   strafeSpeed = -2;
    //   counter = 0;
    // } else

    // If the tag is not found, increment the counter by 1
    counter += distanceY != -1 ? 0 : 1;

    if (distanceY != -1) {
      strafeSpeed = controller.calculate(distanceY, 0);
      counter = 0;
    } else if (counter >= Constants.Vision.tagFindingTries) {
      System.out.println(counter);
      strafeSpeed = 0;
    }

    SmartDashboard.putNumber("Distance Y", distanceY);

    ChassisSpeeds speeds = new ChassisSpeeds();

    speeds.vxMetersPerSecond = 0;
    speeds.vyMetersPerSecond = -strafeSpeed;
    speeds.omegaRadiansPerSecond = 0;

    drive.runVelocity(speeds);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Ended Strafe");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double range = visionSub.getDistanceY();
    if (Math.abs(range) < Constants.Vision.errorThreshHoldStrafeMeters) {
      return true;
    } else {
      return false;
    }
  }
}
