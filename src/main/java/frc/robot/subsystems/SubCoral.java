package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SubCoral extends SubsystemBase {
  // create motor
  private final TalonSRX coralMotor = new TalonSRX(Constants.CoralValues.Motor.coralMotorID);
  private final TalonSRX wristMotor = new TalonSRX(Constants.CoralValues.Wrist.wristID);

  public SubCoral() {
    TalonSRXConfiguration wristConfigs = new TalonSRXConfiguration();

    wristMotor.configFactoryDefault();

    // Configure PID values
    wristConfigs.slot0.kP = Constants.CoralValues.Wrist.kP;
    wristConfigs.slot0.kI = Constants.CoralValues.Wrist.kI;
    wristConfigs.slot0.kD = Constants.CoralValues.Wrist.kD;
    wristConfigs.slot0.kF = Constants.CoralValues.Wrist.kF;

    // Configure feedback sensor
    wristConfigs.primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder;

    // Apply configuration to the motor
    wristMotor.configAllSettings(wristConfigs);

    // Set the sensor position to 0 on startup
    wristMotor.setSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // Display the wrist position on SmartDashboard
    SmartDashboard.putNumber("Wrist Position", wristMotor.getSelectedSensorPosition());
  }

  // Move the coral motor with percent power
  public void moveCoral(double power) {
    coralMotor.set(TalonSRXControlMode.PercentOutput, power);
  }

  // Move the wrist motor to a specific position
  public void moveWrist(double position) {
    wristMotor.set(TalonSRXControlMode.Position, position);
    // wristMotor.set(TalonSRXControlMode.PercentOutput, position);
  }
}
