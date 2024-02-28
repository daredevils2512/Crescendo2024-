package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MotorTest {

  private final WPI_TalonSRX motor;

  public MotorTest() {
    motor = new WPI_TalonSRX(50);
  }

  public void runIntake(double speed) {
    motor.set(speed);
  }
}
