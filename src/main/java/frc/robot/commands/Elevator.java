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
    private DoubleSupplier elevatorSpeed;


    SparkMax elevatorMotor = new SparkMax(Constants.Elevator.motorID, MotorType.kBrushless);
    
    public Elevator(DoubleSupplier elevatorSpeed){
        //just let this chill
        //idk what it does, but it seems important
        this.elevatorSpeed = elevatorSpeed;
    }

    public void execute(){
        //set the voltage of the chosen motor based on the input from robotcontainer
        elevatorMotor.setVoltage(elevatorSpeed.getAsDouble());
    }

}

/* 
what im gonna need

elevator motor (neo)
morot speed
stop elevator when limit switch pressed
controll motor goin gup/down
maybe track current elecvatuoj\n
*/