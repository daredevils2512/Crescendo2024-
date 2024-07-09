package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.Pigeon2;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveSub extends SubsystemBase {

  private final NetworkTable networkTable = NetworkTableInstance.getDefault().getTable(getName());
  // private final NetworkTableEntry moveEntry = networkTable.getEntry("move");
  private final DoublePublisher leftDistance = networkTable.getDoubleTopic("left distance").publish();
  private final DoublePublisher rightDistance = networkTable.getDoubleTopic("right distance").publish();
  private final DoublePublisher leftOutputPublisher = networkTable.getDoubleTopic("Left output").publish();
  private final DoublePublisher rightOutputPublisher = networkTable.getDoubleTopic("Right output").publish();
  private final DoublePublisher leftAppliedOutputPublisher = networkTable.getDoubleTopic("Left applied output").publish();
  private final DoublePublisher rightAppliedOutputPublisher = networkTable.getDoubleTopic("Right applied output").publish();

  private final CANSparkMax frontLeft;
  private final CANSparkMax frontRight;
  private final CANSparkMax backLeft;
  private final CANSparkMax backRight;

  private final SlewRateLimiter leftRateLimiter;
  private final SlewRateLimiter rightRateLimiter;

  private boolean inverted = false;
  private boolean flip = false;

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

    leftRateLimiter = new SlewRateLimiter(3);
    rightRateLimiter = new SlewRateLimiter(3);

    backLeft.follow(frontLeft, false);
    backRight.follow(frontRight, false);

  }

  public void arcadeDrive(double move, double turn) {
    if (getInverted()) {
      move = -move;
    }

    if(getFlip()){
      double newMove = turn;
      turn = move;
      move = newMove;
    }

    WheelSpeeds wheelSpeeds = DifferentialDrive.arcadeDriveIK(move, -turn, true); // documentation is backwards
    double leftOut = leftRateLimiter.calculate(wheelSpeeds.left);
    double rightOut = rightRateLimiter.calculate(wheelSpeeds.right);
    frontLeft.set(leftOut);
    frontRight.set(rightOut);

    leftOutputPublisher.set(leftOut);
    rightOutputPublisher.set(rightOut);
  }

  public boolean getInverted() {
    return inverted;
  }

  public void setInverted(boolean value) {
    inverted = value;
  }

  public boolean getFlip(){
    return flip;
  }

  public void setFlip(boolean flipValue){
    flip = flipValue;
  }

  public void stopDrive(){
    leftOutputPublisher.set(0);
    rightOutputPublisher.set(0);

    leftRateLimiter.reset(0);
    rightRateLimiter.reset(0);
    frontLeft.set(0);
    frontRight.set(0);
  }

  public double getLeftDistance() {
    return frontRight.getEncoder().getPosition() * Constants.DrivetrainConstants.DISTANCE_PER_PULSE;
  }

  public double getRightDistance() {
    return frontLeft.getEncoder().getPosition() * Constants.DrivetrainConstants.DISTANCE_PER_PULSE;
  }

  @Override
  public void periodic() {
    leftDistance.set(getLeftDistance());
    rightDistance.set(getRightDistance());

    leftAppliedOutputPublisher.set(frontLeft.getAppliedOutput());
    rightAppliedOutputPublisher.set(frontRight.getAppliedOutput());
  }

}