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
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.Controllers;
import frc.robot.commands.CoralGrab;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.Elevator;
import frc.robot.commands.Hang;
import frc.robot.commands.MoveCoral;
import frc.robot.commands.MoveHook;
import frc.robot.commands.Wrist;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.HookSub;
import frc.robot.subsystems.Music;
import frc.robot.subsystems.SubCoral;
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
  private final SubCoral subCoral = new SubCoral();
  // Subsystems
  public final Drive drive;
  private Music music;
  private final VisionSub visionSub = new VisionSub();
  private final HookSub hookSub;
  private final ElevatorSub elevatorSub;

  // Controller Bindings
  private final Joystick m_driverController = new Joystick(Controllers.xboxController);
  private final JoystickButton aButton =
      new JoystickButton(m_driverController, XboxController.Button.kA.value);
  private final JoystickButton bButton =
      new JoystickButton(m_driverController, XboxController.Button.kB.value);
  private final JoystickButton xButton =
      new JoystickButton(m_driverController, XboxController.Button.kX.value);
  private final JoystickButton yButton =
      new JoystickButton(m_driverController, XboxController.Button.kY.value);

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);

  // control box
  private final Joystick controlBoxLeft = new Joystick(1);
  private final Joystick controlBoxRight = new Joystick(2);

  // control box inputs
  private final JoystickButton rawElevatorUp = new JoystickButton(controlBoxLeft, 4);
  private final JoystickButton rawElevatorDown = new JoystickButton(controlBoxLeft, 6);
  private final JoystickButton rawTiltUp = new JoystickButton(controlBoxLeft, 5);
  private final JoystickButton rawTiltDown = new JoystickButton(controlBoxLeft, 7);
  private final JoystickButton coralIn = new JoystickButton(controlBoxLeft, 2);
  private final JoystickButton coralOut = new JoystickButton(controlBoxLeft, 3);
  private final JoystickButton algaeIn = new JoystickButton(controlBoxLeft, 0);
  private final JoystickButton algaeOut = new JoystickButton(controlBoxLeft, 1);
  private final JoystickButton hangDown = new JoystickButton(controlBoxLeft, 9);
  private final JoystickButton hangUp = new JoystickButton(controlBoxLeft, 10);
  private final JoystickButton algaeProcessor = new JoystickButton(controlBoxRight, 6);
  private final JoystickButton algaeL2 = new JoystickButton(controlBoxRight, 5);
  private final JoystickButton algaeL3 = new JoystickButton(controlBoxRight, 4);
  private final JoystickButton humanCoral = new JoystickButton(controlBoxRight, 11);
  private final JoystickButton coralTrough = new JoystickButton(controlBoxRight, 10);
  private final JoystickButton coralL1 = new JoystickButton(controlBoxRight, 9);
  private final JoystickButton coralL2 = new JoystickButton(controlBoxRight, 8);
  private final JoystickButton coralL3 = new JoystickButton(controlBoxRight, 7);

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
    // Default command, normal field-relative drive
    drive.setDefaultCommand(
        DriveCommands.joystickDrive(
            drive,
            () -> -controller.getLeftY(),
            () -> -controller.getLeftX(),
            () -> -controller.getRightX()));

    // drive.setDefaultCommand(
    // DriveCommands.joystickDrive(
    // drive,
    // () -> -flightStick.getY(),
    // () -> -flightStick.getX(),
    // () -> -flightStick.getTwist()));

    // Lock to 0° when A button is held
    // controller
    // .a()
    // .whileTrue(
    // DriveCommands.joystickDriveAtAngle(
    // drive,
    // () -> -controller.getLeftY(),
    // () -> -controller.getLeftX(),
    // () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    controller.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

    // Pushing
    // controller.rightTrigger().whileTrue(new MoveCoral(subCoral, () -> -1));
    // controller.rightTrigger().whileFalse(new MoveCoral(subCoral, () -> 0));

    // Pulling
    controller.leftTrigger().whileTrue(new MoveCoral(subCoral, () -> 0.5));
    controller.leftTrigger().whileFalse(new MoveCoral(subCoral, () -> 0));

    // Reset gyro to 0
    controller.y().onTrue(new InstantCommand(() -> drive.zeroYaw()));

    // Reset gyro to 0° when B button is pressed
    controller
        .b()
        .onTrue(
            Commands.runOnce(
                    () ->
                        drive.setPose(
                            new Pose2d(drive.getPose().getTranslation(), new Rotation2d())),
                    drive)
                .ignoringDisable(true));

    controller.leftBumper().whileTrue(new Elevator(() -> 0.25, elevatorSub));
    controller.rightBumper().whileTrue(new Elevator(() -> -0.1, elevatorSub));

    controller
        .leftBumper()
        .onFalse(new Elevator(() -> 0, elevatorSub))
        .and(() -> controller.rightBumper().getAsBoolean());

    // // Coral Tilting
    controller.povUp().whileTrue(new MoveHook(() -> 0.5, hookSub));
    controller
        .povUp()
        .onFalse(new Hang(() -> 0, hookSub))
        .and(() -> !controller.povDown().getAsBoolean())
        .and(() -> !controller.rightTrigger().getAsBoolean());

    controller.povDown().whileTrue(new MoveHook(() -> -0.5, hookSub));

    // Hang
    controller.rightTrigger().whileTrue(new Hang(() -> -0.6, hookSub));

    // coral grab keybinds
    // aButton.whileTrue(new CoralGrab(() -> 0.2, subCoral));
    aButton.whileFalse(new CoralGrab(() -> 0, subCoral));
    bButton.whileTrue(new CoralGrab(() -> -0.2, subCoral));
    bButton.whileFalse(new CoralGrab(() -> 0, subCoral));
    xButton.whileTrue(new Wrist(() -> 0.2, subCoral));
    xButton.whileFalse(new Wrist(() -> 0, subCoral));
    yButton.whileTrue(new Wrist(() -> -0.2, subCoral));
    yButton.whileFalse(new Wrist(() -> 0, subCoral));
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
