package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.MoveCoral;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveHook;
import frc.robot.commands.MoveWrist;
import frc.robot.commands.RawElevator;
import frc.robot.commands.RawWrist;
import frc.robot.subsystems.AlgaeSub;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.HookSub;

public class BoxButtons {
  private final ElevatorSub elevatorSub;
  private final CoralSub coralSub;
  private final AlgaeSub algaeSub;
  private final HookSub hookSub;
  private final CommandJoystick boxLeft;
  private final CommandJoystick boxRight;

  public BoxButtons(
      ElevatorSub elevatorSub,
      CoralSub coralSub,
      AlgaeSub algaeSub,
      HookSub hookSub,
      CommandJoystick boxLeft,
      CommandJoystick boxRight) {
    this.elevatorSub = elevatorSub;
    this.coralSub = coralSub;
    this.algaeSub = algaeSub;
    this.hookSub = hookSub;
    this.boxLeft = boxLeft;
    this.boxRight = boxRight;
  }

  public void configureButtonBindings() {
    // Human Player
    boxRight.button(8).onTrue(new MoveElevator(() -> 2300, elevatorSub));

    // Reef Setpoints
    // Trough
    boxRight.button(9).onTrue(new MoveWrist(() -> 730, coralSub));

    boxRight.button(9).onFalse(new MoveWrist(() -> 0, coralSub));

    // L2
    boxRight
        .button(10)
        .onTrue(
            new MoveElevator(() -> 4900, elevatorSub).andThen(new MoveWrist(() -> 1120, coralSub)));

    // L3
    boxRight
        .button(11)
        .onTrue(
            new MoveElevator(() -> 9330, elevatorSub).andThen(new MoveWrist(() -> 930, coralSub)));

    // L4
    boxRight
        .button(12)
        .onTrue(
            new MoveElevator(() -> 17000, elevatorSub).andThen(new MoveWrist(() -> 900, coralSub)));

    // Coral
    // Movement
    // In
    boxLeft
        .button(9)
        .onTrue(new MoveCoral(() -> 0.8, coralSub))
        .onFalse(new MoveCoral(() -> 0, coralSub));
    // Out
    boxLeft
        .button(10)
        .onTrue(new MoveCoral(() -> -0.5, coralSub))
        .onFalse(new MoveCoral(() -> 0, coralSub));

    // Setpoints

    // Algae
    // Movements
    // boxLeft
    //     .button(1)
    //     .onTrue(new MoveAlgae(() -> 0.5, algaeSub))
    //     .onFalse(new MoveAlgae(() -> 0, algaeSub));
    // boxLeft
    //     .button(2)
    //     .onTrue(new MoveAlgae(() -> -0.5, algaeSub))
    //     .onFalse(new MoveAlgae(() -> 0, algaeSub));

    // Setpoints

    // Raw Wrist Control
    // Up
    boxLeft.button(7).onTrue(new RawWrist(() -> 50, coralSub));
    // Down
    boxLeft.button(5).onTrue(new RawWrist(() -> -50, coralSub));

    // Raw Elevator Control
    // Up
    boxLeft.button(6).onTrue(new RawElevator(() -> -100, elevatorSub));
    // Down
    boxLeft.button(8).onTrue(new RawElevator(() -> 100, elevatorSub));

    // Hook Control
    // Up
    boxLeft
        .button(11)
        .onTrue(new MoveHook(() -> 1.5, hookSub))
        .onFalse(new MoveHook(() -> 0, hookSub));
    // Down
    boxLeft
        .button(12)
        .onTrue(new MoveHook(() -> -1.5, hookSub))
        .onFalse(new MoveHook(() -> 0, hookSub));
  }
}
