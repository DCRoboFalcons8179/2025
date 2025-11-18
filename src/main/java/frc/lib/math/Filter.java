package frc.lib.math;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;

public class Filter {

    /**
     * Return bound that a value passes if value is outside it, else returns value.
     */
    public static double cutoffFilter(double value, double max, double min) {

        if (value > max) {
            return max;
        }

        if (value < min) {
            return min;
        }

        return value;
    }

    /**
     * Cuts off |value| > 1. 
     * Useful for magnitude filtering, or powerCurve.
     */
    public static double cutoffFilter(double value) {
        return cutoffFilter(value , 1, -1);
    }

    /**
     * Cuts off values  < 0. 
     * Useful for anything that starts at 0 and won't behind it (mainly ATAT)
     */
    public static double cutoffFilter(double value, double max) {
        return cutoffFilter(value, max, 0);
    }

    /**
     * Negates the value if it is below the deadband.
     */
    public static double deadband(double value, double db) {
        return (Math.abs(value) < db ? 0 : value);
    }

    /**
     * Returns (value ^ power), preserving its sign. 
     * 
     * You should probably use with a cutoffFilter to prevent numbers from getting too high.
     * 
     * <p> Do not divide by zero.
     */
    public static double powerCurve(double value, double power) {
        int negative_sign = 1;

        if (value < 0) {
            negative_sign *= -1;
            value *= -1;
        }

        double ret = Math.pow(value, power);
        if (!Double.isInfinite(ret)) {
            return ret * negative_sign;
        }

        System.out.println("You divided by zero. \nSHAME!\nSHAME!\nSHAME!");

        return 0;
    }

    /**
     * Returns a unit {@link Translation2d}
     */
    public static Translation2d unit(Translation2d vector) {
        return vector.div(vector.getNorm());
    }

    /**
     * Returns a unit {@link Translation3d}
     */
    public static Translation3d unit(Translation3d vector) {
        return vector.div(vector.getNorm());
    }
}
