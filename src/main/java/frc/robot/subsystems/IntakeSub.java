package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSub extends SubsystemBase {

  enum Position {
    Up, Down
  }

  // net work table of toggles and all

  private final WPI_VictorSPX intakeMotor;
  private final WPI_VictorSPX intakeActuateMotor;
  private final Encoder actuateEncoder;
  private final SlewRateLimiter moveLimit;
  private final DigitalInput limitSwitchTop;
  private final DigitalInput limitSwitchBottom;

  public IntakeSub() {
    intakeMotor = new WPI_VictorSPX(Constants.IntakeConstants.INTAKE_MOTOR_ID);
    intakeActuateMotor = new WPI_VictorSPX(Constants.IntakeConstants.ACTUATE_MOTOR_ID);
    actuateEncoder = new Encoder(Constants.IntakeConstants.ACTUATE_ENCODER_A, Constants.IntakeConstants.ACTUATE_ENCODER_B);
    moveLimit = new SlewRateLimiter(0);
    limitSwitchTop = new DigitalInput(Constants.IntakeConstants.INTAKE_LIMIT_SWITCH_TOP);
    limitSwitchBottom = new DigitalInput(Constants.IntakeConstants.INTAKE_LIMIT_SWITCH_BOTTOM);
    actuateEncoder.setDistancePerPulse(1);

  }

  public void runIntake(double speed) {
    intakeMotor.set(speed);
  }

  public void runActuate(double speed) {

    if (limitSwitchTop.get()) {
      speed = Math.min(speed, 0);
    }

    if (limitSwitchBottom.get()) {
      speed = Math.max(speed, 0);
    }

    intakeActuateMotor.set(speed);
  }

  public boolean isAtTop(){
    return limitSwitchTop.get();
  }
  
  public boolean isAtBottom(){
    return limitSwitchBottom.get();
  }

  public double getDistance() {
    return actuateEncoder.getDistance();
  }
}
