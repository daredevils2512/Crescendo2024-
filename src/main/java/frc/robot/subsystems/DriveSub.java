package frc.robot.subsystems;

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
  private final NetworkTableEntry leftSpeed = networkTable.getEntry("left speed");
  private final NetworkTableEntry rightSpeed = networkTable.getEntry("right speed");
  // private final NetworkTableEntry moveEntry = networkTable.getEntry("move");
  private final NetworkTableEntry leftDistance = networkTable.getEntry("left distance");
  private final NetworkTableEntry rightDistance = networkTable.getEntry("right distance");


  private final CANSparkMax frontLeft;
  private final CANSparkMax frontRight;
  private final CANSparkMax backLeft;
  private final CANSparkMax backRight;

  private final SlewRateLimiter drivelimit;
  private final Encoder leftEncoder;
  private final Encoder rightEncoder;

  private boolean inverted = false;

  public DriveSub() {
    frontLeft = new CANSparkMax(Constants.DrivetrainConstants.DRIVE_FRONT_LEFT_ID, MotorType.kBrushless);
    backLeft = new CANSparkMax(Constants.DrivetrainConstants.DRIVE_BACK_LEFT_ID, MotorType.kBrushless);
    frontRight = new CANSparkMax(Constants.DrivetrainConstants.DRIVE_FRONT_RIGHT_ID, MotorType.kBrushless);
    backRight = new CANSparkMax(Constants.DrivetrainConstants.DRIVE_BACK_RIGHT_ID, MotorType.kBrushless);

    frontLeft.restoreFactoryDefaults();
    backLeft.restoreFactoryDefaults();
    frontRight.restoreFactoryDefaults();
    backRight.restoreFactoryDefaults();

    frontRight.setInverted(true);

    drivelimit = new SlewRateLimiter(5);

    leftEncoder = new Encoder(Constants.DrivetrainConstants.LEFT_ENCODER_A,
        Constants.DrivetrainConstants.LEFT_ENCODER_B);
    rightEncoder = new Encoder(Constants.DrivetrainConstants.RIGHT_ENCODER_A,
        Constants.DrivetrainConstants.RIGHT_ENCODER_B);

    leftEncoder.setDistancePerPulse(Constants.DrivetrainConstants.DISTANCE_PER_PULSE);
    rightEncoder.setDistancePerPulse(Constants.DrivetrainConstants.DISTANCE_PER_PULSE);

    backLeft.follow(frontLeft, false);
    backRight.follow(frontRight, false);
  }

  public void arcadeDrive(double move, double turn) {
    if (getInverted()) {
      move = -move;
    }

    WheelSpeeds wheelSpeeds = DifferentialDrive.arcadeDriveIK(move, -turn, true);// documentation is backwards
    frontLeft.set(wheelSpeeds.left);
    frontRight.set(wheelSpeeds.right);
    leftSpeed.setDouble(wheelSpeeds.left);
    rightSpeed.setDouble(wheelSpeeds.right);

    // // moveEntry.setDouble(move);
  }

  public boolean getInverted() {
    return inverted;
  }

  public void setInverted(boolean value) {
    inverted = value;
  }
  

  public double getLeftDistance() {
    return frontRight.getEncoder().getPosition();
  }

  public double getRightDistance() {
    return frontLeft.getEncoder().getPosition();
  }

  // public double getLeftSpeed() {
  //   return leftEncoder.getRate();
  // }

  // public double getRightSpeed() {
  //   return rightEncoder.getRate();
  // }

  @Override
  public void periodic() {
    leftDistance.setDouble(getLeftDistance());
    rightDistance.setDouble(getRightDistance());
    // leftSpeed.setDouble(getLeftSpeed());
    // rightSpeed.setDouble(getRightSpeed());

  }

}