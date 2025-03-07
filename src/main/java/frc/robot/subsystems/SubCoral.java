package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubCoral extends SubsystemBase {
  // create motor
  TalonFX coralMotor = new TalonFX(Constants.CoralValues.Motor.coralMotorID);
  TalonFX wristMotor = new TalonFX(Constants.CoralValues.Wrist.wristID);
  RelativeEncoder relativeEncoder;

  public SubCoral() {
    TalonFXConfiguration wristConfigs = new TalonFXConfiguration();

    wristConfigs.Slot0 = new Slot0Configs();
    wristConfigs.Slot0.kP = Constants.CoralValues.Wrist.kP;
    wristConfigs.Slot0.kI = Constants.CoralValues.Wrist.kI;
    wristConfigs.Slot0.kD = Constants.CoralValues.Wrist.kD;
    wristConfigs.Slot0.kS = Constants.CoralValues.Wrist.kF;

    wristMotor.getConfigurator().apply(wristConfigs);
  }

  // Move the coral motor with percent power
  public void moveCoral(double power) {
    coralMotor.set(power);
  }

  // Move the wrist motor to a specific position
  public void moveWrist(double position) {
    wristMotor.setPosition(wristMotor.getPosition().getValueAsDouble() + position);
  }
}
