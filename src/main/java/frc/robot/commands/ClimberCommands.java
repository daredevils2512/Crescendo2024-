package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSub;

public class ClimberCommands {
    

  public static Command runClimber(ClimberSub climberSub, double speed) {
    return climberSub.run(() -> climberSub.runClimber(speed)).finallyDo(() -> climberSub.runClimber(0));
  }
}


  
//   public static Command setShooterUp(ShooterSub shooterSub, double speed) {
//     return shooterSub.run(() -> shooterSub.runShooterActuate(speed));
//   }

//   public static Command setShooterDown(ShooterSub shooterSub, double speed) {
//     return shooterSub.run(() -> shooterSub.runShooterActuate(-speed));
//   }
