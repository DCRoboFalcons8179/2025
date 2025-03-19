// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {
  /** Creates a new NewVision. */
  public Vision() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void update() {}

  public boolean getHasTarget() {
    return false;
  }

  public int getTargetID() {
    return -1;
  }

  public Transform3d getTransform3To3dTarget() {
    return new Transform3d();
  }

  public double getDistanceX() {
    return -1;
  }

  public double getDistanceY() {
    return -1;
  }

  public double getCameraYaw() {
    return 181;
  }
}
