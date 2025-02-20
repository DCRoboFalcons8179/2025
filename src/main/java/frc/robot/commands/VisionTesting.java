// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.VisionSub;
import frc.robot.subsystems.drive.Drive;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class VisionTesting extends Command {

  VisionSub visionSub;
  Drive drive;
  CommandXboxController controller;

  final double ANGULAR_P_GAIN = 0.01;
  final double ANGULAR_I_GAIN = 0;
  final double ANGULAR_D_GAIN = 0;

  /** Creates a new VisionTesting. */
  public VisionTesting(
      Drive drive, VisionSub visionSub, CommandXboxController commandXboxController) {
    this.visionSub = visionSub;
    this.drive = drive;
    this.controller = commandXboxController;

    addRequirements(visionSub, drive);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Calculate drivetrain commands from Joystick values

    double forward = -controller.getLeftY() * TunerConstants.kSpeedAt12Volts.baseUnitMagnitude();

    double strafe = -controller.getLeftX() * TunerConstants.kSpeedAt12Volts.baseUnitMagnitude();

    double turn = -controller.getRightX() * 1;

    // Read in relevant data from the Camera

    var camera = visionSub.getFrontCamera();

    boolean targetVisible = false;

    double targetYaw = 0.0;

    var results = camera.getAllUnreadResults();

    if (!results.isEmpty()) {

      // Camera processed a new frame since last

      // Get the last one in the list.

      var result = results.get(results.size() - 1);

      if (result.hasTargets()) {

        // At least one AprilTag was seen by the camera

        for (var target : result.getTargets()) {

          if (target.getFiducialId() == 6) {

            // Found Tag 7, record its information

            targetYaw = target.getYaw();

            targetVisible = true;
          }
        }
      }
    }

    // Auto-align when requested

    if (controller.a().getAsBoolean() && targetVisible) {

      // Driver wants auto-alignment to tag 7

      // And, tag 7 is in sight, so we can turn toward it.

      // Override the driver's turn command with an automatic one that turns toward the tag.

      turn = -1.0 * targetYaw * ANGULAR_P_GAIN * 2;
    }

    // Command drivetrain motors based on target speeds

    ChassisSpeeds chassisSpeeds =
        new ChassisSpeeds() {
          {
            vxMetersPerSecond = forward;
            vyMetersPerSecond = strafe;
          }
        };

    chassisSpeeds.omegaRadiansPerSecond = turn;

    drive.runVelocity(chassisSpeeds);

    // Put debug information to the dashboard

    SmartDashboard.putBoolean("Vision Target Visible", targetVisible);
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
