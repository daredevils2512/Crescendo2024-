package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {

  private final WPI_VictorSPX shooterMotor;
  private final WPI_VictorSPX shooterActuateMotor;
 // private final DoubleSolenoid pneumatics;
  private final DigitalInput limitSwitchTop;
  private final DigitalInput limitSwitchBottom;
  private final Encoder shooterEncoder;

  public ShooterSub() {
    shooterMotor = new WPI_VictorSPX(Constants.ShooterConstants.SHOOTER_MOTOR_ID);
    shooterActuateMotor = new WPI_VictorSPX(Constants.ShooterConstants.SHOOTER_ACTUATE_MOTOR_ID);

    //pneumatics = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, );
    limitSwitchTop = new DigitalInput(Constants.ShooterConstants.SHOOTER_LIMIT_SWITCH_TOP);
    limitSwitchBottom = new DigitalInput(Constants.ShooterConstants.SHOOTER_LIMIT_SWITCH_BOTTOM);
    shooterEncoder = new Encoder(Constants.ShooterConstants.SHOOTER_ENCODER_A, Constants.ShooterConstants.SHOOTER_ENCODER_B);
  }

  public void runShooter(double speed) {
    shooterMotor.set(speed);
  }

  public void runShooterActuate(double speed) {

    if (limitSwitchTop.get()) {
      speed = Math.min(speed, 0);
    }
    if (limitSwitchBottom.get()) {
      speed = Math.max(speed, 0);
    }

    shooterActuateMotor.set(speed);
  }

  public boolean isAtTop() {
    return limitSwitchTop.get();
  }

  public boolean isAtBottom() {
    return limitSwitchBottom.get();
  }

  public double getDistance() {
    return shooterEncoder.getDistance();
  }

}
