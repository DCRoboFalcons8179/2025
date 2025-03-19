package frc.robot.calcVelocities;

import frc.robot.subsystems.vision.Vision;

public class DistanceYaw implements Distance {
  Vision visionSub;

  public DistanceYaw(Vision visionSub) {
    this.visionSub = visionSub;
  }

  public double getVelocity() {
    return visionSub.getCameraYaw() / 10;
  }

  public boolean isDone() {
    return Math.abs(visionSub.getCameraYaw()) < 1;
  }
}
