package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConstants;

public class ShooterArmSub extends SubsystemBase {

  private final WPI_TalonSRX shooterActuateMotor;
  private final DigitalInput limitSwitchTop;
  private final DigitalInput limitSwitchBottom;
  private final Encoder actuateEncoder;
  private final SlewRateLimiter shooterLimit;
  private double speed;

  private final NetworkTable networkTable = NetworkTableInstance.getDefault().getTable(getName());
  private final NetworkTableEntry shooterAngle = networkTable.getEntry("Shooter Angle: ");
  private final NetworkTableEntry shooterBottom = networkTable.getEntry("Shooter at bottom");
  private final NetworkTableEntry shooterTop = networkTable.getEntry("shooter at top");

  public ShooterArmSub() {

    shooterActuateMotor = new WPI_TalonSRX(Constants.ShooterConstants.SHOOTER_ACTUATE_MOTOR_ID);
    shooterActuateMotor.setInverted(true);
    shooterActuateMotor.setNeutralMode(NeutralMode.Brake);

    limitSwitchBottom = new DigitalInput(Constants.ShooterConstants.LIMIT_SWITCH_BOTTOM);
    limitSwitchTop = new DigitalInput(Constants.ShooterConstants.LIMIT_SWITCH_TOP);
    actuateEncoder = new Encoder(Constants.ShooterConstants.SHOOTER_ENCODER_A,Constants.ShooterConstants.SHOOTER_ENCODER_B);
    actuateEncoder.setDistancePerPulse(Constants.ShooterConstants.DEGREES_PER_PULSE + Constants.ShooterConstants.START_ANGLE);
    actuateEncoder.setReverseDirection(false);
    shooterLimit = new SlewRateLimiter(3);
  }

  public void runShooterActuate(double speed) {
    //if (limitSwitchBottom.get()) { DONT FORGET TO TURN ON AGAIN
    //  speed = Math.max(speed, 0);
    //}
    //if (limitSwitchTop.get()){
    //  speed = Math.min(speed, 0);
    //}
   this.speed = speed;
    // shooterActuateMotor.set(speed);
  }

  public enum Position {
    Top, Handoff, Bottom
  };

  public void setPosition(Position position, double speed) {
    switch (position) {
      case Top:
        runShooterActuate(speed);
        break;

      case Handoff:
        if (getDistance() > Constants.ShooterConstants.ENCODER_POSITION + Constants.ShooterConstants.TOLERANCE) {
          runShooterActuate(-speed);
        } else if (getDistance() < Constants.ShooterConstants.ENCODER_POSITION - Constants.ShooterConstants.TOLERANCE) {
          runShooterActuate(speed);
        } else {
          runShooterActuate(0);
        }
        break;

      case Bottom:
        runShooterActuate(-speed);
        break;
    }
  }

  public boolean atPosition(Position position){
    switch (position) {
      case Bottom:
      return (limitSwitchBottom.get());
    
      case Handoff:
      return atAngle(Constants.ShooterConstants.ENCODER_POSITION);

      case Top:
      return (limitSwitchTop.get());

      default:
      return false;
    }
  }

  public boolean atAngle(double angle){
    if (getDistance() > angle + Constants.ShooterConstants.TOLERANCE){
      return false;
    } else if (getDistance() < angle + Constants.ShooterConstants.TOLERANCE){
      return false;
    } else {
      return true;
    }
  }

  public boolean isAtBottom() {
    return limitSwitchBottom.get();
  }

  public boolean isAtTop(){
    return limitSwitchTop.get();
  }

  public double getDistance() {
    return actuateEncoder.getDistance();
  }

  @Override
  public void periodic() {
    if (isAtBottom()) {
      actuateEncoder.reset();
    }

    double limit = shooterLimit.calculate(speed);
    shooterActuateMotor.set(limit);

    shooterAngle.setDouble(getDistance());
    shooterBottom.setBoolean(limitSwitchBottom.get());
    shooterTop.setBoolean(limitSwitchTop.get());
  }
}
