// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

/** Add your docs here. */
public class TalonFXConfigurator {
    /**
     * Makes a config for TalonFX Motors for ease of setup
     * @return TalonFXConfiguration - for TalonFX Motor
     */
    public static TalonFXConfiguration getPresetConfigurator(boolean invert) {
        Slot0Configs config = new Slot0Configs();
        config.kP = Constants.DriveValues.kP;
        config.kI = Constants.DriveValues.kI;
        config.kD = Constants.DriveValues.kD;
        config.kV = Constants.DriveValues.kV;
        TalonFXConfiguration talonFXConfiguration = new TalonFXConfiguration();

        InvertedValue motorInvert = invert ? InvertedValue.CounterClockwise_Positive : InvertedValue.Clockwise_Positive;

        talonFXConfiguration.MotorOutput.Inverted = motorInvert;

        talonFXConfiguration.Slot0 = config;

        return talonFXConfiguration;
    }

    /**
     * Returns a pre-configured TalonFX
     * @param id - ID of the TalonFX Motor
     * @return TalonFX
     */
    public static TalonFX getConfiguredTalonFX(int id, boolean inverted) {
        TalonFX talonFX = new TalonFX(id);

        talonFX.getConfigurator().apply(getPresetConfigurator(inverted));

        return talonFX;
    }
}
