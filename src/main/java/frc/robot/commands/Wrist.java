package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.CoralSub;
import java.util.function.DoubleSupplier;

public class Wrist extends InstantCommand {
  private DoubleSupplier wristPosition;
  private CoralSub coralSub;

  public Wrist(DoubleSupplier wristPosition, CoralSub coralSub) {
    this.wristPosition = wristPosition;
    this.coralSub = coralSub;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(coralSub);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    coralSub.moveWrist(wristPosition.getAsDouble());
  }
}
