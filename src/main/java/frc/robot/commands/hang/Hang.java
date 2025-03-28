// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.hang;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.HookSub;
import java.util.function.DoubleSupplier;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Hang extends InstantCommand {
  DoubleSupplier power;
  HookSub hookSub;

  /** Creates a new Hang. */
  public Hang(DoubleSupplier power, HookSub hookSub) {
    this.power = power;
    this.hookSub = hookSub;
    addRequirements(hookSub);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // output power to the hang motor
    hookSub.hang(power.getAsDouble());
  }
}
