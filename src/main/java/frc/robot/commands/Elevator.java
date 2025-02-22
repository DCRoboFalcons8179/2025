package frc.robot.commands;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.revrobotics.*;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Constants;
import frc.robot.subsystems.ElevatorSub;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Elevator {
    //idk but it may be needed at one point
    private DoubleSupplier elevatorSpeed;
    //loads the elevator subsytem into this file
    private ElevatorSub elevatorSub;
   
    //these buttons are forthe control box
    //hopefully it works, but debugging may be necesary
    BooleanSupplier upButtonPressed;
    BooleanSupplier downButtonPressed;
    //sets the values for the buttons
    public void ElevatorControl(BooleanSupplier upPressed, BooleanSupplier downPressed){
        this.upButtonPressed = upPressed;
        this.downButtonPressed = downPressed;

    }
    
    //dont delete this
    public Elevator(DoubleSupplier elevatorSpeed, ElevatorSub elevatorSub) {
        this.elevatorSpeed = elevatorSpeed;
        this.elevatorSub = elevatorSub;
    }


    public void execute(){
        //command for the elevator
        double upSpeed = upButtonPressed.getAsBoolean() && !downButtonPressed.getAsBoolean() ? 0.5 : 0;
        elevatorSub.moveMotor(upSpeed);
        double downSpeed = !upButtonPressed.getAsBoolean() && !downButtonPressed.getAsBoolean() ? -0.5 : 0;
        elevatorSub.moveMotor(downSpeed);

        //display values in dashboard
        //isgt if smartdashboard doesnt work im gonna tweak out
        SmartDashboard.putNumber("up", upSpeed);
        SmartDashboard.putNumber("down", downSpeed);
    }

}
/* 
what im gonna need

stop elevator when limit switch pressed
*/