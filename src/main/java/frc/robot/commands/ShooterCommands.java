package frc.robot.commands;


import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSub;

public class ShooterCommands {

  public static Command runShooter(ShooterSub shooterSub, double speed) {
    return shooterSub.run(() -> shooterSub.runShooter(speed)).finallyDo(() -> shooterSub.runShooter(0));
  }

  public static Command runTimedShooter(ShooterSub shooterSub, double speed, double time) {
    return shooterSub.run(()-> shooterSub.runShooter(speed)).withTimeout(time).finallyDo(() -> shooterSub.runShooter(0));
  }

  public static Command runShooterActuate(ShooterSub shooterSub, double speed) {
    return shooterSub.run(() -> shooterSub.runShooterActuate(speed)).finallyDo(() -> shooterSub.runShooterActuate(0));
  }
  
  // public static Command setShooterUp(ShooterSub shooterSub, double speed) {
  //   return shooterSub.run(() -> shooterSub.runShooterActuate(speed));
  // }

  // public static Command setShooterDown(ShooterSub shooterSub, double speed) {
  //   return shooterSub.run(() -> shooterSub.runShooterActuate(-speed));
  // }
}
