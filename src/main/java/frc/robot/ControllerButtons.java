package frc.robot;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.coral.MoveCoral;
import frc.robot.commands.elevator.ResetElevator;
import frc.robot.commands.hang.Hang;
import frc.robot.commands.setpoints.Home;
import frc.robot.commands.setpoints.L3;
import frc.robot.commands.setpoints.L4;
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
                .onTrue(new Hang(() -> Constants.HookConstants.hookPercentOut, hookSub))
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
        // L4
        commandXboxController
                .povUp()
                .whileTrue(
                        new AlignToTag(
                                drive,
                                frontCamera,
                                commandXboxController,
                                Constants.SetPoints.L4.leftDesiredXTagDistanceMeters,
                                Constants.SetPoints.L4.leftDesiredYTagDistanceMeters)
                                .andThen(new L4(elevatorSub, coralSub)));

        // L3
        commandXboxController
                .povLeft()
                .whileTrue(
                        new AlignToTag(
                                drive,
                                frontCamera,
                                commandXboxController,
                                Constants.SetPoints.L3.desiredXTagDistanceMeters,
                                Constants.SetPoints.L3.leftDesiredYTagDistanceMeters)
                                .andThen(new L3(elevatorSub, coralSub)));

        // Human Pickups
        commandXboxController
                .povDown()
                .whileTrue(new HumanPickup(topCamera, drive, elevatorSub, coralSub, commandXboxController))
                .onFalse(new MoveCoral(() -> 0, coralSub));

        commandXboxController
                .povLeft()
                .whileTrue(
                        new AlignToTag(
                                drive,
                                frontCamera,
                                commandXboxController,
                                Constants.SetPoints.L4.rightDesiredXTagDistanceMeters,
                                Constants.SetPoints.L4.rightDesiredYTagDistanceMeters));
    }
}
