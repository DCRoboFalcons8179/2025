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
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.MaintainAll;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Music;
import frc.robot.subsystems.VisionSub;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import org.littletonrobotics.junction.networktables.LoggedDashboardChooser;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Commands.Drive;
import frc.robot.Commands.FireAlgae;
import frc.robot.Commands.MoveHook;
import frc.robot.Subsystems.AlgaeSub;
import frc.robot.Subsystems.DriveSub;
import frc.robot.Subsystems.HookSub;
import frc.robot.Subsystems.VisionSub;

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

  private final CommandJoystick flightStick = new CommandJoystick(2);

  // Controller
  private final CommandXboxController controller = new CommandXboxController(0);
  // Subsystems and commands
    private final AlgaeSub algaeSub = new AlgaeSub();
    private final HookSub hookSub = new HookSub();
  // If needed   private final FireAlgae fireAlgae = new FireAlgae(() -> 0.5, algaeSub);

  // Controller bindings
  private final Joystick xboxController = new Joystick(0);

  /* Drive Controls */
  private final int leftX = XboxController.Axis.kLeftX.value;
  private final int rightY = XboxController.Axis.kRightY.value;
  private final JoystickButton leftBumper = new JoystickButton(xboxController, XboxController.Button.kLeftBumper.value);
  private final JoystickButton rightBumper = new JoystickButton(xboxController, XboxController.Button.kRightBumper.value);
  private final JoystickButton startButton = new JoystickButton(xboxController, XboxController.Button.kStart.value);

  Trigger algaeTrigger = new Trigger(() -> (leftBumper.getAsBoolean() & rightBumper.getAsBoolean()));
  // Subsystems;
  DriveSub driveSub;
  VisionSub visionSub;

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

    // Configure the button bindings
    configureButtonBindings();
  }

  public void periodic() {
    drive.getVelocity();
  private void configureBindings() {
    //Bumpers Algea (right takes in and left pushes out)
    leftBumper.whileTrue(new FireAlgae(() -> 0.5, algaeSub));
    rightBumper.whileTrue(new FireAlgae(() -> -0.5, algaeSub));
    algaeTrigger.whileFalse(new FireAlgae(() -> 0, algaeSub));

    //Start button hook
    startButton.whileTrue(new MoveHook(() -> 0.5, hookSub));
    startButton.whileFalse(new MoveHook(() -> 0, hookSub));
  }

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
    //     DriveCommands.joystickDrive(
    //         drive,
    //         () -> -flightStick.getY(),
    //         () -> -flightStick.getX(),
    //         () -> -flightStick.getTwist()));

    // Lock to 0° when A button is held
    controller
        .a()
        .whileTrue(
            DriveCommands.joystickDriveAtAngle(
                drive,
                () -> -controller.getLeftY(),
                () -> -controller.getLeftX(),
                () -> new Rotation2d()));

    // Switch to X pattern when X button is pressed
    controller.x().onTrue(Commands.runOnce(drive::stopWithX, drive));

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

    controller.leftTrigger().whileTrue(new MaintainAll(drive, visionSub));
  }

  private void configMusicButtonBindings() {
    if (!Constants.comp) {
      controller.povUp().onTrue(new InstantCommand(() -> music.play()));
      controller.povLeft().onTrue(new InstantCommand(() -> music.backTrack()));
      controller.povRight().onTrue(new InstantCommand(() -> music.nextTrack()));
      controller.povDown().onTrue(new InstantCommand(() -> music.stop()));
    }
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    drive.zeroYaw();
    // return new PathPlannerAuto("Reef");
    // return DriveCommands.joystickDrive(drive, () -> 0.5, () -> 0, () -> 0).withTimeout(1);
    return autoChooser.get();
  }
}
