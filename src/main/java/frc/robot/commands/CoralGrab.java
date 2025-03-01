package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.SubCoral;
import java.util.function.DoubleSupplier;

public class CoralGrab extends Command {
  private DoubleSupplier power;
  private SubCoral subCoral;
  // creates new coralgrab
  public CoralGrab(DoubleSupplier power, SubCoral subCoral) {
    this.power = power;
    this.subCoral = subCoral;

    addRequirements(subCoral);
  }

  @Override
  public void execute() {
    subCoral.moveCoral(power.getAsDouble());
  }
}
