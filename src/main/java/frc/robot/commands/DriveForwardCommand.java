package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSub;

public class DriveForwardCommand extends Command {
  private final DriveSub driveSub;
  private double initialLeft;
  private double initialRight;
  private double driveTarget;
  // private final double tolerance;

  public DriveForwardCommand(DriveSub driveSub, double driveTarget) {
    this.driveSub = driveSub;
    this.driveTarget = driveTarget;
    // this.tolerance = tolerance;
  }

  @Override
  public void initialize() {
    initialLeft = driveSub.getLeftDistance();
    initialRight = driveSub.getRightDistance();
  }

  @Override
  public void execute() {
    driveSub.arcadeDrive(0.2, 0);
  }

  @Override
  public boolean isFinished() {
    double leftDistance = (driveSub.getLeftDistance()) - (initialLeft);
    // double rightDistance = (driveSub.getRightDistance()) - (initialRight);
    // double averageDistance = (leftDistance) + (rightDistance) / 2;

    if (leftDistance > driveTarget) {
      return true;
    } else
      return false;
  }

  @Override
  public void end(boolean interrupted) {
    driveSub.arcadeDrive(0, 0);
  }
}
