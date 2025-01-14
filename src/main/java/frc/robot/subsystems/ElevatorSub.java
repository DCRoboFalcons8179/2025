package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ElevatorSub extends SubsystemBase{
    //this is the motor we are using for the motor
    SparkMax elevatorMotor = new SparkMax(Constants.Elevator.motorID, MotorType.kBrushless);

    //move the motor
    //if you didnt know that, put your hand in the gears :3
    public void moveMotor(double elevatorSpeed) {
        elevatorMotor.setVoltage(elevatorSpeed);
    }
}
