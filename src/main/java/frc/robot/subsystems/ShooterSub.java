package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {

  private final WPI_TalonSRX shooterMotor;

  public ShooterSub() {
    shooterMotor = new WPI_TalonSRX(Constants.ShooterConstants.SHOOTER_MOTOR_ID);
  }

  public void runShooter(double speed) {
    shooterMotor.set(speed);
  }

}
