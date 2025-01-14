package frc.robot.commands;

import java.util.Set;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.revrobotics.*;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Constants;

public class Elevator {
    //this holds the speed for the elevator motor
    private DoubleSupplier elevatorSpeed;

    //this is the motor we are using for the motor
    SparkMax elevatorMotor = new SparkMax(Constants.Elevator.motorID, MotorType.kBrushless);
    

    public void execute(){
        //set the voltage of the chosen motor based on the input from robotcontainer
        elevatorMotor.setVoltage(elevatorSpeed.getAsDouble());
    }

}

/* 
what im gonna need

stop elevator when limit switch pressed
*/