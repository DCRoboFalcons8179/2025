package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CoralSub;
import java.util.function.DoubleSupplier;

public class CoralGrab extends Command {
  private DoubleSupplier power;
  private CoralSub coralSub;
  // creates new coralgrab
  public CoralGrab(DoubleSupplier power, CoralSub coralSub) {
    this.power = power;
    this.coralSub = coralSub;
    System.out.println("coral grab create new");
    addRequirements(coralSub);
  }

  @Override
  public void execute() {
    coralSub.moveCoral(power.getAsDouble());
    System.out.println("excetue coralgrab" + power);
  }
}
