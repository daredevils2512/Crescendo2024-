// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSub;
import edu.wpi.first.wpilibj2.command.Command;

public final class AutoCommands {

  public static Command autoDriveForward(DriveSub driveSub, double speed, double time) {
    return driveSub.run(() -> driveSub.arcadeDrive(speed, 0))
      .finallyDo(() -> driveSub.stopDrive())
        .withTimeout(time);
  }

  public static Command autoDriveTurn(DriveSub driveSub, double speed, double time) {
    return driveSub.run(() -> driveSub.arcadeDrive(0, speed))
      .finallyDo(() -> driveSub.stopDrive()).withTimeout(time);
  }

  public static Command autoDriveAndTurn(DriveSub driveSub, double speedMove, double speedTurn, double time) {
    return driveSub.run(() -> driveSub.arcadeDrive(speedMove, speedTurn))
      .finallyDo(() -> driveSub.stopDrive())
        .withTimeout(time);
  }

}
