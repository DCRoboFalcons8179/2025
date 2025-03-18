package frc.robot.subsystems;

import java.util.List;

import com.pathplanner.lib.path.GoalEndState;
import com.pathplanner.lib.path.PathConstraints;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.path.Waypoint;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import frc.robot.subsystems.drive.Drive;

public class Aprilign {

    private static Pose2d targetRobotPoseBeforeAprilTag;
    private static List<Waypoint> waypoints;
    private static PathConstraints constraints;
    private static PathPlannerPath path;

    public static void setTargetPoseBeforeAprilTag(Drive drive, NewVision newVision, double offsetX, double offsetY) {
        Transform3d transform3d = newVision.getFrontTransform3To3dTarget();

        double poseX = drive.getPose().getX();
        double poseY = drive.getPose().getY();

        double angle = transform3d.getRotation().getAngle();

        double targetX = offsetX * Math.cos(angle) - offsetY * Math.sin(angle);
        double targetY = offsetX * Math.sin(angle) + offsetY * Math.cos(angle);

        targetRobotPoseBeforeAprilTag = new Pose2d(poseX + transform3d.getX() + rotatedOffsetX, poseY + transform3d.getY() + rotatedOffsetY, transform3d.getRotation().toRotation2d());

        waypoints = PathPlannerPath.waypointsFromPoses(
            drive.getPose(),
            targetRobotPoseBeforeAprilTag
        );

        constraints = new PathConstraints(3.0, 3.0, 2 * Math.PI, 4 * Math.PI); // The constraints for this path.
        // PathConstraints constraints = PathConstraints.unlimitedConstraints(12.0); // You can also use unlimited constraints, only limited by motor torque and nominal battery voltage

        // Create the path using the waypoints created above
        path = new PathPlannerPath(
            waypoints,
            constraints,
            null, // The ideal starting state, this is only relevant for pre-planned paths, so can be null for on-the-fly paths.
            new GoalEndState(0.0, targetRobotPoseBeforeAprilTag.getRotation()) // Goal end state. You can set a holonomic rotation here. If using a differential drivetrain, the rotation will have no effect.
        );

        // Prevent the path from being flipped if the coordinates are already correct
        path.preventFlipping = true;
    }

    public static void setTargetRobotPose(Pose2d pose) {
        targetRobotPoseBeforeAprilTag = pose;
    }

    public static Pose2d getTargetRobotPoseBeforeAprilTag() {
        return targetRobotPoseBeforeAprilTag;
    }

}
