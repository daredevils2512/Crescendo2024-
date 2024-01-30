package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.IntakeSub;

public class IntakeCommands {

  public static Command runIntake(IntakeSub intakeSub, double speed) {
    return intakeSub.run(() -> intakeSub.runIntake(speed)).finallyDo(() -> intakeSub.runIntake(0));
  }
  
  public static Command runIntakeActuate(IntakeSub intakeSub, double speed) {
    return intakeSub.run(() -> intakeSub.runIntakeActuate(speed)).finallyDo(() -> intakeSub.runIntakeActuate(0));
  }

  public static Command setActuateUp(IntakeSub intakeSub, double speed) {
    return intakeSub.run(() -> intakeSub.runIntakeActuate(speed));
  }

  public static Command setActuateDown(IntakeSub intakeSub, double speed) {
    return intakeSub.run(() -> intakeSub.runIntakeActuate(-speed));
  }

  public static Command toggleActuate(IntakeSub intakeSub, double speed) {
    return new ConditionalCommand(setActuateDown(intakeSub, speed), setActuateUp(intakeSub, speed), () -> intakeSub.isAtTop());
  } 

}
