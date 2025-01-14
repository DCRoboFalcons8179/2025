package frc.robot.subsystems;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SubCoral extends SubsystemBase{
    //create motor
    VictorSPX coralMotor = new VictorSPX(Constants.DriveValues.coralMotorId);
    public SubCoral(){
    
    }


//Get percent power
public void moveCoral(double power){
    coralMotor.set(VictorSPXControlMode.PercentOutput, power);
}
}


