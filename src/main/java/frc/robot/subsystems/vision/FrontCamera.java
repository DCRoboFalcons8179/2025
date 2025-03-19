package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.Constants;
import org.photonvision.PhotonCamera;

public class FrontCamera extends Vision {

  /** Front Camera Object */
  private PhotonCamera camera =
      new PhotonCamera(Constants.VisionConstants.FrontCameraValues.cameraName);
  /** Does the Front Camera have a Target */
  private boolean hasTarget;
  /** ID of the Best Tag from the Front Camera */
  private int targetID;
  /** Distance from the Best Tag to the Front Camera */
  private Transform3d transform3To3dTarget;
  /** Yaw from the tag to the Front Camera */
  private double cameraYaw;
  /** Distance from the Front Camera X */
  private double distanceX;
  /** Distance from the Front Camera Y */
  private double distanceY;

  /** Creates a new NewVision. */
  public FrontCamera() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void update() {
    for (var result : camera.getAllUnreadResults()) {
      if (result.hasTargets()) {
        // Front Camera
        hasTarget = true;
        targetID = result.getBestTarget().fiducialId;
        transform3To3dTarget = result.getBestTarget().getBestCameraToTarget();

        // Yaw
        cameraYaw = transform3To3dTarget.getRotation().getZ();
        cameraYaw = cameraYaw < 0 ? -cameraYaw - Math.PI : -cameraYaw + Math.PI;

        // X
        distanceX = transform3To3dTarget.getX();

        // Y
        distanceY = transform3To3dTarget.getY();
      } else {
        hasTarget = false;
        targetID = -1;
        distanceX = -1;
        distanceY = -1;
        cameraYaw = 181;
      }
    }
  }

  public boolean getHasTarget() {
    return hasTarget;
  }

  public int getTargetID() {
    return targetID;
  }

  public Transform3d getTransform3To3dTarget() {
    return transform3To3dTarget;
  }

  public double getDistanceX() {
    return distanceX;
  }

  public double getDistanceY() {
    return distanceY;
  }

  public double getCameraYaw() {
    return cameraYaw;
  }
}
