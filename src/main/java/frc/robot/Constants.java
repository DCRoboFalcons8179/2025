// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;

/** Add your docs here. */
public class Constants {
  /** Defines the possible runtime modes for the robot code. */
  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  /** The simulation mode to use when not running on real hardware. */
  public static final Mode simMode = Mode.SIM;

  /** The current runtime mode, determined by whether running on real hardware or in simulation. */
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  /** Are we debugging the bot? */
  public static boolean debug;

  /** The port numbers for our controllers */
  public class Controllers {
    public static final int xboxController = 0;
  }

  /** Values for configuring Motors */
  public class DriveValues {
    public static final int leftDriveID = 2;
    public static final boolean leftDriveInvert = false;

    public static final int leftFollowerID = 3;
    public static final boolean leftFollowerInvert = false;

    public static final int rightDriveID = 4;
    public static final boolean rightDriveInvert = false;

    public static final int rightFollowerID = 5;
    public static final boolean rightFollowerInvert = false;

    public static final double kP = 0;
    public static final double kI = 0;
    public static final double kD = 0;
    /** kV is the same as kP */
    public static final double kV = 0;

    public static final int maxRPS = 10000;
  }

  public class Vision {
    public class FrontCameraValues {
      public static final String cameraName = "PLACEHOLDER"; // TODO: Add the camera names
      public static final double cameraHeightMeters =
          Units.inchesToMeters(12); // TODO: Add the correct height
      public static final double cameraAngleRadians =
          Units.degreesToRadians(20); // TODO: Add the correct angle
    }

    public class AprilTags {
      public static final double tag1HeightMeters =
          Units.inchesToMeters(50); // TODO: Add the correct height
    }
  }
}
