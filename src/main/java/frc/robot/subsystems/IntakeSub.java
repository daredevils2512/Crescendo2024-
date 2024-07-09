package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSub extends SubsystemBase {

  private final WPI_TalonSRX intakeMotor;
  private final SlewRateLimiter intakeLimit;
  private double speed;

  public IntakeSub() {
    intakeMotor = new WPI_TalonSRX(Constants.IntakeConstants.INTAKE_MOTOR_ID);
    intakeLimit = new SlewRateLimiter(3);
  }

  public void runIntake(double speed) {
    this.speed = speed;
  }

  @Override
  public void periodic(){
    double limit = intakeLimit.calculate(speed);
    intakeMotor.set(limit);
  }
}