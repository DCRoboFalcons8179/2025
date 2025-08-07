package frc.lib;

import frc.robot.Constants;
import frc.robot.Constants.Mode;
import java.io.File;

public class RustBindings {
  static {
    String libPath =
        Constants.currentMode == Mode.REAL
            ? "/home/lvuser/libfilter.so"
            : new File("src/main/java/frc/lib/linux-x86_64/libfilter.so").getAbsolutePath();

    // Initialize the linker
    System.load((libPath));
  }

  public static native double cutoffFilter(double value, double max, double min);
}
