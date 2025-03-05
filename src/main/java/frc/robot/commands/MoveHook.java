// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.HookSub;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class MoveHook extends Command {

   private DoubleSupplier hookPower;
  private HookSub hookSub;

  // This hopefully works for the control box but idk if it will
  BooleanSupplier switch1Pressed;
  BooleanSupplier switch2Pressed;

  public void HookControl(BooleanSupplier switch1Pressed, BooleanSupplier switch2Pressed) {
    this.switch1Pressed = switch1Pressed;
    this.switch2Pressed = switch2Pressed;
  }

  public MoveHook(DoubleSupplier hookPower, HookSub hookSub) {
    this.hookPower = hookPower;
    this.hookSub = hookSub;

    addRequirements(hookSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        // Moves the Hook
        double downPower = switch1Pressed.getAsBoolean() && switch2Pressed.getAsBoolean() ? 0.5 : 0;
        hookSub.driveHook(downPower);
        double upPower = !switch1Pressed.getAsBoolean() && switch2Pressed.getAsBoolean() ? -0.5 : 0;
        hookSub.driveHook(upPower);
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
