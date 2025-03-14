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
import com.pathplanner.lib.events.EventTrigger;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.AutoCoral;
import frc.robot.commands.AutoElevator;
import frc.robot.commands.AutoWrist;
import frc.robot.commands.MoveCoral;
import frc.robot.commands.MoveElevator;
import frc.robot.commands.MoveWrist;
import frc.robot.commands.UpdateElevatorPose;
import frc.robot.commands.UpdateWristPose;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.AlgaeSub;
import frc.robot.subsystems.CoralSub;
import frc.robot.subsystems.ElevatorSub;
import frc.robot.subsystems.HookSub;
import frc.robot.subsystems.Music;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIOPigeon2;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOSim;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import java.util.ArrayList;
import java.util.Arrays;
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
  // private final VisionSub visionSub = new VisionSub();
  public final HookSub hookSub;
  private final ElevatorSub elevatorSub;
  private final CoralSub coralSub;
  private final AlgaeSub algaeSub;

  // Controllers
  private final CommandXboxController commandXboxController = new CommandXboxController(0);
  private final CommandJoystick boxLeft = new CommandJoystick(1);
  private final CommandJoystick boxRight = new CommandJoystick(2);

  // private final CommandXboxController musicCommandXboxController = new
  // CommandXboxController(5);

  // Dashboard inputs
  private final LoggedDashboardChooser<Command> autoChooser;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Set up auto routines
    elevatorSub = new ElevatorSub();
    coralSub = new CoralSub();
    algaeSub = new AlgaeSub();
    hookSub = new HookSub();

    switch (Constants.currentMode) {
      case REAL:
        ModuleIOTalonFX frontLeft = new ModuleIOTalonFX(TunerConstants.FrontLeft);
        ModuleIOTalonFX frontRight = new ModuleIOTalonFX(TunerConstants.FrontRight);

        ModuleIOTalonFX backLeft = new ModuleIOTalonFX(TunerConstants.BackLeft);
        ModuleIOTalonFX backRight = new ModuleIOTalonFX(TunerConstants.BackRight);

        music = new Music(frontLeft, frontRight, backLeft, backRight);
        configMusicButtonBindings();

        // Real robot, instantiate hardware IO implementations
        drive =
            new Drive(new GyroIOPigeon2(), frontLeft, frontRight, backLeft, backRight, elevatorSub);
        break;

      case SIM:
        // Sim robot, instantiate physics sim IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIOSim(TunerConstants.FrontLeft),
                new ModuleIOSim(TunerConstants.FrontRight),
                new ModuleIOSim(TunerConstants.BackLeft),
                new ModuleIOSim(TunerConstants.BackRight),
                elevatorSub);
        break;

      default:
        // Replayed robot, disable IO implementations
        drive =
            new Drive(
                new GyroIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                new ModuleIO() {},
                elevatorSub);
        break;
    }

    // Add the autoChooser dropdown to Shuffleboard
    // ShuffleboardTab tab = Shuffleboard.getTab("Autonomous");
    // tab.add("Auto Path",
    // autoChooser).withWidget(BuiltInWidgets.kComboBoxChooser);

    // Configure the button bindings
    configureButtonBindings();
    autoChooser = new LoggedDashboardChooser<>("Auto Choices", AutoBuilder.buildAutoChooser());

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
                new MoveCoral(() -> 1, coralSub),
                new AutoElevator(() -> 17000, elevatorSub),
                new AutoWrist(() -> 1000, coralSub).withTimeout(1.5),
                new AutoCoral(() -> -1, coralSub).withTimeout(0.5),
                new AutoElevator(() -> 0, elevatorSub).withTimeout(1.2),
                new InstantCommand(() -> elevatorSub.resetPose())));
  }

  public void periodic() {
    drive.getVelocity();

    // SmartDashboard.putNumber("Tag Distance", visionSub.getDistanceX());
    // SmartDashboard.putNumber("Tag Yaw", visionSub.getYaw());

    int autonID = BinaryToInt.getInt(boxRight, boxLeft);

    SmartDashboard.putNumber("Binary To Int", autonID);
    SmartDashboard.putString("Auton Name", getAutonName(autonID));
    // System.out.println(BinaryToInt.getInt(boxRight, boxLeft));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    // Configure the button bindings
    new ControllerButtons(commandXboxController, drive, coralSub, elevatorSub, hookSub) {
      {
        configureButtonBindings();
      }
    };

    new BoxButtons(elevatorSub, coralSub, algaeSub, hookSub, boxLeft, boxRight) {
      {
        configureButtonBindings();
      }
    };

    coralSub.setDefaultCommand(new UpdateWristPose(coralSub));
    elevatorSub.setDefaultCommand(new UpdateElevatorPose(elevatorSub));
  }

  private void configMusicButtonBindings() {
    // musicCommandXboxController.povUp().onTrue(new InstantCommand(() ->
    // music.play()));
    // musicCommandXboxController.povLeft().onTrue(new InstantCommand(() ->
    // music.backTrack()));
    // musicCommandXboxController.povRight().onTrue(new InstantCommand(() ->
    // music.nextTrack()));
    // musicCommandXboxController.povDown().onTrue(new InstantCommand(() ->
    // music.stop()));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    drive.zeroYaw();
    return autoChooser.get();
    // return new PathPlannerAuto(getAutonName(BinaryToInt.getInt(boxRight,
    // boxLeft)));
  }

  public ArrayList<String> autonList =
      new ArrayList<>(
          Arrays.asList(
              "DoNothing.auto",
              "StartProcessorToTrophBasic.auto",
              "CenterStartToTrophBasic.auto",
              "StartCageToTrophBasic.auto",
              "CenterStartToProcessorSideComplex.auto",
              "ProcessorStartToProcessorSideComplex.auto",
              "CenterStartToCageSideComplex.auto",
              "CageStartToCageSideComplex.auto",
              "CommandTestResetAll.auto",
              "CommandTestScoreTroph.auto",
              "CommandTestHumanCoral.auto"));

  public String getAutonName(int index) {
    return index > autonList.size() - 1 ? "DoNothing.auto" : autonList.get(index);
  }
}
