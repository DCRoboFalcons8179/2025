package frc.robot.calcVelocities;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants;
import frc.robot.subsystems.VisionSub;

public class DistanceX implements Distance {
  VisionSub visionSub;

  double targetMisses = 0;
  double forwardSpeed = 0;

  // PID for the forward speed loop
  final double P_GAIN = 2.25;
  final double I_GAIN = 0;
  final double D_GAIN = 0.25;
  PIDController controller = new PIDController(P_GAIN, I_GAIN, D_GAIN);

  public DistanceX(VisionSub visionSub) {
    this.visionSub = visionSub;
  }

  public double getVelocity() {
    double distanceX = visionSub.getDistanceX();

    if (distanceX > 1.5) {
      forwardSpeed = -2;
      targetMisses = 0;
    } else if (distanceX != -1) {
      forwardSpeed = controller.calculate(distanceX, 1);
      targetMisses = 0;
    } else if (targetMisses >= Constants.Vision.tagFindingTries) {
      System.out.println(targetMisses);
      forwardSpeed = 0;
    }

    return forwardSpeed;
  }

  public boolean isDone() {
    double distanceX = visionSub.getDistanceX();
    return Math.abs(distanceX - Constants.Vision.desiredXTagDistanceMeters)
        < Constants.Vision.errorThreshHoldMeters;
  }
}
