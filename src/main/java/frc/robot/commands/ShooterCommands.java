package frc.robot.commands;

import java.security.cert.PKIXReason;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;

import frc.robot.subsystems.ShooterSub;
import frc.robot.subsystems.ShooterSub.Position;

public class ShooterCommands {

public static Command runShooter(ShooterSub shooterSub, double speed) {
    return shooterSub.run(() -> shooterSub.runShooter(speed)).finallyDo(() -> shooterSub.runShooter(0));
  }

  public static Command runShooterActuate(ShooterSub shooterSub, double speed) {
    return shooterSub.run(() -> shooterSub.runShooterActuate(speed)).finallyDo(() -> shooterSub.runShooterActuate(0));
  }

  public static Command runTimedShooterActuate(ShooterSub shooterSub, double speed, double time) {
    return shooterSub.run(() -> shooterSub.runShooterActuate(speed)).withTimeout(time)
    .finallyDo(() -> shooterSub.runShooterActuate(0));
  }

  public static Command setPosition(ShooterSub shooterSub, Position position, double speed) {
    return shooterSub.run(() -> shooterSub.setPosition(position, speed));
  }

  public static Command setShooterUp(ShooterSub shooterSub, double speed) {
    return shooterSub.run(() -> shooterSub.runShooterActuate(speed));
  }

  public static Command setShooterDown(ShooterSub shooterSub, double speed) {
    return shooterSub.run(() -> shooterSub.runShooterActuate(-speed));
  }

  public static Command runTimedShooter(ShooterSub shooterSub, double speed, double time) {
    return shooterSub.run(() -> shooterSub.runShooter(speed)).withTimeout(time)
        .finallyDo(() -> shooterSub.runShooter(0));
  }
}
