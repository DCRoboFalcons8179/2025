package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.SlotConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubCoral extends SubsystemBase {
  // create motor
  TalonSRX coralMotor = new TalonSRX(Constants.CoralValues.Motor.coralMotorID);
  TalonSRX wristMotor = new TalonSRX(Constants.CoralValues.Wrist.wristID);
  RelativeEncoder relativeEncoder;

  public SubCoral() {
    TalonSRXConfiguration wristConfigs = new TalonSRXConfiguration();

    wristConfigs.slot0 =
        new SlotConfiguration() {
          {
            kP = Constants.CoralValues.Wrist.kP;
            kI = Constants.CoralValues.Wrist.kI;
            kD = Constants.CoralValues.Wrist.kD;
            kF = Constants.CoralValues.Wrist.kF;
          }
        };

    wristConfigs.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;

    wristMotor.configAllSettings(wristConfigs);
  }

  // Get percent power
  public void moveCoral(double power) {
    coralMotor.set(TalonSRXControlMode.PercentOutput, power);
  }

  // Get Power
  public void moveWrist(double position) {
    wristMotor.setSelectedSensorPosition(wristMotor.getSelectedSensorPosition() + position);
  }
}
