package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import java.io.ObjectInputFilter.Config;
import java.nio.file.DirectoryStream.Filter;
import java.security.KeyPair;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import com.revrobotics.RelativeEncoder;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.revrobotics.*;
import com.revrobotics.spark.SparkBase;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import frc.robot.Constants;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSub extends SubsystemBase{
    //this is the motor we are using for the motor
    private SparkMax elevatorMotor = new SparkMax(Constants.Elevator.motorID, MotorType.kBrushless);
    RelativeEncoder elevatorEncoder = elevatorMotor.getEncoder();
    //private SparkMaxConfig elevatorConfig = new SparkMaxConfig();

    //private PIDController pidController;

    //pidController = elevatorMotor.get;


    //pid stuff
   

    

    public void autonomousInit(){
        elevatorEncoder.getPosition();
        errorSum = 0;
        lastTimeStamp = Timer.getFPGATimestamp();
        lastError = 0;
    }

    final double kP = 0;
    final double kI = 0;
    final double iLimit = 1;
    final double kD = 0;

    double setpoint = 0;
    double errorSum = 0;
    double lastTimeStamp = 0;
    double lastError = 0;

    public void moveMotor(){
        //get sensor position
        double sensorPOS = elevatorEncoder.getPosition();
        //calculations
        double error = setpoint - sensorPOS;
        double dt = Timer.getFPGATimestamp() - lastTimeStamp;
    
        
        if(Math.abs(error) < iLimit){
            errorSum += error * dt;
        }
        double errorRate = (error - lastError) / dt;

        double outputSpeed = kP * error + kI * errorSum + kD * errorRate;
        
        elevatorEncoder.setPosition(elevatorEncoder.getPosition() + outputSpeed);
    }

    public ElevatorSub() {
        var testConfig = new SparkMaxConfig();
        elevatorMotor.configure(testConfig, null, null);
    }    

    
    
    public void setPosition(double position){
        //elevatorEncoder.setPosition();
    }

    
    
}
