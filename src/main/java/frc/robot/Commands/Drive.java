// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems.DriveSub;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Drive extends Command {
  DriveSub driveSub;
  DoubleSupplier forwardSpeed;
  DoubleSupplier turnSpeed;

  /**
   * Drives the robot with a forward speed and turn speed
   * @param driveSub
   * @param forwardSpeed
   * @param turnSpeed
   */
  public Drive(DriveSub driveSub, DoubleSupplier forwardSpeed, DoubleSupplier turnSpeed) {
    this.driveSub = driveSub;
    this.forwardSpeed = forwardSpeed;
    this.turnSpeed = turnSpeed;
    addRequirements(driveSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveSub.drive(forwardSpeed.getAsDouble(), turnSpeed.getAsDouble());    
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
