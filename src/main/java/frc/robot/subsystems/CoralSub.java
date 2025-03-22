package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
  // Motors
  /** Motor for manipulating the c0oral */
  TalonSRX coralMotor = new TalonSRX(Constants.CoralConstants.Intake.coralMotorID);

  /** Motor for manipulating the wrist */
  SparkMax wristMotor = new SparkMax(Constants.CoralConstants.Wrist.wristID, MotorType.kBrushless);
  /** Closed loop controller for the wrist motor */
  SparkClosedLoopController wristSparkClosedLoopController;
  /** Encoder for the wrist motor */
  RelativeEncoder wristEncoder = wristMotor.getEncoder();

  private final ElevatorSub elevatorSub;

  public CoralSub(ElevatorSub elevatorSub) {
    SparkMaxConfig wristConfig = new SparkMaxConfig();

    wristConfig.inverted(false).idleMode(IdleMode.kBrake);

    wristConfig.encoder.positionConversionFactor(1000).velocityConversionFactor(1000);
    wristConfig
        .closedLoop
        .feedbackSensor(FeedbackSensor.kPrimaryEncoder)
        .pid(
            Constants.CoralConstants.Wrist.kP,
            Constants.CoralConstants.Wrist.kI,
            Constants.CoralConstants.Wrist.kD);

    wristMotor.configure(
        wristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    wristEncoder.setPosition(0);

    wristSparkClosedLoopController = wristMotor.getClosedLoopController();

    // Set the coral motor to brake mode
    coralMotor.setNeutralMode(NeutralMode.Brake);

    this.elevatorSub = elevatorSub;
  }

  /**
   *
   *
   * <h3>Move the coral motor
   *
   * <p>Uses PercentOutput to move the coral motor
   *
   * @param power The power to move the coral motor
   */
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
    desiredPos = position;
  }

  /**
   *
   *
   * <h3>Update the position of the wrist motor
   *
   * <p>Called every cycle
   *
   * <p>Uses a cutoff filter to limit the position of the wrist motor
   *
   * <p>Also updates the SmartDashboard with the desired position
   */
  public void updatePosition() {
    double limitedPose =
        Filter.cutoffFilter(
            desiredPos,
            Constants.CoralConstants.Wrist.maxPose,
            elevatorSub.getPose() > Constants.ElevatorConstants.avoidanceHeight
                ? 1000
                : Constants.CoralConstants.Wrist.minPose);

    SmartDashboard.putNumber("Wrist Desired Position", limitedPose);

    wristSparkClosedLoopController.setReference(limitedPose, ControlType.kPosition);
  }

  /**
   *
   *
   * <h3>Move the wrist motor to a specific position
   *
   * @param position The position to move the wrist motor to go to
   */
  public void goToPose(double position) {
    desiredPos = position;
  }

  /**
   * Move the wrist motor by a certain amount
   *
   * @param increment The amount to move the wrist motor by
   */
  public void rawTilt(double increment) {
    desiredPos += increment;
  }

  /** Moves the wrist to break the velcro */
  public void freeWrist() {
    wristMotor.set(0.2);
  }
}
