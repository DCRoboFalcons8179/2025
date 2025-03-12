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

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;

/** Add your docs here. */
public class Constants {
  /** Are we debugging the bot? */
  public static boolean debug;

  public static final Mode simMode = Mode.SIM;
  public static final Mode currentMode = RobotBase.isReal() ? Mode.REAL : simMode;

  public static enum Mode {
    /** Running on a real robot. */
    REAL,

    /** Running a physics simulator. */
    SIM,

    /** Replaying from a log file. */
    REPLAY
  }

  /** The port numbers for our controllers */
  public class Controllers {
    public static final int xboxController = 0;
    public static final double rumbleTimeSeconds = 0.5;
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

  /** Is the robot at a comp? */
  public static boolean comp = false;

  public static double maxSpeed = 0.5;

  public class Vision {
    public static final double errorThreshHoldMeters = 0.05;
    public static final double errorThreshHoldStrafeMeters = 0.05;
    public static final double errorThreshHoldRadians = 0.0174532925199;
    public static final int tagFindingTries = 15;
    public static final double desiredXTagDistanceMeters = 1;

    public class FrontCameraValues {
      public static final String cameraName = "HD_Web_Camera";
      public static final double cameraHeightMeters = Units.inchesToMeters(4);
      public static final double cameraAngleRadians = Units.degreesToRadians(25);
    }

    public class AprilTags {
      public static final double tag4HeightMeters = Units.inchesToMeters(57);
    }
  }

  public class CoralValues {
    public class Wrist {
      public static final int wristID = 14;
      public static final double kP = 0.00040;
      public static final double kI = 0;
      public static final double kD = 0;
      // public static final double kD = kP / 10000 * 2;
      public static final double kF = 0;
    }

    public class Motor {
      public static final int coralMotorID = 15;
      public static final double kP = 0;
      public static final double kI = 0;
      public static final double kD = 0;
      public static final double kV = 0;
    }
  }

  /** Values for configuring the Elevator */
  public class Elevator {
    public static final int driverID = 12;
    /** It doesn't really follow the driver... */
    public static final int followerMotorID = 7;

    /** Default Idle Mode */
    public static final IdleMode defaultIdleMode = IdleMode.kBrake;

    // Soft Limits
    /** Maximum height of the elevator */
    public static final int maxHeight = 17000;
    /** Height of the elevator when the wrist's minimum position is changed */
    public static final int avoidanceHeight = 3000;
    /** Max current for the motors */
    public static final int currentLimit = 40;

    public static final double encoderLimit = 3;

    // PID Values
    /** kP for the elevator */
    public static final double kP = 0.00125;
    /** kI for the elevator */
    public static final double kI = 0;
    /** kD for the elevator */
    public static final double kD = kP / 4;

    /** Slew Rate Limiter */
    public static final double slewRateLimit = 2000;
  }

  /** Values for configuring the Hook */
  public class HookInfo {
    /** ID for the Hook Motor */
    public static final int HookID = 17;

    /** PID Values for the Hook Motor */
    public class HookPID {
      /** kP for the Hook Motor */
      public static final double kP = 1;
      /** kI for the Hook Motor */
      public static final double kI = 0.1;
      /** kD for the Hook Motor */
      public static final double kD = 0;
    }
  }

  /** Values for configuring the Algae */
  public class Algae {
    /** ID for the Algae Motor */
    public static final int AlgaeID = 0;
  }
}
