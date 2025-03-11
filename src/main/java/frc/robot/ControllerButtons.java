package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.MoveCoral;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveWrist;
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
    commandXboxController.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Pushing
    commandXboxController.rightTrigger().whileTrue(new MoveCoral(() -> -0.5, coralSub));
    commandXboxController.rightTrigger().onFalse(new MoveCoral(() -> 0, coralSub));
    // commandXboxController.rightTrigger().whileFalse(new MoveCoral(coralSub, () -> 0));

    // Pulling
    commandXboxController.leftTrigger().whileTrue(new MoveCoral(() -> 0.5, coralSub));
    commandXboxController.leftTrigger().onFalse(new MoveCoral(() -> 0, coralSub));

    // Reset gyro to 0
    commandXboxController.y().onTrue(new InstantCommand(() -> drive.zeroYaw()));

    // Reset gyro to 0° when B button is pressed
    commandXboxController
        .b()
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));

    // Elevator
    commandXboxController
        .leftBumper()
        .onTrue(
            new MoveElevator(() -> 650, elevatorSub)
                .withTimeout(0.1)
                .andThen(new MoveElevator(() -> 1300, elevatorSub)));
    // Home
    commandXboxController
        .rightBumper()
        .onTrue(
            new MoveElevator(() -> 0, elevatorSub)
                .andThen(new MoveWrist(() -> 0, coralSub))
                .withTimeout(5)
                .andThen(new InstantCommand(() -> elevatorSub.resetPose())));

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
