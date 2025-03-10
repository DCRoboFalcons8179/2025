package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
import frc.lib.math.Filter;
import frc.robot.Constants;

public class CoralSub extends SubsystemBase {
  // create motor
  TalonSRX coralMotor = new TalonSRX(Constants.CoralValues.Motor.coralMotorID);
  SparkMax wristMotor = new SparkMax(Constants.CoralValues.Wrist.wristID, MotorType.kBrushless);
  SparkClosedLoopController wristSparkClosedLoopController;
  // create the elevator encoder
  RelativeEncoder wristEncoder = wristMotor.getEncoder();
  ElevatorSub elevatorSub;

  private int minPosition;

  public CoralSub(ElevatorSub elevatorSub) {
    SparkMaxConfig wristConfig = new SparkMaxConfig();

    this.elevatorSub = elevatorSub;

    wristConfig.inverted(false).idleMode(IdleMode.kBrake);

    wristConfig.encoder.positionConversionFactor(1000).velocityConversionFactor(1000);
    wristConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(
            Constants.CoralValues.Wrist.kP,
            Constants.CoralValues.Wrist.kI,
            Constants.CoralValues.Wrist.kD);

    wristMotor.configure(
        wristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    wristEncoder.setPosition(0);

    wristSparkClosedLoopController = wristMotor.getClosedLoopController();
  }

  // Move the coral motor with percent power
  public void moveCoral(double power) {
    coralMotor.set(ControlMode.PercentOutput, power);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Wrist Temperature Celsius", wristMotor.getMotorTemperature());
    SmartDashboard.putNumber("Wrist Position", wristEncoder.getPosition());
  }

  // Move the wrist motor to a specific position
  double desiredPos = 0;

  public void moveWrist(double position) {
    SmartDashboard.putNumber("No Filter Pose", wristEncoder.getPosition() + position);
    desiredPos = Filter.cutoffFilter(desiredPos + position, 1750, 0);

    wristSparkClosedLoopController.setReference(desiredPos, ControlType.kPosition);
  }

  public void updatePosition() {
    double limitedPose =
        Filter.cutoffFilter(
            desiredPos,
            1750,
            elevatorSub.getPosition() >= Constants.Elevator.avoidanceHeight ? 250 : 0);

    SmartDashboard.putNumber("Desired Position", limitedPose);

    wristSparkClosedLoopController.setReference(limitedPose, ControlType.kPosition);
  }

  public void goToPose(double position) {
    desiredPos = position;
  }

  public void avoidCollision() {
    minPosition = 250;
  }
}
