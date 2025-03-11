// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HookSub extends SubsystemBase {
  /** TalonFX motor controller for the hook */
  private final TalonFX hook = new TalonFX(Constants.HookInfo.HookID);

  /** PositionVoltage control for closed-loop position control */
  private final PositionVoltage positionControl = new PositionVoltage(0);

  public HookSub() {
    // Configure the TalonFX motor controller
    TalonFXConfiguration hookConfiguration = new TalonFXConfiguration();

    // Set PID values
    hookConfiguration.Slot0.kP = Constants.HookInfo.HookPID.kP;
    hookConfiguration.Slot0.kI = Constants.HookInfo.HookPID.kI;
    hookConfiguration.Slot0.kD = Constants.HookInfo.HookPID.kD;

    // Apply the configuration
    hook.getConfigurator().apply(hookConfiguration);

    // Set the motor to brake mode
    hook.setNeutralMode(NeutralModeValue.Brake);

    // Reset the encoder position to 0 (if needed)
    hook.setPosition(0);
  }

  @Override
  public void periodic() {
    // Update SmartDashboard with hook position and other data
    SmartDashboard.putNumber("Hook Position", hook.getPosition().getValueAsDouble());
    SmartDashboard.putNumber("Hook Velocity", hook.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Hook Current", hook.getStatorCurrent().getValueAsDouble());
  }

  /**
   * Set the hook to a specific position using closed-loop control.
   *
   * @param position The desired position in encoder units.
   */
  public void setPosition(double position) {
    // Use PositionVoltage control to move to the desired position
    hook.setControl(positionControl.withPosition(hook.getPosition().getValueAsDouble() + position));
  }

  /**
   * Manually control the hook using open-loop power.
   *
   * @param power The power to apply to the hook motor (between -1.0 and 1.0).
   */
  public void hang(double power) {
    hook.set(power);
  }
}
