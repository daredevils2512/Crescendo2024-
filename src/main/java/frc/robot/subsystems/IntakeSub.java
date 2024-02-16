package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSub extends SubsystemBase {
  
  private final WPI_VictorSPX intakeMotor;
  
  public IntakeSub() {
    intakeMotor = new WPI_VictorSPX(Constants.IntakeConstants.INTAKE_MOTOR_ID);
  }

  public void runIntake(double speed) {
   intakeMotor.set(speed);
  }

}
