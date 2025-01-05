// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.List;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class VisionSub extends SubsystemBase {
  public PhotonCamera frontCamera = new PhotonCamera(Constants.Vision.FrontCamera.cameraName);
  private List<PhotonPipelineResult> frontCameraUnreadResults;

  /** Creates a new VisionSub. */
  public VisionSub() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public class frontCamera {
    /**
     * Sets the unreadResults for the frontCamera, only ever use
     * {@link #frontCamera.getAllUnreadResults()} in this method
     */
    public void setResults() {
      frontCameraUnreadResults = frontCamera.getAllUnreadResults();
    }

    /**
     * Does the frontCamera have a target?
     * 
     * @return boolean - Sees or doesn't see an April Tag
     */
    public boolean hasTarget() {
      return !frontCameraUnreadResults.isEmpty() ? frontCameraUnreadResults.get(0).hasTargets() : false;
    }

    public PhotonTrackedTarget getBestTarget() {
      if (hasTarget()) {
        return frontCameraUnreadResults.get(0).getBestTarget();
      }

      return null;
    }

    public double getYaw() {
      if (!frontCameraUnreadResults.isEmpty()) {
        if (frontCameraUnreadResults.get(0).hasTargets()) {
          return PhotonUtils.calculateDistanceToTargetMeters(
            Constants.Vision.FrontCamera.cameraHeightMeters, // Camera Height
            Constants.Vision.AprilTags.tag1HeightMeters, // Tag Height
            Constants.Vision.FrontCamera.cameraAngleRadians, // Camera Angle
            Units.degreesToRadians(getBestTarget().pitch) // Tag Angle
          );
        }
      }

      return 0;
    }
  }
}
