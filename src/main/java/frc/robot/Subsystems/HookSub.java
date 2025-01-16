// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.RemoteFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.VictorSPXConfiguration;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HookSub extends SubsystemBase {
  /** Insert Id later */
  VictorSPX hook = new VictorSPX(0);
  Encoder hookEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);
  public HookSub() {
    VictorSPXConfiguration hookConfiguration = new VictorSPXConfiguration();
    hookEncoder.setMaxPeriod(1);

    hookConfiguration.slot0.kP = Constants.Hook.kP;
    hookConfiguration.slot0.kI = Constants.Hook.kI;
    hookConfiguration.slot0.kD = Constants.Hook.kD;

    hook.config_kP(0, Constants.Hook.kP);
    hook.config_kI(0, Constants.Hook.kI);
    hook.config_kD(0, Constants.Hook.kD);
    hook.config_kF(0, Constants.Hook.kF);
    hook.configSelectedFeedbackSensor(RemoteFeedbackDevice.None,
      Constants.Hook.feedbackSensor, 10);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
