// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HookSub extends SubsystemBase {
  /** Insert Id later */
  TalonFX hook = new TalonFX(Constants.HookInfo.HookID);

  Encoder hookEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);

  public HookSub() {
    TalonFXConfiguration hookConfiguration = new TalonFXConfiguration();
    // Hook PID
    hookConfiguration.Slot0.kP = Constants.HookInfo.HookPID.kP;
    hookConfiguration.Slot0.kI = Constants.HookInfo.HookPID.kI;
    hookConfiguration.Slot0.kD = Constants.HookInfo.HookPID.kD;

    hook.getConfigurator().apply(new TalonFXConfiguration());

    hook.getConfigurator().apply(hookConfiguration.Slot0);

    hook.setNeutralMode(NeutralModeValue.Brake);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Hook Position", hook.getPosition().getValueAsDouble());
  }

  public void setPosition(double position) {
    hook.setControl(new PositionVoltage(hook.getPosition().getValueAsDouble() + position));
  }

  public void hang(double power) {
    hook.set(power);
  }
}
