// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import org.photonvision.PhotonCamera;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class NewVision extends SubsystemBase {
  /**Front Camera Object */
  private PhotonCamera frontCamera = new PhotonCamera(Constants.VisionConstants.FrontCameraValues.cameraName);
  /**Does the Front Camera have a Target */
  private boolean frontHasTarget;
  /**ID of the Best Tag from the Front Camera */
  private int frontTargetID;
  /**Distance from the Best Tag to the Front Camera */
  private Transform3d frontTransform3To3dTarget;
  /**Yaw from the tag to the Front Camera */
  private double frontCameraYaw;
  /**Distance from the Front Camera X */
  private double frontDistanceX;
  /**Distance from the Front Camera Y */
  private double frontDistanceY;

  /** Creates a new NewVision. */
  public NewVision() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void update() {
    for (var result : frontCamera.getAllUnreadResults()) {
      if (result.hasTargets()) {
        // Front Camera
        frontHasTarget = true;
        frontTargetID = result.getBestTarget().fiducialId;
        frontTransform3To3dTarget = result.getBestTarget().getBestCameraToTarget(); 

        // Yaw
        frontCameraYaw = frontTransform3To3dTarget.getRotation().getZ();
        frontCameraYaw = frontCameraYaw < 0 ? -frontCameraYaw - Math.PI : -frontCameraYaw + Math.PI;

        // X
        frontDistanceX = frontTransform3To3dTarget.getX();
        
        // Y
        frontDistanceY = frontTransform3To3dTarget.getY();
      }
    }
  }

  public boolean getFrontHasTarget() {
    return frontHasTarget;
  }

  public int getFrontTargetID() {
      return frontTargetID;
  }

  public Transform3d getFrontTransform3To3dTarget() {
      return frontTransform3To3dTarget;
  }

  public double getFrontDistanceX() {
      return frontDistanceX;
  }

  public double getFrontDistanceY() {
      return frontDistanceY;
  }

  public double getFrontCameraYaw() {
      return frontCameraYaw;
  }
}
