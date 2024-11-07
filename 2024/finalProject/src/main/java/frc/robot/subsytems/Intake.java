package frc.robot.subsytems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    
    private static final PWMSparkMax intakeMotor = new PWMSparkMax(2);
    Solenoid solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);
       
    @Override
    public void periodic(){
        SmartDashboard.putNumber("Intake Motor Speec",intakeMotor.get());
    }

    public InstantCommand intakeSpeed(double speed) {
        return new InstantCommand(()->
        {
            intakeMotor.set(speed);
        }
        );
    }

    public InstantCommand stopIntake() {
        return new InstantCommand(()->
        {
            intakeMotor.set(-0.1);
        }
        );
    }

}
