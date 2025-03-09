// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SoftLimitConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.math.Filter;
import frc.robot.Constants;
import frc.robot.Constants.Elevator;

public class ElevatorSub extends SubsystemBase {
    /**Elevator Motors */
    private SparkMax m_LiftA = new SparkMax(Elevator.driverID, MotorType.kBrushless);
    private SparkMax m_LiftB = new SparkMax(Elevator.followerMotorID, MotorType.kBrushless);
    
    /**Elevator Sensors */
    private RelativeEncoder LiftEncoderA = m_LiftA.getEncoder();
    private RelativeEncoder liftEncoderB = m_LiftB.getEncoder();

    /**Elevator PID Controllers */
    private SparkClosedLoopController lifControllerA = m_LiftA.getClosedLoopController();
    private SparkClosedLoopController lifControllerB = m_LiftB.getClosedLoopController();
    
    /**Elevator Configurations */
    private SparkMaxConfig liftConfigA = new SparkMaxConfig();
    private SparkMaxConfig liftConfigB = new SparkMaxConfig();
    
    /** Soft Limit Config */
    private SoftLimitConfig softLimitConfig = new SoftLimitConfig();

    /** Internal Variables */
    private double setPoint = 0;

    /** Creates a new Elevator. */
    public ElevatorSub() {
        /**Factory Reset */
        m_LiftA.configure(liftConfigA, ResetMode.kResetSafeParameters,null);
        m_LiftB.configure(liftConfigB, ResetMode.kResetSafeParameters, null);

        /**Inversion Factors */
        liftConfigA.inverted(false);
        liftConfigB.inverted(true);

        /**Current Limits */
        liftConfigA.smartCurrentLimit(Elevator.currentLimit);
        liftConfigB.smartCurrentLimit(Elevator.currentLimit);

        /** Set Position */
        liftEncoderB.setPosition(0);
        LiftEncoderA.setPosition(0);

        /**Software Limits */
        // softLimitConfig.apply(liftConfigA.softLimit);
        // softLimitConfig.apply(liftConfigB.softLimit);
        // liftConfigA.softLimit.reverseSoftLimitEnabled(false);
        // liftConfigB.softLimit.reverseSoftLimitEnabled(false);
        // liftConfigA.softLimit.forwardSoftLimitEnabled(true);
        // liftConfigB.softLimit.forwardSoftLimitEnabled(true);
        // liftConfigA.softLimit.reverseSoftLimit(0);
        // liftConfigB.softLimit.reverseSoftLimit(0);
        // liftConfigA.softLimit.forwardSoftLimit(Elevator.fwdSoftLimitNum);
        // liftConfigB.softLimit.forwardSoftLimit(Elevator.fwdSoftLimitNum);

        /** Neutral Modes */
        liftConfigA.idleMode(Elevator.defaultIdleMode);
        liftConfigB.idleMode(Elevator.defaultIdleMode);

        /* PIDS */
        liftConfigA.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(Elevator.kP, Elevator.kI, Elevator.kD);
        liftConfigB.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(Elevator.kP, Elevator.kI, Elevator.kD);

        /* Burning Configs */
        m_LiftA.configure(liftConfigA, null, PersistMode.kPersistParameters);
        m_LiftB.configure(liftConfigB, null, PersistMode.kPersistParameters);
    }
        
    /**
     * returns the value of the motor encoder in rotations
     * @return double, motor rotations
     */
    private double getPosition(){
        return LiftEncoderA.getPosition();
    }

    /**
     * Sets the Elevator position set point
     * @param targetSetPoint, double, units of motor rotations
     */
    public void setPosition(double targetSetPoint){
        setPoint = Filter.cutoffFilter(targetSetPoint, Constants.Elevator.maxHeight, 0);
        lifControllerA.setReference(setPoint, ControlType.kPosition);
        lifControllerB.setReference(setPoint, ControlType.kPosition);
    }

    /**
     * Sets output to lift motors.
     * @param speed double, percentage output
     */
    public void setElevatorSpeed(double speed){
        m_LiftA.set(speed);
        m_LiftB.set(speed);
    }

    /**
     * Updates the position of the elevator
     */
    public void updatePosition(){
        setPosition(Filter.cutoffFilter(setPoint, Constants.Elevator.maxHeight, 0));
    }

    /**
     * Tells us if it between the upper and lower position 
     * @return true if between upper and lower limit, else
     */
    public boolean atPosition(){
        double lowerLimit = setPoint - Elevator.encoderSetPointError;
        double UpperLimit = setPoint + Elevator.encoderSetPointError;
        // System.out.println("Encoder value:" + getPosition() + ((getPosition() >= lowerLimit) && (getPosition()<= UpperLimit)));

        if ((getPosition() >= lowerLimit) && (getPosition()<= UpperLimit)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        SmartDashboard.putNumber("Elevator encoder position", getPosition());
        SmartDashboard.putNumber("Elevator set point", setPoint);
        SmartDashboard.putBoolean("Elevator At Pose", atPosition());
    }
}