package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ElevatorSub;
import java.util.function.DoubleSupplier;

public class MoveElevator extends InstantCommand {
  // idk but it may be needed at one point
  private DoubleSupplier elevatorPosition;
  // loads the elevator subsystem into this file
  private ElevatorSub elevatorSub;

  // sets the elevator speed
  public MoveElevator(DoubleSupplier sup, ElevatorSub elevatorSub) {
    this.elevatorPosition = sup;
    this.elevatorSub = elevatorSub;
    System.out.println("new move leleveatr");
  }

  @Override
  public void initialize() {
    elevatorSub.setPosition(elevatorPosition.getAsDouble());
    System.out.println("initalize elevaetor" + elevatorPosition);
  }
}
