package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSub;
import java.util.function.DoubleSupplier;

public class Elevator extends Command {
  // idk but it may be needed at one point
  private DoubleSupplier elevatorPosition;
  // loads the elevator subsystem into this file
  private ElevatorSub elevatorSub;

  // sets the elevator speed
  public Elevator(DoubleSupplier sup, ElevatorSub elevatorSub) {
    this.elevatorPosition = sup;
    this.elevatorSub = elevatorSub;
    addRequirements(elevatorSub);
  }

  public void execute() {
    elevatorSub.setPosition(elevatorPosition.getAsDouble());
  }
}
