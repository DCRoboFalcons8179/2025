package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

public class BinaryToInt {

  public BinaryToInt() {}

  public static int getInt(CommandJoystick boxRight, CommandJoystick boxLeft) {
    // System.out.println(boxRight.button(1).getAsBoolean());
    return (boxLeft.button(3).getAsBoolean() ? 1 << 4 : 0)
        + (boxRight.button(4).getAsBoolean() ? 1 << 3 : 0)
        + (boxRight.button(3).getAsBoolean() ? 1 << 2 : 0)
        + (boxRight.button(2).getAsBoolean() ? 1 << 1 : 0)
        + (boxRight.button(1).getAsBoolean() ? 1 << 0 : 0);
  }
}
