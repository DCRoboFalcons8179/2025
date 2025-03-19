package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.commands.coral.MoveCoral;
import frc.robot.commands.elevator.MoveElevator;
import frc.robot.commands.elevator.RawElevator;
import frc.robot.commands.hang.MoveHook;
import frc.robot.commands.setpoints.L1;
import frc.robot.commands.setpoints.L2;
import frc.robot.commands.setpoints.L3;
import frc.robot.commands.setpoints.L4;
import frc.robot.commands.wrist.MoveWrist;
import frc.robot.commands.wrist.RawWrist;
import frc.robot.subsystems.AlgaeSub;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.HookSub;

public class BoxButtons {
  public static void configureButtonBindings(
      ElevatorSub elevatorSub,
      CoralSub coralSub,
      AlgaeSub algaeSub,
      HookSub hookSub,
      CommandJoystick boxLeft,
      CommandJoystick boxRight) {
    // Human Player
    boxRight.button(8).onTrue(new MoveElevator(() -> 2300, elevatorSub));

    // Reef Setpoints
    // L1
    boxRight.button(9).onTrue(new L1(coralSub));

    boxRight.button(9).onFalse(new MoveWrist(() -> 0, coralSub));

    // L2
    boxRight.button(10).onTrue(new L2(elevatorSub, coralSub));

    // L3
    boxRight.button(11).onTrue(new L3(elevatorSub, coralSub));

    // L4
    boxRight.button(12).onTrue(new L4(elevatorSub, coralSub));

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

    // SetPoints

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

    // SetPoints

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
