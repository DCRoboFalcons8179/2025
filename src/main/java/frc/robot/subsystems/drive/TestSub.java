// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drive;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestSub extends SubsystemBase {
  TalonFX talonFX = new TalonFX(10);

  /** Creates a new testSub. */
  public TestSub() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive() {
    talonFX.set(0.1);
  }
}
