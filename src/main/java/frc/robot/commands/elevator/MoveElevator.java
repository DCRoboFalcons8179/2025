package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.ElevatorSub;
import java.util.function.DoubleSupplier;

public class MoveElevator extends InstantCommand {
  // idk but it may be needed at one point
  private DoubleSupplier elevatorPosition;
  // loads the elevator subsystem into this file
  private ElevatorSub elevatorSub;

  /**
   * Creates a new MoveElevator.
   *
   * <p>Uses PID to move the elevator.
   *
   * @param elevatorPosition The position the elevator should move to
   * @param elevatorSub The subsystem used by this command.
   */
  public MoveElevator(DoubleSupplier sup, ElevatorSub elevatorSub) {
    this.elevatorPosition = sup;
    this.elevatorSub = elevatorSub;
  }

  @Override
  public void initialize() {
    elevatorSub.goToPose(elevatorPosition.getAsDouble());
  }
}
