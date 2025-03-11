package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
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
import frc.lib.math.Filter;
import frc.robot.Constants;

public class ElevatorSub extends SubsystemBase {
  // this is the motor we are using for the motor
  private SparkMax elevatorMotor = new SparkMax(Constants.Elevator.driverID, MotorType.kBrushless);
  // create the follower motor
  private SparkMax followerMotor =
      new SparkMax(Constants.Elevator.followerMotorID, MotorType.kBrushless);

  private SparkClosedLoopController elevatorSparkClosedLoopController;
  private SparkClosedLoopController followerSparkClosedLoopController;

  // create the elevator encoder
  RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
  RelativeEncoder followerEncoder = followerMotor.getEncoder();

  private double desiredPos = 0;

  // config for the elevator encoder
  public ElevatorSub() {

    SparkMaxConfig elevatorConfig = new SparkMaxConfig();

    elevatorConfig.inverted(false).idleMode(IdleMode.kBrake);

    elevatorConfig.encoder.positionConversionFactor(1000).velocityConversionFactor(1000);
    elevatorConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    // elevatorConfig.smartCurrentLimit(Constants.Elevator.currentLimit);

    elevatorMotor.configure(
        elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    elevatorSparkClosedLoopController = elevatorMotor.getClosedLoopController();

    SparkMaxConfig followerConfig = new SparkMaxConfig();

    followerConfig.inverted(false).idleMode(IdleMode.kBrake);

    followerConfig.encoder.positionConversionFactor(1000).velocityConversionFactor(1000);

    // followerConfig.follow(Constants.Elevator.driverID);

    followerConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    // followerConfig.smartCurrentLimit(Constants.Elevator.currentLimit);

    followerSparkClosedLoopController = followerMotor.getClosedLoopController();

    followerMotor.configure(
        followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Zeros Position
    elevatorEncoder.setPosition(0);
    followerEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    // SmartDashboard.putNumber(getName(), elevatorSparkBase.get());
    SmartDashboard.putNumber("Elevator Driver Position", elevatorEncoder.getPosition());
    SmartDashboard.putNumber(
        "Elevator Driver Temperature Celsius", elevatorMotor.getMotorTemperature());
    SmartDashboard.putNumber(
        "Elevator Follower Temperature Celsius", followerMotor.getMotorTemperature());
    SmartDashboard.putNumber("Elevator Driver Amps", elevatorMotor.getOutputCurrent());
    SmartDashboard.putNumber("Elevator Follower Amps", followerMotor.getOutputCurrent());
    SmartDashboard.putNumber("Elevator Follower Position", followerEncoder.getPosition());
    SmartDashboard.putBoolean("Follower Command", followerMotor.isFollower());
  }

  public double getPose() {
    return elevatorEncoder.getPosition();
  }

  public void updatePosition() {
    double limitedPose = Filter.cutoffFilter(desiredPos, Constants.Elevator.maxHeight, 0);

    SmartDashboard.putNumber("Desired Elevator Position", limitedPose);

    // Calculate the direction of movement
    double currentPose = elevatorEncoder.getPosition();
    double direction = Math.signum(limitedPose - currentPose);

    // Apply feedforward based on direction
    double feedforward = 0;
    if (direction > 0) {
      // Position PID with feedforward
      elevatorSparkClosedLoopController.setReference(
          limitedPose, ControlType.kPosition, ClosedLoopSlot.kSlot0, feedforward);
      followerSparkClosedLoopController.setReference(
          limitedPose, ControlType.kPosition, ClosedLoopSlot.kSlot0, feedforward);
    } else if (direction < 0) {
      elevatorMotor.set(0);
      followerMotor.set(0);
    }
  }

  public void goToPose(double position) {
    desiredPos = Filter.cutoffFilter(position, Constants.Elevator.maxHeight, 0);
  }

  public void resetPose() {
    elevatorEncoder.setPosition(0);
    followerEncoder.setPosition(0);
  }
}
