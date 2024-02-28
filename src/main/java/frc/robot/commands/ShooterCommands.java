package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterArmSub;
import frc.robot.subsystems.ShooterSub;
import frc.robot.subsystems.ShooterArmSub.Position;

public class ShooterCommands {

  public static Command runShooter(ShooterSub shooterSub, double speed) {
    return shooterSub.run(() -> shooterSub.runShooter(speed)).finallyDo(() -> shooterSub.runShooter(0));
  }

  public static Command runTimedShooter(ShooterSub shooterSub, double speed, double time) {
    return shooterSub.run(() -> shooterSub.runShooter(speed)).withTimeout(time)
      .finallyDo(() -> shooterSub.runShooter(0));
  }

  public static Command runShooterActuate(ShooterArmSub shooterArmSub, double speed) {
    return shooterArmSub.run(() -> shooterArmSub.runShooterActuate(speed)).finallyDo(() -> shooterArmSub.runShooterActuate(0));
  }

  public static Command runTimedShooterActuate(ShooterArmSub shooterArmSub, double speed, double time) {
    return shooterArmSub.run(() -> shooterArmSub.runShooterActuate(speed)).withTimeout(time)
      .finallyDo(() -> shooterArmSub.runShooterActuate(0));
  }

  public static Command setPosition(ShooterArmSub shooterArmSub, Position position, double speed) {
    return shooterArmSub.run(() -> shooterArmSub.setPosition(position, speed));
  }

}
