// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ElevatorSub;
import java.util.function.DoubleSupplier;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class SmartElevator extends InstantCommand {
  ElevatorSub elevatorSub;
  DoubleSupplier pose;

  public SmartElevator(DoubleSupplier pose, ElevatorSub elevatorSub) {
    this.elevatorSub = elevatorSub;
    this.pose = pose;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    double pose1 = pose.getAsDouble() / 4;
    double pose3 = pose.getAsDouble() / 2;
    double pose2 = pose1 + pose3;

    new MoveElevator(() -> pose1, elevatorSub)
        .withTimeout(0.1)
        .andThen(new MoveElevator(() -> pose2, elevatorSub))
        .withTimeout(0.1)
        .andThen(new MoveElevator(() -> pose3, elevatorSub))
        .withTimeout(0.1)
        .andThen(new MoveElevator(pose, elevatorSub));
  }
}
