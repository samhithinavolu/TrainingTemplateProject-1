package frc.robot.subsystems;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;

public class Drivetrain {
    
    private Encoder leftEncoder = new Encoder(0, 1);
    private Encoder rightEncoder = new Encoder(2, 3);

    private EncoderSim leftEncoderSim = new EncoderSim(leftEncoder);
    private EncoderSim rightEncoderSim = new EncoderSim(rightEncoder);

    private AnalogGyro gyro = new AnalogGyro(1);

    private AnalogGyroSim gyroSim = new AnalogGyroSim(gyro);

   private  DifferentialDrivetrainSim m_driveSim = new DifferentialDrivetrainSim(
        DCMotor.getNEO(2),
        7.29,
        7.5,
        60.0,
        Units.inchesToMeters(3),
        0.7112,
        
        VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005));

    private DifferentialDrivetrainSim driveSim = new DifferentialDrivetrainSim.createKitbotSim(
        KitbotMotor.kDualCIMPerSide,
        KitbotGearing.k10p71,
        KitbotWheelSize.kSixInch,
        null
    );
}
