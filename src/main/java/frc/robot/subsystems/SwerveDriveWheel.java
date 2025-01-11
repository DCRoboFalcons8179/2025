// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDriveWheel
{
  public PIDController directionController;
  public PIDOutput directionMotor;
  public PIDOutputExtended speedMotor;
  public PIDSource directionSensor;

  public SwerveDriveWheel(double P, double I, double D, PIDSource directionSensor, PIDOutput directionMotor, PIDOutput speedMotor)
  {
    this.directionSensor = directionSensor;
    this.directionMotor = directionMotor;
    this.speedMotor = new PIDOutputExtended(speedMotor);
    directionController = new PIDController(P, I, D, directionSensor, directionMotor);
  }

  public void setDirection(double setpoint)
  {
    directionController.reset();

    double currentAngle = directionSensor.get();
    // find closest angle to setpoint
    double setpointAngle = closestAngle(currentAngle, setpoint);
    // find closest angle to setpoint + 180
    double setpointAngleFlipped = closestAngle(currentAngle, setpoint + 180.0);
    // if the closest angle to setpoint is shorter
    if (Math.abs(setpointAngle) <= Math.abs(setpointAngleFlipped))
    {
      // unflip the motor direction use the setpoint
      directionMotor.setGain(1.0);
      directionController.setSetpoint(currentAngle + setpointAngle);
    }
    // if the closest angle to setpoint + 180 is shorter
    else
    {
      // flip the motor direction and use the setpoint + 180
      directionMotor.setGain(-1.0);
      directionController.setSetpoint(currentAngle + setpointAngleFlipped);
    }

    directionController.enable();
  }

  public void setSpeed(double speed)
  {
    speedMotor.set(speed);
  }

  @Override
public void periodic() {
    // This method will be called once per scheduler run
  }
}
