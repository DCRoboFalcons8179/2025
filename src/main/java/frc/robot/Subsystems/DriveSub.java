// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.TalonFXConfigurator;

public class DriveSub extends SubsystemBase {
  // Left Motors
  private TalonFX leftDrive;
  private TalonFX leftFollower;

  // Right Motors
  private TalonFX rightDrive;
  private TalonFX rightFollower;
  

  /** DriveSub Constructor */
  public DriveSub() {
    leftDrive = TalonFXConfigurator.getConfiguredTalonFX(Constants.DriveValues.leftDriveID, Constants.DriveValues.leftDriveInvert);
    leftFollower = TalonFXConfigurator.getConfiguredTalonFX(Constants.DriveValues.leftFollowerID, Constants.DriveValues.leftFollowerInvert);
      
    rightDrive = TalonFXConfigurator.getConfiguredTalonFX(Constants.DriveValues.rightDriveID, Constants.DriveValues.rightDriveInvert);
    rightFollower = TalonFXConfigurator.getConfiguredTalonFX(Constants.DriveValues.rightFollowerID, Constants.DriveValues.rightFollowerInvert);
    
  }


  /**
   * Moves the motors with a given forwardPower & turningPower
   * @param forwardPower
   * @param turnPower
   * @return void
   */
  public void drive(double forwardPower, double turnPower) {
    // Ok, this is basically saying follow the motor with the ID of leftDrive and don't go the opposite direction
    leftFollower.setControl(new Follower(Constants.DriveValues.leftDriveID, false));
    rightFollower.setControl(new Follower(Constants.DriveValues.rightFollowerID, false));

    // Left & Right Speeds
    double leftSpeed = forwardPower + turnPower;
    double rightSpeed = forwardPower - turnPower;

    // Request for controlling motors
    var request = new VelocityVoltage(0).withSlot(0);

    leftDrive.setControl(request.withVelocity(leftSpeed * Constants.DriveValues.maxRPS));
    rightDrive.setControl(request.withVelocity(rightSpeed * Constants.DriveValues.maxRPS));
  }

  @Override
  public void periodic() {
    if (Constants.debug) {
      SmartDashboard.putNumber("Left Drive Velocity", leftDrive.getVelocity().getValueAsDouble());
      SmartDashboard.putNumber("Right Drive Velocity", rightDrive.getVelocity().getValueAsDouble());
    }
  }

  /**
   * Drive that takes no turning argument, overrides the above drive
   * @param forwardPower
   * @return void
   */
  public void drive(double forwardPower) {
    drive(forwardPower, 0);
  }
}
