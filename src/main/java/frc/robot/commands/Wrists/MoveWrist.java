// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Wrists;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.CoralSub;
import java.util.function.DoubleSupplier;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class MoveWrist extends InstantCommand {
  DoubleSupplier pose;
  CoralSub coralSub;

  public MoveWrist(DoubleSupplier pose, CoralSub coralSub) {
    this.pose = pose;
    this.coralSub = coralSub;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    coralSub.goToPose(pose.getAsDouble());
  }
}
