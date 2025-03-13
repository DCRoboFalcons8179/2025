package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.Hang;
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
            () -> -commandXboxController.getLeftY(),
            () -> -commandXboxController.getLeftX(),
            () -> -commandXboxController.getRightX()));

    // drive.setDefaultCommand(
    // DriveCommands.joystickDrive(
    // drive,
    // () -> -flightStick.getY(),
    // () -> -flightStick.getX(),
    // () -> -flightStick.getTwist()));

    // Lock to 0° when A button is held
    // commandXboxController
    // .a()
    // .whileTrue(
    // DriveCommands.joystickDriveAtAngle(
    // drive,
    // () -> -commandXboxController.getLeftY(),
    // () -> -commandXboxController.getLeftX(),
    // () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed

    // Pushing
    // commandXboxController.rightTrigger().onTrue(new MoveCoral(() -> -0.5, coralSub));
    // commandXboxController.rightTrigger().onFalse(new MoveCoral(() -> 0, coralSub));
    // commandXboxController.rightTrigger().whileFalse(new MoveCoral(coralSub, () -> 0));

    // Pulling
    // commandXboxController.leftTrigger().onTrue(new MoveCoral(() -> 0.5, coralSub));
    // commandXboxController.leftTrigger().onFalse(new MoveCoral(() -> 0, coralSub));

    // Reset gyro to 0
    commandXboxController.y().onTrue(new InstantCommand(() -> drive.zeroYaw()));

    // Reset gyro to 0° when B button is pressed
    commandXboxController
        .b()
        .onTrue(new Hang(() -> -1, hookSub))
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

    // // Coral Tilting
    // commandXboxController.povUp().whileTrue(new MoveHook(() -> 0.5, hookSub));
    // commandXboxController
    //     .povUp()
    //     .onFalse(new Hang(() -> 0, hookSub))
    //     .and(() -> !commandXboxController.povDown().getAsBoolean())
    //     .and(() -> !commandXboxController.rightTrigger().getAsBoolean());

    commandXboxController.povUp().onTrue(new MoveWrist(() -> 10, coralSub));
    commandXboxController.povRight().onTrue(new MoveWrist(() -> 150, coralSub));
    commandXboxController.povLeft().onTrue(new MoveWrist(() -> 940, coralSub));
    commandXboxController.povDown().onTrue(new MoveWrist(() -> 300, coralSub));

    // commandXboxController.povDown().whileTrue(new MoveHook(() -> -0.5, hookSub));

    // Hang
    // commandXboxController.rightTrigger().whileTrue(new Hang(() -> -0.6, hookSub));
  }
}
