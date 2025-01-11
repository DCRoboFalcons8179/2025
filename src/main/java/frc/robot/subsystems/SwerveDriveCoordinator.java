// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDriveCoordinator
{
  SwerveDriveWheel leftFrontWheel;
  SwerveDriveWheel leftBackWheel;
  SwerveDriveWheel rightFrontWheel;
  SwerveDriveWheel rightBackWheel;

  public SwerveDriveCoordinator(SwerveDriveWheel leftFrontWheel, SwerveDriveWheel leftBackWheel, SwerveDriveWheel rightFrontWheel, SwerveDriveWheel rightBackWheel)
  {
    this.leftFrontWheel = leftFrontWheel;
    this.leftBackWheel = leftBackWheel;
    this.rightFrontWheel = rightFrontWheel;
    this.rightBackWheel = rightBackWheel;
  }

  public void translate(double direction, double power)
  {
    leftFrontWheel.setDirection(direction);
    leftBackWheel.setDirection(direction);
    rightFrontWheel.setDirection(direction);
    rightBackWheel.setDirection(direction);

    leftFrontWheel.setSpeed(power);
    leftBackWheel.setSpeed(power);
    rightFrontWheel.setSpeed(power);
    rightBackWheel.setSpeed(power);
  }

  public void inplaceTurn(double power)
  {
    leftFrontWheel.setDirection(135.0);
    leftBackWheel.setDirection(45.0);
    rightFrontWheel.setDirection(-45.0);
    rightBackWheel.setDirection(-135.0);

    leftFrontWheel.setSpeed(power);
    leftBackWheel.setSpeed(power);
    rightFrontWheel.setSpeed(power);
    rightBackWheel.setSpeed(power);
  }

  public void translateTurn(double direction, double translatePower, double turnPower) 
  {
    double turnAngle = turnPower * 45.0;

    // if the left front wheel is in the front
    if (closestAngle(direction, 135.0) >= 90.0)
    {
      leftFrontWheel.setDirection(direction + turnAngle);
    }
    // if it's in the back
    else
    {
      leftFrontWheel.setDirection(direction - turnAngle);
    }
    // if the left back wheel is in the front
    if (closestAngle(direction, 225.0) > 90.0)
    {
      leftBackWheel.setDirection(direction + turnAngle);
    }
    // if it's in the back
    else
    {
      leftBackWheel.setDirection(direction - turnAngle);
    }
    // if the right front wheel is in the front
    if (closestAngle(direction, 45.0) > 90.0)
    {
      rightFrontWheel.setDirection(direction + turnAngle);
    }
    // if it's in the back
    else
    {
      rightFrontWheel.setDirection(direction - turnAngle);
    }
    // if the right back wheel is in the front
    if (closestAngle(direction, 315.0) >= 90.0)
    {
      rightBackWheel.setDirection(direction + turnAngle);
    }
    // if it's in the back
    else
    {
      rightBackWheel.setDirection(direction - turnAngle);
    }

    leftFrontWheel.setSpeed(translatePower);
    leftBackWheel.setSpeed(translatePower);
    rightFrontWheel.setSpeed(translatePower);
    rightBackWheel.setSpeed(translatePower);
  }

  public void setSwerveDrive(double direction, double translatePower, double turnPower)
  {
    if ((translatePower == 0.0) && (turnPower != 0.0))
    {
      inplaceTurn(turnPower);
    }
    else
    {
      translateTurn(direction, translatePower, turnPower);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
