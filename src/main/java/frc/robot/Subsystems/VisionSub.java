// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.List;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class VisionSub extends SubsystemBase {
  private PhotonCamera frontCamera = new PhotonCamera(Constants.Vision.FrontCameraValues.cameraName);
  private List<PhotonPipelineResult> frontCameraUnreadResults;

  /** Creates a new VisionSub. */
  public VisionSub() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  /**
   * Sets the unreadResults for the frontCamera, only ever use
   * {@link #frontCamera.getAllUnreadResults()} in this method
   */
  public void setFrontResults() {
    frontCameraUnreadResults = frontCamera.getAllUnreadResults();
  }

  /**
   * @returns List<PhotonPipelineResult> - The unread results from the front camera  
   */
  public List<PhotonPipelineResult> getFrontCameraUnreadResults() {
    return frontCameraUnreadResults;
  }

  /**
   * Does the frontCamera have a target?
   * 
   * @return boolean - Sees or doesn't see an April Tag
   */
  public boolean hasTarget(List<PhotonPipelineResult> cameraUnreadResults) {
    if (!cameraUnreadResults.isEmpty()) {
      return cameraUnreadResults.get(0).hasTargets();
    }

    return false;
  }

  /**
   * If the camera has a target it will return the closet one
   * 
   * @return PhotonTrackedTarget - Will return null if no target is found, HANDLE
   *         that using {@code (target != null)}
   */
  public PhotonTrackedTarget getBestTarget(List<PhotonPipelineResult> cameraUnreadResults) {
    if (hasTarget(cameraUnreadResults)) {
      return cameraUnreadResults.get(0).getBestTarget();
    }

    return null;
  }

  /**
   * Gets the Yaw of the robot in relation to the target it is looking at
   * 
   * @return double - Yaw from Robot to Target
   */
  public double getYaw(List<PhotonPipelineResult> cameraUnreadResults) {
    if (!cameraUnreadResults.isEmpty()) {
      if (cameraUnreadResults.get(0).hasTargets()) {
        PhotonTrackedTarget trackedTarget = getBestTarget(cameraUnreadResults);

        if (trackedTarget == null) {
          return 0;
        }

        return PhotonUtils.calculateDistanceToTargetMeters(
            Constants.Vision.FrontCameraValues.cameraHeightMeters, // Camera Height
            Constants.Vision.AprilTags.tag1HeightMeters, // Tag Height
            Constants.Vision.FrontCameraValues.cameraAngleRadians, // Camera Angle
            Units.degreesToRadians(trackedTarget.pitch) // Tag Angle
        );
      }
    }

    return 0;
  }

  /**
   * Get the distance from the desired camera to the tag in meters
   * @param cameraUnreadResults - Camera to use
   * @return double = Distance from target in Meters, will return -1 if no target was found
   */
  public double getDistance(List<PhotonPipelineResult> cameraUnreadResults) {
    PhotonTrackedTarget target = getBestTarget(cameraUnreadResults);

    if (!cameraUnreadResults.isEmpty() && cameraUnreadResults.get(0).hasTargets() && target != null) {
      return PhotonUtils.calculateDistanceToTargetMeters(
        Constants.Vision.FrontCameraValues.cameraHeightMeters,
        Constants.Vision.AprilTags.tag1HeightMeters,
        Constants.Vision.FrontCameraValues.cameraAngleRadians,
        Units.degreesToRadians(target.pitch)
      );
    }

    return -1;
  }
}
