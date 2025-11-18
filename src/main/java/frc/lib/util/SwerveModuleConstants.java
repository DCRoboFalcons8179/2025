package frc.lib.util;

public class SwerveModuleConstants {
    public final int driveMotorID;
    public final int angleMotorID;
    public final int cancoderID;
    public final double desiredCanCoderPos;


    /**
     * Swerve Module Constants to be used when creating swerve modules.
     * @param driveMotorID
     * @param angleMotorID
     * @param canCoderID
     * @param angleOffset
     * @param desiredCanCoderPos
     */
    public SwerveModuleConstants(int driveMotorID, int angleMotorID, int canCoderID, double desiredCanCoderPos) {
        this.driveMotorID = driveMotorID;
        this.angleMotorID = angleMotorID;
        this.cancoderID = canCoderID;
        this.desiredCanCoderPos = desiredCanCoderPos;
    }
}
