package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.subsystems.IntakeSub;

public class IntakeCommands {
  
    public static Command runIntake(IntakeSub intakeSub){
        return intakeSub.run(() -> intakeSub.runIntake(1)).finallyDo(() -> intakeSub.runIntake(0));
    }

    public static Command runActuate(IntakeSub intakeSub){
        return intakeSub.run(() -> intakeSub.runActuate(1)).finallyDo(() -> intakeSub.runActuate(0));

    }

    
  }

