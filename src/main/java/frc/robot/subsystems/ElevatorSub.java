package frc.robot.subsystems;

import com.revrobotics.*;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSub extends SubsystemBase {
  // this is the motor we are using for the motor
  private SparkMax elevatorMotor = new SparkMax(Constants.Elevator.motorID, MotorType.kBrushless);
  // create the elevator encoder
  RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();

  // create the configs for the motors
  private SparkMaxConfig test = new SparkMaxConfig();
  private ClosedLoopConfig testclosed = new ClosedLoopConfig();
  // sparkbase for the motor
  private SparkBase elevatorSparkBase = new SparkBase(0, null, null) {};

  // create the follower motor
  private SparkMax followerMotor =
      new SparkMax(Constants.Elevator.followerMotorID, MotorType.kBrushless);
  // create the follower encoder
  RelativeEncoder followerEncoder = followerMotor.getEncoder();

  @Override
  public void periodic() {
    SmartDashboard.putNumber(getName(), elevatorSparkBase.get());
  }

  // pid stuff
  public void autonomousInit() {
    // zero encoder position
    elevatorEncoder.getPosition();
    // reset pid
    errorSum = 0;
    lastTimeStamp = Timer.getFPGATimestamp();
    lastError = 0;
  }

  // pid variables
  final double kP = 0;
  final double kI = 0;
  final double iLimit = 1;
  final double kD = 0;

  // current elevator setpoint (where the elevator is going)

  // more pid variables
  double errorSum = 0;
  double lastTimeStamp = 0;
  double lastError = 0;

  // does a bunch of math and uses pid loop to move the motor for the elevator
  public void moveMotor(double setpoint) {
    // set sensor position equal to the encoder position
    double sensorPOS = elevatorEncoder.getPosition();
    // calculations for the pid loop
    double error = setpoint - sensorPOS;
    double dt = Timer.getFPGATimestamp() - lastTimeStamp;
    if (Math.abs(error) < iLimit) {
      errorSum += error * dt;
    }
    double errorRate = (error - lastError) / dt;
    // final speed calculation
    double outputSpeed = kP * error + kI * errorSum + kD * errorRate;
    // move the elevator motor
    elevatorEncoder.setPosition(elevatorEncoder.getPosition() + outputSpeed);
    followerEncoder.setPosition(elevatorEncoder.getPosition() + outputSpeed);
  }

  // config for the elevator encoder
  public ElevatorSub() {
    // set the new config for the motor
    var testConfig = new SparkMaxConfig();
    // apply conifguration to the elevator motor
    elevatorMotor.configure(testConfig, null, null);
    test.apply(testclosed);
  }
  // use this function later for if/when we do setpoints
  public void setPosition(double position) {}
}
