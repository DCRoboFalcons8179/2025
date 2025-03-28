// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.commands.coral.MoveCoral;
import frc.robot.commands.elevator.MoveElevator;
import frc.robot.commands.wrist.MoveWrist;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.TopCamera;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class HumanPickup extends SequentialCommandGroup {
  /** Creates a new Human. */
  public HumanPickup(
      TopCamera topCamera,
      Drive drive,
      ElevatorSub elevatorSub,
      CoralSub coralSub,
      CommandXboxController commandXboxController) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new MoveElevator(() -> Constants.SetPoints.HumanPickup.elevatorPose, elevatorSub),
        new MoveWrist(() -> Constants.SetPoints.HumanPickup.wristPose, coralSub),
        new MoveCoral(() -> Constants.CoralConstants.Intake.inputSpeed, coralSub),
        new AlignToTag(
            drive,
            topCamera,
            commandXboxController,
            Constants.SetPoints.HumanPickup.desiredXTagDistanceMeters,
            Constants.SetPoints.HumanPickup.desiredYTagDistanceMeters));
  }
}
