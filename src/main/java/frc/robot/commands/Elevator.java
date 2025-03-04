package frc.robot.commands;

import frc.robot.subsystems.ElevatorSub;
import java.util.function.DoubleSupplier;

public class Elevator {
  // idk but it may be needed at one point
  private DoubleSupplier elevatorPosition;
  // loads the elevator subsystem into this file
  private ElevatorSub elevatorSub;

  // sets the elevator speed
  public Elevator(DoubleSupplier elevatorPosition, ElevatorSub elevatorSub) {
    this.elevatorPosition = elevatorPosition;
    this.elevatorSub = elevatorSub;
  }

  public void execute() {
    elevatorSub.moveMotor(elevatorPosition.getAsDouble());
  }
}
