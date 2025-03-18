// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.SetPoints;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.Elevator.MoveElevator;
import frc.robot.commands.Wrist.MoveWrist;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class L3 extends SequentialCommandGroup {
  /** Creates a new L3. */
  public L3(ElevatorSub elevatorSub, CoralSub coralSub) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new MoveElevator(() -> Constants.SetPoints.L3.elevatorPose, elevatorSub),
      new MoveWrist(() -> Constants.SetPoints.L3.wristPose, coralSub)
    );
  }
}
