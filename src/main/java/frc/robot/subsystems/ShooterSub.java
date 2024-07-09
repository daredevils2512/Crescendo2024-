package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkRelativeEncoder;
//import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkLowLevel.MotorType;


import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.filter.SlewRateLimiter;

import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {

  private final WPI_TalonSRX shooterMotor;
  private final CANSparkMax launcherMotor;
  private final CANSparkMax launcherMotor2;
  private final RelativeEncoder launcherEncoder;
  private final SlewRateLimiter rateLimiter;
  public final DigitalInput ring_switch;
  //private final ColorSensorV3 colorSensor;
  
  private final NetworkTable networkTable = NetworkTableInstance.getDefault().getTable(getName());
  private final NetworkTableEntry proximity = networkTable.getEntry("prox");

  public ShooterSub() {
    shooterMotor = new WPI_TalonSRX(Constants.ShooterConstants.SHOOTER_MOTOR_ID);
    shooterMotor.setInverted(true);
    launcherMotor = new CANSparkMax(Constants.ShooterConstants.RIGHT_LAUNCHER_MOTOR_ID,MotorType.kBrushless);
    launcherMotor2 = new CANSparkMax(Constants.ShooterConstants.LEFT_LAUNCHER_MOTOR_ID,MotorType.kBrushless);
    launcherMotor2.follow(launcherMotor,false);

    launcherMotor.restoreFactoryDefaults();
    launcherMotor2.restoreFactoryDefaults();

    rateLimiter = new SlewRateLimiter(0.1);
    launcherEncoder = launcherMotor.getEncoder(SparkRelativeEncoder.Type.kHallSensor,42);
    ring_switch = new DigitalInput(8);
    //colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
  }

  public void runShooter(double speed) {
    shooterMotor.set(speed);
  }

  public void runLauncher(double speed) {
    if(speed != 0.0) {
      double limvel = rateLimiter.calculate(speed);
      System.out.println(limvel);
      launcherMotor.set(limvel);
    } else {
      launcherMotor.set(0.0);
      rateLimiter.reset(0.0);
    }
    //System.out.println(launcherEncoder.getVelocity());
  }

  public boolean detectRingValue() {
    return ring_switch.get();

    //return (colorSensor.getRed() > 400);
  }

  @Override
  public void periodic() {
    //proximity.setDouble(colorSensor.getRed());
  }
}
