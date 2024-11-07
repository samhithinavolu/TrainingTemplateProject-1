package frc.robot.subsytems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    
    private static final PWMSparkMax intakeMotor = new PWMSparkMax(2);
    
   
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Intake Motor Speec",intakeMotor.get());
    }

}
