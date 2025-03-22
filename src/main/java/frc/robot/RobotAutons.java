import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.coral.AutoCoral;
import frc.robot.commands.coral.MoveCoral;
import frc.robot.commands.elevator.AutoElevator;
import frc.robot.commands.elevator.MoveElevator;
import frc.robot.commands.wrist.AutoWrist;
import frc.robot.commands.wrist.MoveWrist;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.NewVision;

public class RobotAutons {

    public static void initializeEventTriggers(ElevatorSub elevatorSub, CoralSub coralSub, NewVision newVision) {
        new EventTrigger("ResetAll")
            .onTrue(
                new MoveElevator(() -> 0, elevatorSub)
                    .andThen(new MoveWrist(() -> 0, coralSub))
                    .andThen(new MoveCoral(() -> 0, coralSub)));

        new EventTrigger("ScoreTroph").onTrue(new MoveElevator(() -> 0, elevatorSub));
        // .andThen(new MoveWrist(() -> 900, coralSub))
        // .andThen(new MoveCoral(() -> -1, coralSub)));

        new EventTrigger("HumanCoral")
            .onTrue(
                new MoveElevator(() -> 2300, elevatorSub)
                    .andThen(new MoveWrist(() -> 0, coralSub))
                    .andThen(new MoveCoral(() -> 1, coralSub)));
        new EventTrigger("ScoreL4")
            .onTrue(
                new SequentialCommandGroup(
                    // new MaintainAll(drive, visionSub),
                    new MoveCoral(() -> 1, coralSub),
                    new AutoElevator(() -> 17000, elevatorSub),
                    new AutoWrist(() -> 950, coralSub).withTimeout(1.5),
                    new AutoCoral(() -> -1, coralSub).withTimeout(0.5),
                    new AutoElevator(() -> 0, elevatorSub).withTimeout(1.2),
                    new InstantCommand(() -> elevatorSub.resetPose()),
                    new AutoCoral(() -> 0, coralSub),
                    new AutoWrist(() -> 0, coralSub)));
    }

}