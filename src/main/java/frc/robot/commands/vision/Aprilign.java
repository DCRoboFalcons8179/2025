package frc.robot.commands.vision;

import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.IdealStartingState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.vision.Vision;
import java.util.List;

public class Aprilign extends Command {
  private final Drive drive;
  private final Vision visionSub;
  private final double offsetX;
  private final double offsetY;
  private Command followPathCommand;
  private PathPlannerPath path;

  private static Pose2d targetRobotPoseBeforeAprilTag;
  private static List<Waypoint> waypoints;
  private static PathConstraints constraints;

  public PathPlannerPath getPath() {
    return path;
  }

  public Command getFollowPathCommand() {
    return followPathCommand;
  }

  public Aprilign(Drive drive, Vision visionSub, double offsetX, double offsetY) {
    this.drive = drive;
    this.visionSub = visionSub;
    this.offsetX = offsetX;
    this.offsetY = offsetY;

    addRequirements(drive, visionSub);
  }

  @Override
  public void initialize() {
    Pose2d currentPose = drive.getPose();
    if (visionSub.getTargetID() != -1 && !Double.isNaN(visionSub.getCameraYaw())) {

      Transform3d transform3d = visionSub.getTransform3To3dTarget();

      double poseX = currentPose.getX();
      double poseY = currentPose.getY();

      double angle = transform3d.getRotation().getAngle();

      double theta = drive.getRotation().getRadians();
      double phi = visionSub.getCameraYaw();
      double ct = Math.cos(theta);
      double st = Math.sin(theta);
      double cp = Math.cos(phi);
      double sp = Math.sin(phi);

      double distX = visionSub.getDistanceX();
      double distY = visionSub.getDistanceY();

      double deltaX =
          -distX * st
              + distY * ct
              + (offsetX * cp + offsetY * sp) * ct
              + (offsetX * cp - offsetY * sp) * st;
      double deltaY =
          +distX * ct
              + distY * st
              + (offsetX * cp + offsetY * sp) * st
              + (offsetX * sp - offsetY * cp) * ct;

      double targetX = poseX + deltaX;
      double targetY = poseY + deltaY;

      targetRobotPoseBeforeAprilTag = new Pose2d(targetX, targetY, new Rotation2d(theta + phi));

      waypoints =
          PathPlannerPath.waypointsFromPoses(drive.getPose(), targetRobotPoseBeforeAprilTag);

      constraints = new PathConstraints(3.0, 2.0, 0.5, 0.5, 12); // The constraints for this path.
      // PathConstraints constraints = PathConstraints.unlimitedConstraints(12.0); // You can also
      // use
      // unlimited constraints, only limited by motor torque and nominal battery voltage

      // Create the path using the waypoints created above

      path =
          new PathPlannerPath(
              waypoints,
              constraints, // null
              new IdealStartingState(
                  drive.getAverageSpeed(),
                  null), // The ideal starting state, this is only relevant for pre-planned paths,
              // so
              // can
              // be null for on-the-fly paths.
              new GoalEndState(
                  0.0,
                  targetRobotPoseBeforeAprilTag
                      .getRotation()) // Goal end state. You can set a holonomic rotation here. If
              // using a differential drivetrain, the rotation will have no
              // effect.
              );
    } else {
      waypoints = PathPlannerPath.waypointsFromPoses(drive.getPose(), drive.getPose());

      constraints = new PathConstraints(0.1, 0.1, 0.5, 0.5, 12);

      path = new PathPlannerPath(waypoints, constraints, null, null);
    }

    // Prevent the path from being flipped if the coordinates are already correct
    path.preventFlipping = true;
  }

  public static void setTargetRobotPose(Pose2d pose) {
    targetRobotPoseBeforeAprilTag = pose;
  }

  public static Pose2d getTargetRobotPoseBeforeAprilTag() {
    return targetRobotPoseBeforeAprilTag;
  }

  @Override
  public void execute() {
    // Continuously update the path-following
    if (followPathCommand != null) {
      followPathCommand.execute();
    }
  }

  @Override
  public boolean isFinished() {
    return followPathCommand != null && followPathCommand.isFinished();
  }

  @Override
  public void end(boolean interrupted) {
    // Optionally stop or clean up any path-following behavior if the command is interrupted
    if (followPathCommand != null) {
      followPathCommand.end(interrupted);
    }
  }
}
