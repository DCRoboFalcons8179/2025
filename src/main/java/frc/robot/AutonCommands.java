package frc.robot;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.events.EventTrigger;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.coral.AutoCoral;
import frc.robot.commands.coral.MoveCoral;
import frc.robot.commands.elevator.AutoElevator;
import frc.robot.commands.elevator.MoveElevator;
import frc.robot.commands.setpoints.Home;
import frc.robot.commands.setpoints.L4;
import frc.robot.commands.vision.AlignToTag;
import frc.robot.commands.vision.Aprilign;
import frc.robot.commands.vision.HumanPickup;
import frc.robot.commands.wrist.AutoWrist;
import frc.robot.commands.wrist.MoveWrist;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.FrontCamera;
import frc.robot.subsystems.vision.TopCamera;

public class AutonCommands {
  public static void GenCommands(
      Drive drive,
      ElevatorSub elevatorSub,
      CoralSub coralSub,
      CommandXboxController commandXboxController,
      TopCamera topCamera,
      FrontCamera frontCamera) {
    new EventTrigger("ResetAll")
        .onTrue(
            new MoveElevator(() -> 0, elevatorSub)
                .andThen(new MoveWrist(() -> 0, coralSub))
                .andThen(new MoveCoral(() -> 0, coralSub)));

    NamedCommands.registerCommand("ScoreTroph", new MoveElevator(() -> 0, elevatorSub));

    NamedCommands.registerCommand(
        "HumanCoral",
        new MoveElevator(() -> 2300, elevatorSub)
            .andThen(new MoveWrist(() -> 0, coralSub))
            .andThen(new MoveCoral(() -> 1, coralSub)));

    NamedCommands.registerCommand(
        "ScoreL4Left",
        new SequentialCommandGroup(
            new MoveCoral(() -> Constants.CoralConstants.Intake.inputSpeed, coralSub),
            new AutoElevator(() -> Constants.SetPoints.L4.elevatorPose, elevatorSub)
                .withTimeout(0.5),
            new AutoWrist(() -> Constants.SetPoints.L4.wristPose, coralSub).withTimeout(1.5),
            new AutoCoral(() -> Constants.CoralConstants.Intake.outputSpeed, coralSub)
                .withTimeout(0.5),
            new MoveElevator(() -> 0, elevatorSub),
            new InstantCommand(() -> elevatorSub.resetPose()),
            new MoveCoral(() -> 0, coralSub),
            new MoveWrist(() -> 0, coralSub)));

    NamedCommands.registerCommand(
        "ScoreL4Right",
        new SequentialCommandGroup(
            new L4(elevatorSub, coralSub).withTimeout(2),
            new AutoCoral(() -> Constants.CoralConstants.Intake.outputSpeed, coralSub)
                .withTimeout(1),
            new Home(elevatorSub, coralSub)));

    NamedCommands.registerCommand(
        "AlignToReef",
        new SequentialCommandGroup(
            new AlignToTag(
                drive,
                frontCamera,
                commandXboxController,
                Constants.SetPoints.L4.leftDesiredXTagDistanceMeters,
                Constants.SetPoints.L4.leftDesiredYTagDistanceMeters),
            DriveCommands.joystickDrive(drive, elevatorSub, () -> 0, () -> 0, () -> 0)
                .withTimeout(0.001)));

    NamedCommands.registerCommand(
        "AlignToReefRight",
        new AlignToTag(
            drive,
            frontCamera,
            commandXboxController,
            Constants.SetPoints.L4.rightDesiredXTagDistanceMeters,
            Constants.SetPoints.L4.rightDesiredYTagDistanceMeters));

    NamedCommands.registerCommand(
        "AlignToHumanPickup",
        new HumanPickup(topCamera, drive, elevatorSub, coralSub, commandXboxController)
            .andThen(
                DriveCommands.joystickDrive(drive, elevatorSub, () -> 0, () -> 0, () -> 0)
                    .withTimeout(0.001)));

    NamedCommands.registerCommand("Aprilign", new Aprilign(drive, frontCamera, 0, 20));
  }
}
