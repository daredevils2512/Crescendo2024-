package frc.robot;

import frc.robot.Constants.IoConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.IntakeCommands;
import frc.robot.io.Extreme;
import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.DriveSub;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  private final DriveSub driveSub = new DriveSub();
  private final IntakeSub intakeSub = new IntakeSub();
  private final Extreme extreme = new Extreme(1); // constants??
  private final CommandXboxController xbox = new CommandXboxController(IoConstants.XBOX_CONTROLLER_PORT);
  
  public RobotContainer() {
    configureBindings();

  }

  private void configureBindings() {
    // driveSub.setDefaultCommand(driveSub.run(() -> driveSub.arcadeDrive(-xbox.getLeftY(),xbox.getRightX()))); //using both sticks
    driveSub.setDefaultCommand(driveSub.run(() -> driveSub.arcadeDrive(-xbox.getLeftY(), xbox.getLeftX()))); //both on left
    intakeSub.setDefaultCommand(intakeSub.run(() -> intakeSub.runActuate(-xbox.getRightY())));

    xbox.rightBumper().whileTrue(IntakeCommands.runIntake(intakeSub));
    xbox.y().onTrue(IntakeCommands.setActuateUp(intakeSub));
    xbox.x().onTrue(IntakeCommands.setActuateDown(intakeSub));
    xbox.a().onTrue(IntakeCommands.toggleActuate(intakeSub));
  }

  // public Command getAutonomousCommand() {
  // return Autos.exampleAuto(m_exampleSubsystem);
  // }
}






// beware the watermelon man
// how bad can the watermelon man possibly be?