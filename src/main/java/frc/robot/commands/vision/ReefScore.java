// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants;
import frc.robot.commands.setpoints.L4;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.FrontCamera;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ReefScore extends SequentialCommandGroup {
  /** Creates a new ReefScore. */
  public ReefScore(
      FrontCamera frontCamera,
      Drive drive,
      ElevatorSub elevatorSub,
      CoralSub coralSub,
      CommandXboxController commandXboxController,
      String level) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());

    addCommands(
        new AlignToTag(
            drive,
            frontCamera,
            commandXboxController,
            Constants.SetPoints.L4.leftDesiredXTagDistanceMeters,
            Constants.SetPoints.L4.leftDesiredYTagDistanceMeters));

    switch (level) {
      case "L4":
        addCommands(new L4(elevatorSub, coralSub));
        break;
      case "L3":
        break;
      case "L2":
        break;
      case "L1":
        break;

      default:
        break;
    }
  }
}
