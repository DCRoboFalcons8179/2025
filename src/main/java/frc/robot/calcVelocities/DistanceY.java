// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.calcVelocities;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.subsystems.VisionSub;

/** Add your docs here. */
public class DistanceY implements Distance {
  VisionSub visionSub;
  double tagMisses = 0;
  double strafeSpeed = 0;

  // PID for the strafe speed loop
  final double STRAFE_P_GAIN = 0.75;
  final double STRAFE_I_GAIN = 0;
  final double STRAFE_D_GAIN = 0.25;
  PIDController strafeController = new PIDController(STRAFE_P_GAIN, STRAFE_I_GAIN, STRAFE_D_GAIN);

  public DistanceY(VisionSub visionSub) {
    this.visionSub = visionSub;
  }

  public double getVelocity() {

    double distanceY = visionSub.getDistanceY();

    // If the tag is not found, increment the counter by 1
    tagMisses += distanceY != -1 ? 0 : 1;

    if (distanceY != -1) {
      strafeSpeed = strafeController.calculate(distanceY, 0);
      tagMisses = 0;
    } else if (tagMisses >= Constants.Vision.tagFindingTries) {
      System.out.println(tagMisses);
      strafeSpeed = 0;
    }

    SmartDashboard.putNumber("Distance Y", distanceY);

    return strafeSpeed;
  }

  public boolean isDone() {
    double distanceY = visionSub.getDistanceY();
    return Math.abs(distanceY) < Constants.Vision.errorThreshHoldStrafeMeters;
  }
}
