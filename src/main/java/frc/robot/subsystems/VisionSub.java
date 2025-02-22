// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

public class VisionSub extends SubsystemBase {
  private PhotonCamera camera = new PhotonCamera(Constants.Vision.FrontCameraValues.cameraName);

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public boolean hasTarget() {
    PhotonPipelineResult result = camera.getLatestResult();
    return result.hasTargets();
  }

  public PhotonCamera getFrontCamera() {
    return camera;
  }

  public int getBestTargetID() {
    PhotonPipelineResult result = camera.getLatestResult();
    if (result.hasTargets()) {
      return result.getBestTarget().getFiducialId();
    }
    return -1;
  }

  public PhotonTrackedTarget getBestTarget() {
    PhotonPipelineResult result = camera.getLatestResult();
    if (hasTarget()) {
      return result.getBestTarget();
    }
    return null;
  }

  public Transform3d getTransform3dTo3dTarget() {
    if (hasTarget()) {
      return getBestTarget().getBestCameraToTarget();
    } else {
      return new Transform3d(new Transform2d(0, 0, new Rotation2d(0)));
    }
  }

  public double getYaw() {
    PhotonPipelineResult result = camera.getLatestResult();
    if (result.hasTargets()) {
      return getTransform3dTo3dTarget().getRotation().getZ();
    }

    return 181;
  }

  public double getRemappedYaw() {
    PhotonPipelineResult result = camera.getLatestResult();
    if (result.hasTargets()) {
      double yaw = getTransform3dTo3dTarget().getRotation().getZ();
      return yaw < 0 ? yaw + Math.PI : yaw - Math.PI;
    }

    return 181;
  }

  public double getDistanceX() {
    if (hasTarget()) {
      return getTransform3dTo3dTarget().getX();
    }
    return -1;
  }

  public double getDistanceY() {
    if (hasTarget()) {
      return getTransform3dTo3dTarget().getY();
    }
    return -1;
  }
}
