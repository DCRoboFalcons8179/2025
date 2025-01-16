// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
// import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.VisionSub;
import frc.robot.subsystems.drive.Drive;

public class MaintainAll extends Command {
  VisionSub visionSub;
  Drive drive;

  // PID for the forwardSpeed PID loop
  final double SPEED_P_GAIN = 0.75;
  final double SPEED_D_GAIN = 0;

  PIDController controller = new PIDController(SPEED_P_GAIN, 0, SPEED_D_GAIN);

  // PID for the rotSpeed PID loop
  final double ANGULAR_P_GAIN = 0.2;
  final double ANGULAR_D_GAIN = 0.0;

  PIDController turnController = new PIDController(ANGULAR_P_GAIN, 0, ANGULAR_D_GAIN);

  // Initialize the variable for speed, not really needed here but why not
  private double forwardSpeed;
  private double rotSpeed;

  /** Creates a new MaintainAll. */
  public MaintainAll(VisionSub visionSub, Drive drive) {
    this.visionSub = visionSub;
    this.drive = drive;

    addRequirements(visionSub, drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double yaw = visionSub.getYaw(visionSub.getFrontCameraUnreadResults());
    double range = visionSub.getDistance(visionSub.getFrontCameraUnreadResults());

    /**
     * The PID controller didn't like calculating when the value was zero since I return zero when
     * no tag is found so these ternary operators will just set the speed to zero when there isn't
     * an april tag detected
     */
    rotSpeed = yaw != 0 ? -turnController.calculate(yaw, 0) / 10 : 0;

    forwardSpeed = range != 0 ? controller.calculate(range, 1.5) : 0;

    // Drives the robot at the desired forward and rotational speed
    // driveSub.drive(forwardSpeed, rotSpeed);
    DriveCommands.joystickDrive(drive, () -> forwardSpeed, () -> 0, () -> rotSpeed);
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
