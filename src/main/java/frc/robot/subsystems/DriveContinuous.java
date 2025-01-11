// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.commands.Drive;

/**
 * Swerve drive command
 */
public class DriveContinuous extends Command {

    public DriveContinuous()
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drive);
    }

  // Called just before this Command runs the first time
    protected void initialize()
    {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        // get joystick input
        double angle = Math.arctan2(OI.driver.getRawAxis(1), OI.driver.getRawAxis(0));
        double magnitude = deadzone(Math.hypot(OI.driver.getRawAxis(0), OI.driver.getRawAxis(1)), 0.1);
        double twist = deadzone(OI.driver.getRawAxis(2), 0.1);

        // use field centric controls by subtracting off the robot angle
        angle -= Drive.DRIVE_GYRO.get();

        Drive.SWERVE_DRIVE_COORDINATOR.setSwerveDrive(angle, magnitude, twist);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished()
    {
        return false;
    }

    // Called once after isFinished returns true
    protected void end()
    {
        // sets all drive wheels to 0.0
        Drive.SWERVE_DRIVE_COORDINATOR.setSwerveDrive(0.0, 0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted()
    {
        end();
    }

    private double deadzone(double value, double deadzone)
    {
        if (Math.abs(value) < deadzone)
        {
            return 0;
        }
        return value;
    }

    public void initDefaultCommand()
    {
    setDefaultCommand(new DriveContinuous());
    }

    @Override
    public void periodic() {
    // This method will be called once per scheduler run
    }
}

