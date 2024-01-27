package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants;

public class ShooterSub {

  private final WPI_VictorSPX shooterMotor;
  private final WPI_VictorSPX shooterActuateMotor;

public ShooterSub(){
    shooterMotor = new WPI_VictorSPX(Constants.IntakeConstants.INTAKE_MOTOR_ID);
    shooterActuateMotor = new WPI_VictorSPX(Constants.IntakeConstants.ACTUATE_MOTOR_ID);
} 
}
