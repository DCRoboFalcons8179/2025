// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeSub extends SubsystemBase {
  // Put in Motor Id later
  VictorSPX algae = new VictorSPX(0);

  public AlgaeSub() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void driveAlgea(double AlgaePower) {
    VictorSPXConfiguration algaeConfiguration = new VictorSPXConfiguration();
    // Algea PID
    algaeConfiguration.slot0.kP = Constants.AlgaePID.kP;
    algaeConfiguration.slot0.kI = Constants.AlgaePID.kI;
    algaeConfiguration.slot0.kD = Constants.AlgaePID.kD;
    algaeConfiguration.slot0.kF = Constants.AlgaePID.kF;

    algae.getAllConfigs(algaeConfiguration);

    // Replace "feedbackSensor" when I know what to call it. (:
    algae.configSelectedFeedbackSensor(
        RemoteFeedbackDevice.None, Constants.Algae.feedbackSensor, 10);
  }
}
