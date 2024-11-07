package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.simulation.AnalogGyroSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;


public class Drivetrain {

    PWMSparkMax leftMotor = new PWMSparkMax(0);
    PWMSparkMax rightMotor = new PWMSparkMax(2);

    private Encoder leftEncoder = new Encoder(0, 1);
    private Encoder rightEncoder = new Encoder(2, 3);

    private EncoderSim leftEncoderSim = new EncoderSim(leftEncoder);
    private EncoderSim rightEncoderSim = new EncoderSim(rightEncoder);

    private AnalogGyro gyro = new AnalogGyro(1);
    
    private AnalogGyroSim gyroSim = new AnalogGyroSim(gyro);

    private DifferentialDrive DiffDrive = new DifferentialDrive(leftMotor, rightMotor);

    private  DifferentialDrivetrainSim m_driveSim = new DifferentialDrivetrainSim(
        DCMotor.getNEO(2),
        7.29,
        7.5,
        60.0,
        Units.inchesToMeters(3),
        0.7112,  
        VecBuilder.fill(0.001, 0.001, 0.001, 0.1, 0.1, 0.005, 0.005)
    );

    private DifferentialDrivetrainSim driveSim = DifferentialDrivetrainSim.createKitbotSim(
        KitbotMotor.kDualCIMPerSide,
        KitbotGearing.k10p71,
        KitbotWheelSize.kSixInch,
        null
    );

    private PWMSparkMax m_leftMotor = new PWMSparkMax(0);
    private PWMSparkMax m_rightMotor = new PWMSparkMax(1);
    
    public Drivetrain() {
    int kWheelRadius = 3;
    double kEncoderResolution = Double.MAX_VALUE;;
    leftEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);
    rightEncoder.setDistancePerPulse(2 * Math.PI * kWheelRadius / kEncoderResolution);
    }

    public void simulationPeriodic() {

        m_driveSim.setInputs(m_leftMotor.get() * RobotController.getInputVoltage(),
                       m_rightMotor.get() * RobotController.getInputVoltage());

        m_driveSim.update(0.02);

        leftEncoderSim.setDistance(m_driveSim.getLeftPositionMeters());
        leftEncoderSim.setRate(m_driveSim.getLeftVelocityMetersPerSecond());
        rightEncoderSim.setDistance(m_driveSim.getRightPositionMeters());
        rightEncoderSim.setRate(m_driveSim.getRightVelocityMetersPerSecond());
        gyroSim.setAngle(-m_driveSim.getHeading().getDegrees());
    }

    private void arcadeDrive(DoubleSupplier linearSpeed, DoubleSupplier angularSpeed) {

        
    }
}
