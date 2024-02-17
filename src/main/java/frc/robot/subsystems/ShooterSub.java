package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {

  private final WPI_TalonSRX shooterMotor;
  private final WPI_TalonSRX shooterActuateMotor;
 // private final DoubleSolenoid pneumatics;
  private final DigitalInput limitSwitchTop;
  private final DigitalInput limitSwitchBottom;
  private final Encoder shooterEncoder;
  private final NetworkTable networkTable = NetworkTableInstance.getDefault().getTable(getName());
  private final NetworkTableEntry shooterLength = networkTable.getEntry("Shooter length: ");

  public ShooterSub() {
    shooterMotor = new WPI_TalonSRX(Constants.ShooterConstants.SHOOTER_MOTOR_ID);
    shooterActuateMotor = new WPI_TalonSRX(Constants.ShooterConstants.SHOOTER_ACTUATE_MOTOR_ID);
    shooterActuateMotor.setNeutralMode(NeutralMode.Brake);

    //pneumatics = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, );
    limitSwitchTop = new DigitalInput(Constants.ShooterConstants.SHOOTER_LIMIT_SWITCH_TOP);
    limitSwitchBottom = new DigitalInput(Constants.ShooterConstants.SHOOTER_LIMIT_SWITCH_BOTTOM);
    shooterEncoder = new Encoder(Constants.ShooterConstants.SHOOTER_ENCODER_A, Constants.ShooterConstants.SHOOTER_ENCODER_B);
    shooterEncoder.getDirection();
  }

  public void runShooter(double speed) {
    shooterMotor.set(speed);
    
  }

  public void runShooterActuate(double speed) {
    // // make 3 different points shooting(all the way up), hand off(middle), resting(all the way down)
    // if (limitSwitchTop.get()) {
    //   speed = Math.min(speed, 0);
    // }
    // if (limitSwitchBottom.get()) {
    //   speed = Math.max(speed, 0);
    // }
    // // keep limit switches commented till theyre on the robot

    shooterActuateMotor.set(speed);
  }

  // public boolean isAtTop() {
  //   return limitSwitchTop.get();
  // }

  // public boolean isAtBottom() {
  //   return limitSwitchBottom.get();
  // } 
  
  public double getShooterLength() {
    return shooterEncoder.getDistance();
  }
  
  @Override
  public void periodic() {
    shooterLength.setDouble(getShooterLength());
  }

}


//two diff subs for actuate and shoot