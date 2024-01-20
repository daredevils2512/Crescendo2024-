package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSub extends SubsystemBase {

  private final NetworkTable networkTable = NetworkTableInstance.getDefault().getTable(getName());
  // private final NetworkTableEntry leftDistance = networkTable.getEntry("left distance");
  // private final NetworkTableEntry rightDistance = networkTable.getEntry("right distance");
  private final NetworkTableEntry leftSpeed = networkTable.getEntry("left speed");
  private final NetworkTableEntry rightSpeed = networkTable.getEntry("right speed");

  // private final NetworkTableEntry right1Temp = networkTable.getEntry("right 1 temp");
  // private final NetworkTableEntry right2Temp = networkTable.getEntry("right 2 temp");
  // private final NetworkTableEntry left1Temp = networkTable.getEntry("left 1 temp");
  // private final NetworkTableEntry left2Temp = networkTable.getEntry("left 2 temp");

  private final CANSparkMax frontLeft;
  private final CANSparkMax frontRight;
  private final CANSparkMax backLeft;
  private final CANSparkMax backRight;

  private final SlewRateLimiter drivelimit;
  private final Encoder leftEncoder;
  private final Encoder rightEncoder;

  public DriveSub() {
    frontLeft = new CANSparkMax(Constants.DrivetrainConstants.DRIVE_FRONT_RIGHT_ID, MotorType.kBrushless);
    backLeft = new CANSparkMax(Constants.DrivetrainConstants.DRIVE_BACK_RIGHT_ID, MotorType.kBrushless);
    frontRight = new CANSparkMax(Constants.DrivetrainConstants.DRIVE_FRONT_LEFT_ID, MotorType.kBrushless);
    backRight = new CANSparkMax(Constants.DrivetrainConstants.DRIVE_BACK_LEFT_ID, MotorType.kBrushless);

    drivelimit = new SlewRateLimiter(0);

    leftEncoder = new Encoder(Constants.DrivetrainConstants.LEFT_ENCODER_A,
        Constants.DrivetrainConstants.LEFT_ENCODER_B);
    rightEncoder = new Encoder(Constants.DrivetrainConstants.RIGHT_ENCODER_A,
        Constants.DrivetrainConstants.RIGHT_ENCODER_B);

    leftEncoder.setDistancePerPulse(Constants.DrivetrainConstants.DISTANCE_PER_PULSE);
    rightEncoder.setDistancePerPulse(Constants.DrivetrainConstants.DISTANCE_PER_PULSE);

    backLeft.follow(frontLeft);
    backRight.follow(frontRight);

  }

  public void arcadeDrive(double move, double turn) {
    WheelSpeeds wheelSpeeds = DifferentialDrive.arcadeDriveIK(move, -turn, true);
    frontLeft.set(wheelSpeeds.left);
    frontRight.set(wheelSpeeds.right);
    leftSpeed.setDouble(wheelSpeeds.left);
    rightSpeed.setDouble(wheelSpeeds.right);
  }

  public double getLeftDistance() {
    return leftEncoder.getDistance();
  }

  public double getRightDistance() {
    return rightEncoder.getDistance();
  }

  public double getLeftSpeed() {
    return leftEncoder.getRate();
  }

  public double getRightSpeed() {
    return rightEncoder.getRate();
  }

  public double getDistance() {
    return (getLeftDistance() + getRightDistance()) / 2;
  }

  @Override
  public void periodic() {
    // leftDistance.setDouble(getLeftDistance());
    // rightDistance.setDouble(getRightDistance());
    leftSpeed.setDouble(getLeftSpeed());
    rightSpeed.setDouble(getRightSpeed());

    // right1Temp.setDouble(frontRight.getTemperature());
    // right2Temp.setDouble(backRight.getTemperature());
    // left1Temp.setDouble(frontLeft.getTemperature());
    // left2Temp.setDouble(backLeft.getTemperature());
  }

}