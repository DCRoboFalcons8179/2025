package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSub extends SubsystemBase {
  // this is the motor we are using for the motor
  private SparkMax elevatorMotor = new SparkMax(Constants.Elevator.motorID, MotorType.kBrushless);
  // create the follower motor
  private SparkMax followerMotor =
      new SparkMax(Constants.Elevator.followerMotorID, MotorType.kBrushless);

  private SparkClosedLoopController elevatorSparkClosedLoopController;
  private SparkClosedLoopController followerSparkClosedLoopController;

  // create the elevator encoder
  RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
  // create the follower encoder
  RelativeEncoder followerEncoder = followerMotor.getEncoder();

  // config for the elevator encoder
  public ElevatorSub() {

    SparkMaxConfig elevatorConfig = new SparkMaxConfig();

    elevatorConfig.inverted(false).idleMode(IdleMode.kBrake);

    elevatorConfig.encoder.positionConversionFactor(1000).velocityConversionFactor(1000);
    elevatorConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    elevatorMotor.configure(
        elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    elevatorSparkClosedLoopController = elevatorMotor.getClosedLoopController();

    SparkMaxConfig followerConfig = new SparkMaxConfig();

    followerConfig.inverted(false).idleMode(IdleMode.kCoast);

    followerConfig.encoder.positionConversionFactor(1000).velocityConversionFactor(1000);
    followerConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    followerMotor.configure(
        elevatorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

    followerSparkClosedLoopController = followerMotor.getClosedLoopController();
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber(getName(), elevatorSparkBase.get());
    SmartDashboard.putNumber("Elevator Position", elevatorEncoder.getPosition());
    SmartDashboard.putNumber("Elevator Temperature Celsius", elevatorMotor.getMotorTemperature());
    SmartDashboard.putNumber("Elevator Amps", elevatorMotor.getOutputCurrent());
  }

  // use this function later for if/when we do setpoints
  public void setPosition(double position) {
    elevatorSparkClosedLoopController.setReference(
        elevatorEncoder.getPosition() + position, ControlType.kPosition);
    // elevatorMotor.set(position);
    // followerMotor.set(position);
    // followerSparkClosedLoopController.setReference(position, ControlType.kPosition);
  }
}
