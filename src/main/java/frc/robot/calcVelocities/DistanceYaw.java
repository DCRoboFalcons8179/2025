package frc.robot.calcVelocities;

import frc.robot.subsystems.VisionSub;

public class DistanceYaw implements Distance {
  VisionSub visionSub;

  public DistanceYaw(VisionSub visionSub) {
    this.visionSub = visionSub;
  }

  public double getVelocity() {
    return visionSub.getYaw() / 10;
  }

  public boolean isDone() {
    return Math.abs(visionSub.getYaw()) < 1;
  }
}
