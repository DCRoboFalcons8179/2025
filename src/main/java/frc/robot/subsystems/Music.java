// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.Orchestra;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drive.ModuleIOTalonFX;
import java.util.ArrayList;

public class Music extends SubsystemBase {
  private Orchestra orchestra = new Orchestra();
  private int trackIndex = -1;
  private ArrayList<String> tracks = new ArrayList<String>();

  String[] songs = {"Cirice Ghost", "halo_main_theme", "live_and_learn", "Hail to the King"};

  /** Creates a new Music. */
  public Music(ModuleIOTalonFX... moduleIOTalonFXs) {
    for (String song : songs) {
      tracks.add(song);
    }

    // Adds all the Swerve Modules to the Orchestra
    for (ModuleIOTalonFX module : moduleIOTalonFXs) {
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

  public void loadMusic(String song) {
    orchestra.loadMusic(String.format("music/%s.chrp", song));
  }

  public void nextTrack() {
    stop();
    trackIndex = trackIndex == tracks.size() - 1 ? 0 : trackIndex + 1;
    loadMusic(tracks.get(trackIndex));
    play();
  }

  public void backTrack() {
    stop();
    trackIndex = trackIndex == 0 ? tracks.size() - 1 : trackIndex - 1;
    loadMusic(tracks.get(trackIndex));
    play();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
