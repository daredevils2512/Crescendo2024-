// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSub;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;

public class DriveCommands extends Command {
  public static Command arcadeDrive(DriveSub driveSub, DoubleSupplier move, DoubleSupplier turn) {
    return driveSub.run(() -> driveSub.arcadeDrive(move.getAsDouble(), turn.getAsDouble()))
      .finallyDo(() -> driveSub.arcadeDrive(0, 0));
  }

  public static Command toggleInverted(DriveSub driveSub) {
    return driveSub.runOnce(() -> driveSub.setInverted(!driveSub.getInverted()));
  }

  public static Command setInverted(DriveSub driveSub, boolean inverted) {
    return driveSub.runOnce(() -> driveSub.setInverted(inverted));
  }

  public static Command setFlip(DriveSub driveSub, boolean flip){
    return driveSub.runOnce(() -> driveSub.setFlip(flip));
  }

  public static Command toggleFlip(DriveSub driveSub){
    return driveSub.runOnce(() -> driveSub.setFlip(!driveSub.getFlip()));
  }
}
