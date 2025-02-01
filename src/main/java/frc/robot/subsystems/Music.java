// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.Orchestra;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.SwerveModule;

public class Music extends SubsystemBase {
  private Orchestra orchestra = new Orchestra();
  private int trackIndex = 0;
  private String[] tracks = {"halo_main_theme", "live_and_learn"};
  /** Creates a new Music. */
  public Music(Swerve swerve) {
    // Adds all the Swerve Modules to the Orchestra
    for (SwerveModule module : swerve.mSwerveMods) {
      orchestra.addInstrument(module.getDriveMotorPointer());
      orchestra.addInstrument(module.getAngleMotorPointer());
    }
  }

  public void play() {
    orchestra.play();
  }

  public void stop() {
    orchestra.stop();
  }

  public void load(int index) {
    orchestra.stop();
    orchestra.loadMusic(String.format("music/%s.chrp",tracks[index]));
    orchestra.play();
  }

  public void nextTrack() {
    trackIndex = trackIndex == tracks.length-1 ? 0 : trackIndex + 1; 
    load(trackIndex);
  }

  public void backTrack() {
    trackIndex = trackIndex == 0 ? tracks.length-1 : trackIndex - 1;
    load(trackIndex);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
