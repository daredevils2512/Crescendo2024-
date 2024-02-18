package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSub extends SubsystemBase {
  private final WPI_VictorSPX climberMotor;
  private final DigitalInput limitSwitchTop;
  private final DigitalInput limitSwitchBottom;

  public ClimberSub() {
    climberMotor = new WPI_VictorSPX(Constants.ClimberConstants.CLIMBER_MOTOR_ID);
    limitSwitchTop = new DigitalInput(Constants.ClimberConstants.CLIMBER_LIMIT_SWITCH_TOP);
    limitSwitchBottom = new DigitalInput(Constants.ClimberConstants.CLIMBER_LIMIT_SWITCH_BOTTOM);
  }

  public void runClimber(double speed) {

    if (limitSwitchTop.get()) {
      speed = Math.min(speed, 0);
    }
    if (limitSwitchBottom.get()) {
      speed = Math.max(speed, 0);
    }

    climberMotor.set(speed);
  }

  public boolean isAtTop() {
    return limitSwitchTop.get();
  }

  public boolean isAtBottom() {
    return limitSwitchBottom.get();
  }
}