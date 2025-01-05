// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/** Add your docs here. */
public class Constants {
    /**Are we debugging the bot?*/
    public static boolean debug;

    public class DriveValues {
        public static int leftDriveID = 2;
        public static boolean leftDriveInvert = false;
        

        public static int leftFollowerID = 3;
        public static boolean leftFollowerInvert = false;

        public static int rightDriveID = 4;
        public static boolean rightDriveInvert = false;

        public static int rightFollowerID = 5;
        public static boolean rightFollowerInvert = false;


        public static double kP = 0;
        public static double kI = 0;
        public static double kD = 0;
        /** <p>kV is the same as kP*/
        public static double kV = 0;
    }
}
