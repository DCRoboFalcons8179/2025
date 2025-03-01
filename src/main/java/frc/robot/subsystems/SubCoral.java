package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubCoral extends SubsystemBase {
  // create motor
  TalonFX coralMotor = new TalonFX(Constants.CoralValues.coralMotorID);
  TalonFX wristMotor = new TalonFX(Constants.CoralValues.wristID);

  public SubCoral() {}

  // Get percent power
  public void moveCoral(double power) {
    coralMotor.set(power);
  }

  // Get Power
  public void moveWrist(double power) {
    wristMotor.set(power);
  }
}
