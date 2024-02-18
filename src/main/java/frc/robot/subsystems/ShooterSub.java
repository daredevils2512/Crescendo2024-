package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {

  private final NetworkTable networkTable = NetworkTableInstance.getDefault().getTable(getName());
  private final NetworkTableEntry actauteEntry = networkTable.getEntry("actuate encoder: ");
  private final WPI_VictorSPX shooterMotor;
  private final WPI_VictorSPX shooterActuateMotor;
  // private final DoubleSolenoid pneumatics;
  private final DigitalInput limitSwitchTop;
  private final DigitalInput limitSwitchBottom;
  private final Encoder actuateEncoder;
  // private final PIDController pid;

  public ShooterSub() {

    shooterMotor = new WPI_VictorSPX(Constants.ShooterConstants.SHOOTER_MOTOR_ID);
    shooterActuateMotor = new WPI_VictorSPX(Constants.ShooterConstants.SHOOTER_ACTUATE_MOTOR_ID);

    limitSwitchTop = new DigitalInput(Constants.ShooterConstants.SHOOTER_LIMIT_SWITCH_TOP);
    limitSwitchBottom = new DigitalInput(Constants.ShooterConstants.SHOOTER_LIMIT_SWITCH_BOTTOM);
    actuateEncoder = new Encoder(Constants.ShooterConstants.SHOOTER_ENCODER_A, Constants.ShooterConstants.SHOOTER_ENCODER_B);
    
    // pid = new PIDController(0.1, 0.0, 0);
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

  public enum Position {
    Top, Handoff, Bottom
  };

  public void setPosition(Position position, double speed) {
    switch (position) {
      case Top:
        runShooterActuate(speed);
        break;

      case Handoff: 
      // pid.calculate(speed);
      if (getDistance() > Constants.ShooterConstants.ENCODER_POSITION + Constants.ShooterConstants.TOLERANCE){      //change encoder position
          runShooterActuate(-speed);
      } else if (getDistance() < Constants.ShooterConstants.ENCODER_POSITION - Constants.ShooterConstants.TOLERANCE){
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

  public boolean isAtTop() {
    return limitSwitchTop.get();
  }

  public boolean isAtBottom() {
    return limitSwitchBottom.get();
  }

  public double getDistance() {
    return actuateEncoder.getDistance();
  }

  @Override
  public void periodic() {
    if (isAtBottom()) {
      actuateEncoder.reset();
    }

    actauteEntry.setDouble(getDistance());
  }
}