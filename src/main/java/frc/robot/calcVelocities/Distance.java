package frc.robot.calcVelocities;

public interface Distance {
  public default double getVelocity() {
    return 0;
  }

  public default boolean isDone() {
    return false;
  }
}
