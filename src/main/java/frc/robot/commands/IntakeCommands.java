package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSub;

public class IntakeCommands {

  public static Command runIntake(IntakeSub intakeSub, double speed) {
    return intakeSub.run(() -> intakeSub.runIntake(speed)).finallyDo(() -> intakeSub.runIntake(0));
  }

}
