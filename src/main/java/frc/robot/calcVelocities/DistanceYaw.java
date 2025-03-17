package frc.robot.calcVelocities;

import frc.robot.subsystems.NewVision;
public class DistanceYaw implements Distance {
  NewVision visionSub;

  public DistanceYaw(NewVision visionSub) {
    this.visionSub = visionSub;
  }

  public double getVelocity() {
    return visionSub.getFrontCameraYaw() / 10;
  }

  public boolean isDone() {
    return Math.abs(visionSub.getFrontCameraYaw()) < 1;
  }
}