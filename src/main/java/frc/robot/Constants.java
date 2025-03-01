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

/** Add your docs here. */
public class Constants {
    /**Are we debugging the bot?*/
    public static boolean debug;
    
    /**The port numbers for our controllers*/
    public class Controllers {
        public final static int xboxController = 0;
    }

    /**Values for configuring Motors*/
    public class DriveValues {
        public final static int leftDriveID = 2;
        public final static boolean leftDriveInvert = false;
        

        public final static int leftFollowerID = 3;
        public final static boolean leftFollowerInvert = false;

        public final static int rightDriveID = 4;
        public final static boolean rightDriveInvert = false;

        public final static int rightFollowerID = 5;
        public final static boolean rightFollowerInvert = false;

        


        public final static double kP = 0;
        public final static double kI = 0;
        public final static double kD = 0;
        /** <p>kV is the same as kP*/
        public final static double kV = 0;

        public final static int maxRPS = 10000;


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
      public static final String cameraName = "C270_HD_WEBCAM";
      public static final double cameraHeightMeters = Units.inchesToMeters(4);
      public static final double cameraAngleRadians = Units.degreesToRadians(25);
    }

    public class AprilTags {
      public static final double tag4HeightMeters = Units.inchesToMeters(57);
    }
   }
   public class CoralValues {
        public final static int wristID = 6;
        public final static int coralMotorID = 7;
   }
  }
}
