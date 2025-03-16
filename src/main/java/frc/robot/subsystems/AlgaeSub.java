// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeSub extends SubsystemBase {

  VictorSPX algae;
  /** Creates a new AlgaeSub. */
  public AlgaeSub() {
    algae = new VictorSPX(Constants.Algae.AlgaeID);
    algae.setNeutralMode(NeutralMode.Brake);
  }

  /**
   *
   *
   * <h3>Moves the Algae with percent output
   *
   * @param AlgaePower
   */
  public void driveAlgae(double AlgaePower) {
    algae.set(VictorSPXControlMode.PercentOutput, AlgaePower);
  }
}
