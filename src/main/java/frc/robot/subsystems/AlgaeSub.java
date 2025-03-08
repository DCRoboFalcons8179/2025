// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeSub extends SubsystemBase {

  VictorSPX algae = new VictorSPX(Constants.AlgaeInfo.AlgaeID);
  /** Creates a new AlgaeSub. */
  public AlgaeSub() {}

  public void driveAlgea(double AlgaePower) {
    VictorSPXConfiguration algaeConfiguration = new VictorSPXConfiguration();
    // Algea PID
    algaeConfiguration.slot0.kP = Constants.AlgaeInfo.AlgaePID.kP;
    algaeConfiguration.slot0.kI = Constants.AlgaeInfo.AlgaePID.kI;
    algaeConfiguration.slot0.kD = Constants.AlgaeInfo.AlgaePID.kD;
    algaeConfiguration.slot0.kF = Constants.AlgaeInfo.AlgaePID.kF;

    algae.getAllConfigs(algaeConfiguration);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
