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
import frc.robot.subsystems.ElevatorSub;

public class Elevator {
    //this holds the speed for the elevator motor
    private DoubleSupplier elevatorSpeed;
    private ElevatorSub elevatorSub;
   
    
    //dont delete this
    public Elevator(DoubleSupplier elevatorSpeed, ElevatorSub elevatorSub) {
        this.elevatorSpeed = elevatorSpeed;
        this.elevatorSub = elevatorSub;
    }


    public void execute(){
        //set the voltage of the chosen motor based on the input from robotcontainer
        elevatorSub.moveMotor(elevatorSpeed.getAsDouble());
    }

}

/* 
what im gonna need

stop elevator when limit switch pressed
*/