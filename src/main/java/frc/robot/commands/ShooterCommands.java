package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.ShooterArmSub;
import frc.robot.subsystems.ShooterSub;
import frc.robot.subsystems.ShooterArmSub.Position;

public class ShooterCommands {

  public static Command runShooter(ShooterSub shooterSub, double speed) {
    return shooterSub.run(() -> shooterSub.runShooter(speed)).finallyDo(() -> shooterSub.runShooter(0));
  }

  public static Command stopShooter(ShooterSub shooterSub) {
    return shooterSub.run(() -> shooterSub.runShooter(0));
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
    return runShooterActuate(shooterArmSub, speed).until(() -> shooterArmSub.atPosition(position));
  }

  public static Command dectectRing(ShooterSub shooterSub, double speed){
    return shooterSub.run(() -> shooterSub.runShooter(speed)).finallyDo(()-> shooterSub.runShooter(0)).until(()-> shooterSub.detectRingValue());
  }

  public static Command runLauncher(ShooterSub shooterSub, double speed){
    return shooterSub.run(() -> shooterSub.runLauncher(speed)).finallyDo(()-> shooterSub.runShooter(0));
  }
}
