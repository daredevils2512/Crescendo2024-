package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSub extends SubsystemBase {

  enum Position {
    Up, Down
  }

  private final WPI_VictorSPX intakeMotor;
  private final WPI_VictorSPX actuateMotor;
  private final SlewRateLimiter driveLimit;
  private final Encoder intakeEncoder;
  private final Encoder actuateEncoder;

  public IntakeSub() {
    intakeMotor = new WPI_VictorSPX(Constants.IntakeConstants.INTAKE_MOTOR_ID);
    actuateMotor = new WPI_VictorSPX(Constants.IntakeConstants.ACTUATE_MOTOR_ID);
    driveLimit = new SlewRateLimiter(0);
    intakeEncoder = new Encoder(Constants.IntakeConstants.INTAKE_ENCODER_A, Constants.IntakeConstants.INTAKE_ENCODER_B);
    actuateEncoder = new Encoder(Constants.IntakeConstants.ACTUATE_ENCODER_A, Constants.IntakeConstants.ACTUATE_ENCODER_B);
  }

  public void runIntake(double speed){
    intakeMotor.set(speed);
  }

  public void runActuate(double speed){
    actuateMotor.set(speed);
  }
  
  public void setActuateSpeed() {

  }
  public void setPosition(Position intakeUp) {

  }
}
