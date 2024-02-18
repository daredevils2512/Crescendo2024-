package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.ShooterSub;

public class IntakeCommands {

  public static Command runIntake(IntakeSub intakeSub, double speed) {
    return intakeSub.run(() -> intakeSub.runIntake(speed)).finallyDo(() -> intakeSub.runIntake(0));
  }

  public static Command runIntakeToShooter(IntakeSub intakeSub, ShooterSub shooterSub, double speed){
    return intakeSub.run(()-> intakeSub.runIntake(speed)).alongWith(ShooterCommands.runShooter(shooterSub, -speed))
    .finallyDo(() -> intakeSub.runIntake(0));
  }
}
