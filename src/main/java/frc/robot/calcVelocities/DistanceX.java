package frc.robot.calcVelocities;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.subsystems.vision.Vision;

public class DistanceX implements Distance {
  Vision visionSub;

  double forwardSpeed = 0;
  double desiredXTagDistanceMeters;

  int tagMisses = 0;

  // PID for the forward speed loop
  final double P_GAIN = 2.25;
  final double I_GAIN = 0;
  final double D_GAIN = 0.25;
  PIDController controller = new PIDController(P_GAIN, I_GAIN, D_GAIN);

  public DistanceX(Vision visionSub, double desiredXTagDistanceMeters) {
    this.visionSub = visionSub;
    this.desiredXTagDistanceMeters = desiredXTagDistanceMeters;
  }

  public double getVelocity() {
    double distanceX = visionSub.getDistanceX();

    // If the tag is not found, increment the counter by 1
    tagMisses += distanceX != -1 ? 0 : 1;

    if (distanceX > 1.5) {
      forwardSpeed = -2;
      tagMisses = 0;
    } else if (distanceX != -1) {
      forwardSpeed = controller.calculate(distanceX, desiredXTagDistanceMeters);
      tagMisses = 0;
    } else if (tagMisses >= Constants.VisionConstants.tagFindingTries) {
      forwardSpeed = 0;
    }

    return forwardSpeed;
  }

  public boolean isDone() {
    double distanceX = visionSub.getDistanceX();
    return Math.abs(distanceX - desiredXTagDistanceMeters)
        < Constants.VisionConstants.errorThreshHoldMeters;
  }
}
