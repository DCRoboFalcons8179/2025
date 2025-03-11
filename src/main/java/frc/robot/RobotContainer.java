// Copyright 2021-2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// version 3 as published by the Free Software Foundation or
// available in the root directory of this project.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.UpdateElevatorPose;
import frc.robot.commands.UpdateWristPose;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.AlgaeSub;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.HookSub;
import frc.robot.subsystems.Music;
import frc.robot.subsystems.VisionSub;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // Subsystems
  public final Drive drive;
  private Music music;
  private final VisionSub visionSub = new VisionSub();
  private final HookSub hookSub;
  private final ElevatorSub elevatorSub;
  private final CoralSub coralSub;
  private final AlgaeSub algaeSub;

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);
  private final CommandJoystick boxLeft = new CommandJoystick(1);
  private final CommandJoystick boxRight = new CommandJoystick(2);

  // control box inputs
  // private final JoystickButton rawElevatorUp = new JoystickButton(controlBoxLeft, 4);
  // private final JoystickButton rawElevatorDown = new JoystickButton(controlBoxLeft, 6);
  // private final JoystickButton rawTiltUp = new JoystickButton(controlBoxLeft, 5);
  // private final JoystickButton rawTiltDown = new JoystickButton(controlBoxLeft, 7);
  // private final JoystickButton coralIn = new JoystickButton(controlBoxLeft, 2);
  // private final JoystickButton coralOut = new JoystickButton(controlBoxLeft, 3);
  // private final JoystickButton algaeIn = new JoystickButton(controlBoxLeft, 0);
  // private final JoystickButton algaeOut = new JoystickButton(controlBoxLeft, 1);
  // private final JoystickButton hangDown = new JoystickButton(controlBoxLeft, 9);
  // private final JoystickButton hangUp = new JoystickButton(controlBoxLeft, 10);
  // private final JoystickButton algaeProcessor = new JoystickButton(controlBoxRight, 6);
  // private final JoystickButton algaeL2 = new JoystickButton(controlBoxRight, 5);
  // private final JoystickButton algaeL3 = new JoystickButton(controlBoxRight, 4);
  // private final JoystickButton humanCoral = new JoystickButton(controlBoxRight, 11);
  // private final JoystickButton coralTrough = new JoystickButton(controlBoxRight, 10);
  // private final JoystickButton coralL1 = new JoystickButton(controlBoxRight, 9);
  // private final JoystickButton coralL2 = new JoystickButton(controlBoxRight, 8);
  // private final JoystickButton coralL3 = new JoystickButton(controlBoxRight, 7);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    switch (Constants.currentMode) {
      case REAL:
        ModuleIOTalonFX frontLeft = new ModuleIOTalonFX(TunerConstants.FrontLeft);
        ModuleIOTalonFX frontRight = new ModuleIOTalonFX(TunerConstants.FrontRight);

        ModuleIOTalonFX backLeft = new ModuleIOTalonFX(TunerConstants.BackLeft);
        ModuleIOTalonFX backRight = new ModuleIOTalonFX(TunerConstants.BackRight);

        music = new Music(frontLeft, frontRight, backLeft, backRight);
        configMusicButtonBindings();

        // Real robot, instantiate hardware IO implementations
        drive = new Drive(new GyroIOPigeon2(), frontLeft, frontRight, backLeft, backRight);
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(TunerConstants.FrontLeft),
                new ModuleIOSim(TunerConstants.FrontRight),
                new ModuleIOSim(TunerConstants.BackLeft),
                new ModuleIOSim(TunerConstants.BackRight));
        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {});
        break;
    }

    // Set up auto routines
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());
    elevatorSub = new ElevatorSub();
    coralSub = new CoralSub(elevatorSub);
    algaeSub = new AlgaeSub();
    hookSub = new HookSub();

    // Configure the button bindings
    configureButtonBindings();
  }

  public void periodic() {
    drive.getVelocity();

    SmartDashboard.putNumber("Tag Distance", visionSub.getDistanceX());
    SmartDashboard.putNumber("Tag Yaw", visionSub.getYaw());
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new ControllerButtons(controller, drive, coralSub, elevatorSub, hookSub) {
      {
        configureButtonBindings();
      }
    };

    new BoxButtons(elevatorSub, coralSub, algaeSub, boxLeft, boxRight) {
      {
        configureButtonBindings();
      }
    };

    coralSub.setDefaultCommand(new UpdateWristPose(coralSub));
    elevatorSub.setDefaultCommand(new UpdateElevatorPose(elevatorSub));
  }

  private void configMusicButtonBindings() {
    // if (!Constants.comp) {
    // controller.povUp().onTrue(new InstantCommand(() -> music.play()));
    // controller.povLeft().onTrue(new InstantCommand(() -> music.backTrack()));
    // controller.povRight().onTrue(new InstantCommand(() -> music.nextTrack()));
    // controller.povDown().onTrue(new InstantCommand(() -> music.stop()));
    // }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    drive.zeroYaw();
    // return new PathPlannerAuto("Reef");
    // return DriveCommands.joystickDrive(drive, () -> 0.5, () -> 0, () ->
    // 0).withTimeout(1);
    return autoChooser.get();
  }
}
