package frc.lib;

import frc.robot.Constants;
import frc.robot.Constants.Mode;
import java.io.File;
import org.photonvision.common.hardware.Platform;

public class RustMath {
  private static boolean loaded = false;

  public static void init() {
    if (loaded) return;

    String simPath = "src/main/java/frc/lib";

    String libPath =
        Constants.currentMode == Mode.REAL
            ? "/home/lvuser/deploy/libmath.so"
            : new File(
                    Platform.isLinux()
                        ? simPath + "/linux-x86_64/libmath.so"
                        : simPath + "windows-x86_64/math.dll")
                .getAbsolutePath();

    // Initialize the linker
    System.load((libPath));
    loaded = true;
  }

  public static native double cutoffFilter(double value, double max, double min);

  public static native double powerCurve(double value, double power);

  public static native double getAverageSpeed(double[] speeds, double[] angles, int len);
}
