package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.Hang;
import frc.robot.commands.MoveCoral;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveWrist;
import frc.robot.commands.ResetElevator;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.HookSub;
import frc.robot.subsystems.drive.Drive;

public class ControllerButtons {
  CommandXboxController commandXboxController;
  Drive drive;
  CoralSub coralSub;
  ElevatorSub elevatorSub;
  HookSub hookSub;

  public ControllerButtons(
      CommandXboxController commandXboxController,
      Drive drive,
      CoralSub coralSub,
      ElevatorSub elevatorSub,
      HookSub hookSub) {
    this.commandXboxController = commandXboxController;
    this.drive = drive;
    this.coralSub = coralSub;
    this.elevatorSub = elevatorSub;
    this.hookSub = hookSub;
  }

  public void configureButtonBindings() {
    // Default command, normal field-relative drive
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            elevatorSub,
            () -> -commandXboxController.getLeftY(),
            () -> -commandXboxController.getLeftX(),
            () -> -commandXboxController.getRightX()));

    // Reset gyro to 0
    commandXboxController.y().onTrue(new InstantCommand(() -> drive.zeroYaw()));

    // Hang
    commandXboxController
        .b()
        .onTrue(new Hang(() -> -0.3, hookSub))
        .onFalse(new Hang(() -> 0, hookSub));

    // Elevator
    commandXboxController.leftBumper().onTrue(new ResetElevator(elevatorSub));

    // Home
    commandXboxController
        .rightBumper()
        .onTrue(
            new MoveElevator(() -> 0, elevatorSub)
                .andThen(new MoveWrist(() -> 0, coralSub))
                .withTimeout(5));

    // Coral
    // In
    commandXboxController
        .leftTrigger()
        .onTrue(new MoveCoral(() -> 1, coralSub))
        .onFalse(new MoveCoral(() -> 0, coralSub));

    // Out
    commandXboxController
        .rightTrigger()
        .onTrue(new MoveCoral(() -> -1, coralSub))
        .onFalse(new MoveCoral(() -> 0, coralSub));

    // // Coral Tilting
    // commandXboxController.povUp().whileTrue(new MoveHook(() -> 0.5, hookSub));
    // commandXboxController
    //     .povUp()
    //     .onFalse(new Hang(() -> 0, hookSub))
    //     .and(() -> !commandXboxController.povDown().getAsBoolean())
    //     .and(() -> !commandXboxController.rightTrigger().getAsBoolean());

    // commandXboxController.povDown().whileTrue(new MoveHook(() -> -0.5, hookSub));

    // Hang
    // commandXboxController.rightTrigger().whileTrue(new Hang(() -> -0.6, hookSub));
  }
}
