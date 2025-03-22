package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.coral.MoveCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.hang.Hang;
import frc.robot.commands.setpoints.Home;
import frc.robot.commands.vision.AlignToTag;
import frc.robot.commands.vision.HumanPickup;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.HookSub;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.FrontCamera;
import frc.robot.subsystems.vision.TopCamera;

public class ControllerButtons {
  public static void configureButtonBindings(
      CommandXboxController commandXboxController,
      Drive drive,
      CoralSub coralSub,
      ElevatorSub elevatorSub,
      HookSub hookSub,
      FrontCamera frontCamera,
      TopCamera topCamera) {
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
    commandXboxController.rightBumper().onTrue(new Home(elevatorSub, coralSub));

    // Coral
    // In
    commandXboxController
        .leftTrigger()
        .onTrue(new MoveCoral(() -> Constants.CoralConstants.Intake.inputSpeed, coralSub))
        .onFalse(new MoveCoral(() -> 0, coralSub));

    // Out
    commandXboxController
        .rightTrigger()
        .onTrue(new MoveCoral(() -> Constants.CoralConstants.Intake.outputSpeed, coralSub))
        .onFalse(new MoveCoral(() -> 0, coralSub));

    // Vision
    // Reef
    commandXboxController
        .povUp()
        .whileTrue(
            new AlignToTag(
                drive,
                frontCamera,
                commandXboxController,
                Constants.SetPoints.L4.desiredXTagDistanceMeters,
                Constants.SetPoints.L4.leftDesiredYTagDistanceMeters)
            // .andThen(new L4(elevatorSub, coralSub))
            );
    // Human Pickups
    commandXboxController
        .povDown()
        .whileTrue(new HumanPickup(topCamera, drive, elevatorSub, coralSub, commandXboxController))
        .onFalse(new MoveCoral(() -> 0, coralSub));

    // // Coral Tilting
    // commandXboxController.povUp().whileTrue(new MoveHook(() -> 0.5, hookSub));
    // commandXboxController
    // .povUp()
    // .onFalse(new Hang(() -> 0, hookSub))
    // .and(() -> !commandXboxController.povDown().getAsBoolean())
    // .and(() -> !commandXboxController.rightTrigger().getAsBoolean());

    // commandXboxController.povDown().whileTrue(new MoveHook(() -> -0.5, hookSub));

    // Hang
    // commandXboxController.rightTrigger().whileTrue(new Hang(() -> -0.6,
    // hookSub));
  }
}
