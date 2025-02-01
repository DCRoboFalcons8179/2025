// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.Orchestra;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.drive.Drive;

public class Music extends SubsystemBase {
  private Orchestra orchestra = new Orchestra();
  private int trackIndex = 0;
  private String[] tracks = {""};
  /** Creates a new Music. */
  public Music(Drive swerve) {
    // Adds all the Swerve Modules to the Orchestra
    for (frc.robot.subsystems.drive.Module module : swerve.modules) {

      // orchestra.addInstrument(module.getDriveMotorPointer());
      // orchestra.addInstrument(module.getAngleMotorPointer());
    }
  }

  public void play() {
    orchestra.play();
  }

  public void stop() {
    orchestra.stop();
  }

  public void nextTrack() {
    stop();
    trackIndex = trackIndex == tracks.length - 1 ? 0 : trackIndex + 1;
    orchestra.loadMusic(tracks[trackIndex]);
    play();
  }

  public void backTrack() {
    stop();
    trackIndex = trackIndex == 0 ? tracks.length - 1 : trackIndex - 1;
    orchestra.loadMusic(tracks[trackIndex]);
    play();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
