// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSub;
import edu.wpi.first.wpilibj2.command.Command;

public final class AutoCommands {

  public static Command autoDriveForward(DriveSub driveSub, double speed, double time) {
    return driveSub.run(() -> driveSub.arcadeDrive(speed, 0)).withTimeout(time)
        .finallyDo(() -> driveSub.arcadeDrive(0, 0));
  }

  public static Command autoDriveLeft(DriveSub driveSub, double speed, double time) {
    return driveSub.run(() -> driveSub.arcadeDrive(0, speed)).withTimeout(time)
        .finallyDo(() -> driveSub.arcadeDrive(0, 0));
  }

  public static Command autoDriveAndTurn(DriveSub driveSub, double speed, double time) {
    return driveSub.run(() -> driveSub.arcadeDrive(speed, speed)).withTimeout(time)
        .finallyDo(() -> driveSub.arcadeDrive(0, 0));
  }

}
