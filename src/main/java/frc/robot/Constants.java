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
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
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

    public static final int maxRPS = 10000;
  }

  /** Is the robot at a comp? */
  public static boolean comp = true;

  public static double maxSpeed = 0.7;

  public class VisionConstants {
    public static final double errorThreshHoldMeters = 0.005;
    public static final double errorThreshHoldStrafeMeters = 0.005;
    public static final double errorThreshHoldRadians = 0.0349065850399 * 0.75;
    public static final int tagFindingTries = 15;

    public class FrontCameraValues {
      /** Name of the Camera */
      public static final String cameraName = "Front_Camera";
      /** Position of the Camera Relative to the Robot */
      public static final Transform3d cameraToRobot =
          new Transform3d(0, 0, 0, new Rotation3d(0, 0, 0));
    }

    public class BackCameraValues {
      /** Name of the Camera */
      public static final String cameraName = "Back_Camera";

      /** Camera Position Relative to the Robot */
      public static final Transform3d cameraToRobot =
          new Transform3d(0, 0, 0, new Rotation3d(0, 0, 0));
    }

    public class TopCameraValues {
      /** Name of the Camera */
      public static final String cameraName = "Top_Camera";
    }

    public class AprilTags {
      public static final double tag4HeightMeters = Units.inchesToMeters(57);
    }
  }

  public class CoralConstants {
    public class Wrist {
      public static final int wristID = 14;
      public static final double kP = 0.00075;
      public static final double kI = 0;
      public static final double kD = 0;
      // public static final double kD = kP / 10000 * 2;
      public static final double kF = 0;

      public static final double minPose = 1000;
      public static final double maxPose = 18530;
    }

    public class Intake {
      public static final int coralMotorID = 15;
      public static final double kP = 0;
      public static final double kI = 0;
      public static final double kD = 0;
      public static final double kV = 0;

      public static final double outputSpeed = 0.5;
      public static final double inputSpeed = -0.5;
    }

    // public class Sensor {
    // public static final int sensorButtonID = ;
    // }
  }

  /** Values for configuring the Elevator */
  public class ElevatorConstants {
    public static final int driverID = 12;
    /** It doesn't really follow the driver... */
    public static final int followerMotorID = 7;

    /** Default Idle Mode */
    public static final IdleMode defaultIdleMode = IdleMode.kBrake;

    // Soft Limits
    /** Maximum height of the elevator */
    public static final int maxHeight = 18500;
    /** Height of the elevator when the wrist's minimum position is changed */
    public static final int avoidanceHeight = 3000;
    /** Max current for the motors */
    public static final int currentLimit = 70;

    public static final double encoderLimit = 3;

    public static final double maxElevatorSpeedPercentOut = 0.5;
    public static final double minElevatorSpeedPercentOut = -0.20;

    // PID Values
    /** kP for the elevator */
    public static final double kP = 0.0025;
    /** kI for the elevator */
    public static final double kI = 0;
    /** kD for the elevator */
    public static final double kD = 0;

    /** Slew Rate Limiter */
    public static final double slewRateLimit = 2000;
  }

  /** Values for configuring the Hook */
  public class HookConstants {
    /** ID for the Hook Motor */
    public static final int HookID = 17;

    /** Percent Out for the Hook */
    public static final double hookPercentOut = 1;

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
  public class AlgaeConstants {
    /** ID for the Algae Motor */
    public static final int AlgaeID = 0;
  }

  public class SetPoints {
    public class L4 {
      public static final double elevatorPose = 18200;
      public static final double wristPose = 14000;
      public static final double leftDesiredXTagDistanceMeters = 0.45;
      public static final double leftDesiredYTagDistanceMeters = -0.21;
      public static final double leftYawRads = Units.degreesToRadians(-170);
      public static final double rightDesiredXTagDistanceMeters = 0.46;
      public static final double rightDesiredYTagDistanceMeters = -0.13;
    }

    public class L3 {
      public static final double elevatorPose = 9930;
      public static final double wristPose = 14530;
      public static final double desiredXTagDistanceMeters = 0.39;
      public static final double leftDesiredYTagDistanceMeters = -0.15;
    }

    public class L2 {
      public static final double elevatorPose = 4500;
      public static final double wristPose = 14200;
    }

    public class L1 {
      public static final double wristPose = 730;
    }

    public class Home {
      public static final double elevatorPose = 0;
      public static final double wristPose = 1000;
    }

    public class HumanPickup {
      public static final double elevatorPose = 1950;
      public static final double wristPose = 7350;
      public static final double desiredXTagDistanceMeters = 0.59;
      public static final double desiredYTagDistanceMeters = -0.15;
    }
  }
}
