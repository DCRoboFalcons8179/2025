// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.lib.math;

/** Add your docs here. */
public class Utils {
    public static double threshHold(double value, double max, double min) {
        return value > max ? max : value < min ? min : value;
    }

    public static double threshHold(double value) {
        return threshHold(value, 1, -1);
    }
}
