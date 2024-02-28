package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSub;

public class ClimberCommands {

  public static Command runClimberLeft(ClimberSub climberSub, double speed) {
    return climberSub.run(() -> climberSub.runClimberLeft(speed)).finallyDo(() -> climberSub.runClimberLeft(0));
  }

  public static Command runClimberRight(ClimberSub climberSub, double speed) {
    return climberSub.run(() -> climberSub.runClimberRight(-speed)).finallyDo(() -> climberSub.runClimberRight(0));
  }

  public static Command runClimbers(ClimberSub climberSub, double leftSpeed, double rightSpeed) {
    return climberSub.run(() -> {
      climberSub.runClimberLeft(leftSpeed);
      climberSub.runClimberRight(rightSpeed);
    });
  }

  // public static Command runTimedClimber(ClimberSub climberSub, double speed,
  // double time) {
  // return climberSub.run(()->
  // climberSub.runClimber(speed)).withTimeout(time).finallyDo(() ->
  // climberSub.runClimber(0));
  // }

}
