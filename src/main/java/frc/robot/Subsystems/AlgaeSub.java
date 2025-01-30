// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.math.Filter;
import frc.robot.Constants;

public class AlgaeSub extends SubsystemBase {
  //Put in Motor Id later
  VictorSPX Algae = new VictorSPX(0);
  
  public AlgaeSub() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void driveAlgea(double AlgaePower){
    //Algea PID
    Algae.set(VictorSPXControlMode.PercentOutput, AlgaePower);
    

  }
}
