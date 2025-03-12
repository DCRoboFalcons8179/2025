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
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.math.Filter;
import frc.robot.Constants;

public class ElevatorSub extends SubsystemBase {
  private SparkMax elevatorMotor = new SparkMax(Constants.Elevator.driverID, MotorType.kBrushless);
  private SparkMax followerMotor =
      new SparkMax(Constants.Elevator.followerMotorID, MotorType.kBrushless);

  private SparkClosedLoopController elevatorSparkClosedLoopController;
  private SparkClosedLoopController followerSparkClosedLoopController;

  private RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
  private RelativeEncoder followerEncoder = followerMotor.getEncoder();

  private double desiredPos = 0;
  private double limitedDesiredPos = 0; // The slew-rate-limited desired position

  // Slew rate limiter to limit how quickly the desired position can change
  private SlewRateLimiter slewRateLimiter = new SlewRateLimiter(Constants.Elevator.slewRateLimit);

  public ElevatorSub() {
    SparkMaxConfig elevatorConfig = new SparkMaxConfig();
    elevatorConfig.inverted(false).idleMode(IdleMode.kBrake);
    elevatorConfig.encoder.positionConversionFactor(1000).velocityConversionFactor(1000);
    elevatorConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    elevatorConfig.closedLoop.maxOutput(0.1);
    elevatorConfig.closedLoop.minOutput(0);

    elevatorMotor.configure(
        elevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    elevatorSparkClosedLoopController = elevatorMotor.getClosedLoopController();

    SparkMaxConfig followerConfig = new SparkMaxConfig();
    followerConfig.inverted(false).idleMode(IdleMode.kBrake);
    followerConfig.encoder.positionConversionFactor(1000).velocityConversionFactor(1000);
    followerConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);

    followerSparkClosedLoopController = followerMotor.getClosedLoopController();

    elevatorConfig.closedLoop.maxOutput(0.1);
    elevatorConfig.closedLoop.minOutput(0);

    followerMotor.configure(
        followerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Zero the encoders
    elevatorEncoder.setPosition(0);
    followerEncoder.setPosition(0);
  }

  @Override
  public void periodic() {
    // Apply slew rate limiting to the desired position
    // limitedDesiredPos = slewRateLimiter.calculate(desiredPos);
    // limitedDesiredPos = desiredPos;

    // Update the SmartDashboard with motor data
    SmartDashboard.putNumber("Elevator Driver Position", elevatorEncoder.getPosition());
    SmartDashboard.putNumber(
        "Elevator Driver Temperature Celsius", elevatorMotor.getMotorTemperature());
    SmartDashboard.putNumber(
        "Elevator Follower Temperature Celsius", followerMotor.getMotorTemperature());
    SmartDashboard.putNumber("Elevator Driver Amps", elevatorMotor.getOutputCurrent());
    SmartDashboard.putNumber("Elevator Follower Amps", followerMotor.getOutputCurrent());
    SmartDashboard.putNumber("Elevator Follower Position", followerEncoder.getPosition());
    SmartDashboard.putNumber("Limited Desired Position", limitedDesiredPos);
    SmartDashboard.putNumber("Desired Elevator Position", desiredPos);
  }

  public double getPose() {
    return elevatorEncoder.getPosition();
  }

  public void updatePosition() {
    // Use the slew-rate-limited desired position for upward motion
    double limitedPose = Filter.cutoffFilter(desiredPos, Constants.Elevator.maxHeight, 0);

    // Calculate the direction of movement
    double currentPose = elevatorEncoder.getPosition();
    double direction = Math.signum(limitedPose - currentPose);

    SmartDashboard.putNumber("Desired Elevator Position", limitedPose);

    if (direction > 0) {
      // Moving upward: use PID control with slew rate limiting
      elevatorSparkClosedLoopController.setReference(
          limitedPose, ControlType.kPosition, ClosedLoopSlot.kSlot0, 0);
      followerSparkClosedLoopController.setReference(
          limitedPose, ControlType.kPosition, ClosedLoopSlot.kSlot0, 0);
    } else if (direction < 0) {
      // Moving downward: disable motors and let gravity pull the elevator down
      elevatorMotor.set(0);
      followerMotor.set(0);
    }
  }

  public void goToPose(double position) {
    desiredPos = position;
  }

  public void rawMove(double position) {
    desiredPos += position;
  }

  public void resetPose() {
    elevatorEncoder.setPosition(0);
    followerEncoder.setPosition(0);
  }
}
