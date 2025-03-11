// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralSub;
import java.util.function.DoubleSupplier;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveCoral extends Command {
  CoralSub coralSub;
  DoubleSupplier percentOut;

  /**
   * Creates a new MoveCoral.
   *
   * <p>Uses PercentOutput to move the coral.
   *
   * @param percentOut The speed at which the coral should move
   * @param coralSub The subsystem used by this command.
   */
  public MoveCoral(DoubleSupplier percentOut, CoralSub coralSub) {
    this.percentOut = percentOut;
    this.coralSub = coralSub;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    coralSub.moveCoral(percentOut.getAsDouble());
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
