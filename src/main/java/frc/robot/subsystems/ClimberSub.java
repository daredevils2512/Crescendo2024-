package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSub extends SubsystemBase {
  private final WPI_TalonSRX climberMotor;

  public ClimberSub(int motorID, boolean inverted) {
    climberMotor = new WPI_TalonSRX(motorID);
    climberMotor.setInverted(inverted);
  }

  public void runClimber(double speed) {
    climberMotor.set(speed);
  }

}