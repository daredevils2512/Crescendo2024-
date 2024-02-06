package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class MotorTest {
    
  private final WPI_VictorSPX motor;
  
  public MotorTest() {
    motor = new WPI_VictorSPX(9);
  }

  public void runIntake(double speed) {
    motor.set(speed);
  } 
}
