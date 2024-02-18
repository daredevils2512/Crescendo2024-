package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {

  private final WPI_TalonSRX shooterMotor;
  private final WPI_TalonSRX shooterActuateMotor;
  private final DigitalInput limitSwitchTop;
  private final DigitalInput limitSwitchBottom;
  private final Encoder actuateEncoder;
  private final NetworkTable networkTable = NetworkTableInstance.getDefault().getTable(getName());
  private final NetworkTableEntry shooterLength = networkTable.getEntry("Shooter length: ");

  public ShooterSub() {
    shooterMotor = new WPI_TalonSRX(Constants.ShooterConstants.SHOOTER_MOTOR_ID);
    shooterActuateMotor = new WPI_TalonSRX(Constants.ShooterConstants.SHOOTER_ACTUATE_MOTOR_ID);
    shooterActuateMotor.setNeutralMode(NeutralMode.Brake);

    limitSwitchTop = new DigitalInput(Constants.ShooterConstants.SHOOTER_LIMIT_SWITCH_TOP);
    limitSwitchBottom = new DigitalInput(Constants.ShooterConstants.SHOOTER_LIMIT_SWITCH_BOTTOM);
    actuateEncoder = new Encoder(Constants.ShooterConstants.SHOOTER_ENCODER_A, Constants.ShooterConstants.SHOOTER_ENCODER_B);
    
    // pid = new PIDController(0.1, 0.0, 0);
  }

  public void runShooter(double speed) {
    shooterMotor.set(speed);
  }

  public void runShooterActuate(double speed) {
    // if (limitSwitchTop.get()) {
    //   speed = Math.min(speed, 0);
    // }
    // if (limitSwitchBottom.get()) {
    //   speed = Math.max(speed, 0);
    // }

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

  // public boolean isAtTop() {
  //   return limitSwitchTop.get();
  // }

  // public boolean isAtBottom() {
  //   return limitSwitchBottom.get();
  // }

  public double getDistance() {
    return actuateEncoder.getDistance();
  }

  @Override
  public void periodic() {
  //   if (isAtBottom()) {
  //     actuateEncoder.reset();
  //   }
    shooterLength.setDouble(getDistance());

  }
}
