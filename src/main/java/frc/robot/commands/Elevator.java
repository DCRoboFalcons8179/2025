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
    
    //sets the elevator speed
    public Elevator(DoubleSupplier elevatorSpeed, ElevatorSub elevatorSub) {
        this.elevatorSpeed = elevatorSpeed;
        this.elevatorSub = elevatorSub;
    }


    public void execute(){
        //commanded speed for the elevator
        //if up button is pressed and down button is not pressed, command 0.5 speed up
        double upSpeed = upButtonPressed.getAsBoolean() && !downButtonPressed.getAsBoolean() ? 0.5 : 0;
        elevatorSub.moveMotor(upSpeed);

        //if down button is pressed and up button is not pressed, command -0.5 speed down
        double downSpeed = !upButtonPressed.getAsBoolean() && downButtonPressed.getAsBoolean() ? -0.5 : 0;
        elevatorSub.moveMotor(downSpeed);

        //display the commanded up and down speeds in dashboard
        SmartDashboard.putNumber("up", upSpeed);
        SmartDashboard.putNumber("down", downSpeed);
        //editor's note: isgt if smartdashboard doesnt work im gonna tweak out
    }

}