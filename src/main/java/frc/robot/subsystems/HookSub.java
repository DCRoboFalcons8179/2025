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
  private final TalonFX hook = new TalonFX(Constants.HookConstants.HookID);

  /** PositionVoltage control for closed-loop position control */
  private final PositionVoltage positionControl = new PositionVoltage(0);

  /** Desired position for the hook */
  // private double desiredPosition = 0;

  /** State to track if the hook is in hanging mode */
  // private boolean hung = false;

  public HookSub() {
    // Configure the TalonFX motor controller
    TalonFXConfiguration hookConfiguration = new TalonFXConfiguration();

    // Set PID values
    hookConfiguration.Slot0.kP = Constants.HookConstants.HookPID.kP;
    hookConfiguration.Slot0.kI = Constants.HookConstants.HookPID.kI;
    hookConfiguration.Slot0.kD = Constants.HookConstants.HookPID.kD;

    // Apply the configuration
    // hook.getConfigurator().apply(hookConfiguration);

    // Set the motor to brake mode
    hook.setNeutralMode(NeutralModeValue.Brake);

    // Reset the encoder position to 0
    hook.setPosition(0);
  }

  @Override
  public void periodic() {
    // Update SmartDashboard with hook position and other data
    SmartDashboard.putNumber("Hook Position", getCurrentPosition());
    SmartDashboard.putNumber("Hook Velocity", hook.getVelocity().getValueAsDouble());
    SmartDashboard.putNumber("Hook Current", hook.getStatorCurrent().getValueAsDouble());
    SmartDashboard.putNumber("Hook Temperature", hook.getDeviceTemp().getValueAsDouble());
  }

  /**
   * Set the hook to a specific position using closed-loop control.
   *
   * @param position The desired position in encoder units.
   */
  public void setPosition(double position) {
    hook.setControl(positionControl.withPosition(hook.getPosition().getValueAsDouble() + position));
  }

  /**
   * Manually control the hook using open-loop power.
   *
   * @param power The power to apply to the hook motor (between -1.0 and 1.0).
   */
  public void hang(double power) {
    // applies the commanded power to the hook motor for hanging
    hook.set(power);
    // hung = true;
  }

  /**
   * Get the current position of the hook.
   *
   * @return The current position in encoder units.
   */
  public double getCurrentPosition() {
    return hook.getPosition().getValueAsDouble();
  }

  /** Reset the hung state. */
  public void resetHung() {
    // hung = false;
  }
}
