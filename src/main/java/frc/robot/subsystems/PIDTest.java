package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PIDTest extends SubsystemBase {
  private final NetworkTable table = NetworkTableInstance.getDefault().getTable(getName());
  private final DoublePublisher encoderPublisher = table.getDoubleTopic("Encoder").publish();

  private final CANSparkMax motor;
  private final PIDController pid;

  public PIDTest() {
    pid = new PIDController(0.1, 0.05, 0);
    motor = new CANSparkMax(34, MotorType.kBrushless);
    motor.restoreFactoryDefaults();
  }

  public void runPIDMotor(double speed){
    motor.set(speed);
  }

  public void setTargetPosition(double setPoint){
    double output = pid.calculate(motor.getEncoder().getPosition(), setPoint);
    output = MathUtil.clamp(output, -0.3, 0.3);
    motor.set(output);
  }
  
  public double getDistance() {
    return motor.getEncoder().getPosition();
  }

  @Override
  public void periodic() {
    encoderPublisher.set(motor.getEncoder().getPosition());
  }
  // public void weebWoob() {
  //   pid.setTolerance(5, 5);
  //   pid.setIntegratorRange(-0.5, 0.5);
  //   pid.atSetpoint();
    // pid.reset();
  // }
}
