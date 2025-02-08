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

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class defines the runtime mode used by AdvantageKit. The mode is always "real" when running
 * on a roboRIO. Change the value of "simMode" to switch between "sim" (physics sim) and "replay"
 * (log replay from a file).
 */
public final class Constants {
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

  /** Is the robot at a comp? */
  public static boolean comp = false;

  public class Vision {
    public static final double errorThreshHoldMeters = 0.005;

    public class FrontCameraValues {
      public static final String cameraName = "C270_HD_WEBCAM";
      public static final double cameraHeightMeters = Units.inchesToMeters(4);
      public static final double cameraAngleRadians = Units.degreesToRadians(25);
    }

    public class AprilTags {
      public static final double tag4HeightMeters = Units.inchesToMeters(57);
    }
  }
}
