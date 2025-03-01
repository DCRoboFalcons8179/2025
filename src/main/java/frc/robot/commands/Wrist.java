package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SubCoral;
import java.util.function.DoubleSupplier;

public class Wrist extends Command {
  private DoubleSupplier wristSpeed;
  private SubCoral subCoral;

  public Wrist(DoubleSupplier wristSpeed, SubCoral subCoral) {
    this.wristSpeed = wristSpeed;
    this.subCoral = subCoral;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subCoral);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    subCoral.moveWrist(wristSpeed.getAsDouble());
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
