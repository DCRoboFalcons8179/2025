// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
    }
}
