// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HookSub extends SubsystemBase {
  /** Insert Id later */
  TalonFX hook = new TalonFX(0);
  Encoder hookEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k2X);
  public void driveHook(double algaePower) {
    TalonFXConfiguration hookConfiguration = new TalonFXConfiguration();
    //Hook PID
    hookConfiguration.Slot0.kP = Constants.Hook.kP;
    hookConfiguration.Slot0.kI = Constants.Hook.kI;
    hookConfiguration.Slot0.kD = Constants.Hook.kD;

    hook.getConfigurator().apply( new TalonFXConfiguration());

    hook.getConfigurator().apply(hookConfiguration.Slot0);

    //ADD SOMETHING FOR ENCODERS HERE. I do not know how to make it work with TalonFX. (:
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
