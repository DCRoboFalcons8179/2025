// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.AlgaeSub;
import java.util.function.DoubleSupplier;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveAlgae extends InstantCommand {

  private DoubleSupplier algaePower;
  private AlgaeSub algaeSub;

  /** Creates a new FireAlgae. */
  public MoveAlgae(DoubleSupplier algaePower, AlgaeSub algaeSub) {
    this.algaePower = algaePower;
    this.algaeSub = algaeSub;

    addRequirements(algaeSub);
  }
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    algaeSub.driveAlgea(algaePower.getAsDouble());
  }
}
