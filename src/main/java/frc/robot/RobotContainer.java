package frc.robot;

import frc.robot.Constants.IoConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.IntakeCommands;
import frc.robot.commands.ShooterCommands;
import frc.robot.io.Extreme;
import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.ShooterSub;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

public class RobotContainer {
  private final DriveSub driveSub = new DriveSub();
  private final IntakeSub intakeSub = new IntakeSub();
  private final ShooterSub shooterSub = new ShooterSub();
  private final Extreme extreme = new Extreme(1); // constants??
  private final CommandXboxController xbox = new CommandXboxController(IoConstants.XBOX_CONTROLLER_PORT);
  
  public RobotContainer() {
    configureBindings();

  }

  private void configureBindings() {
    driveSub.setDefaultCommand(driveSub.run(() -> driveSub.arcadeDrive(-xbox.getLeftY(), xbox.getLeftX()))); 
    intakeSub.setDefaultCommand(intakeSub.run(() -> intakeSub.runIntakeActuate(-xbox.getRightY())));
    shooterSub.setDefaultCommand(shooterSub.run(()-> shooterSub.runShooterActuate(extreme.getStickY()))); 

    xbox.leftBumper().onTrue(DriveCommands.toggleInverted(driveSub));

    xbox.rightBumper().whileTrue(IntakeCommands.runIntake(intakeSub, 1));
    xbox.y().onTrue(IntakeCommands.setActuateUp(intakeSub, 1));
    xbox.x().onTrue(IntakeCommands.setActuateDown(intakeSub, -1));
    xbox.a().onTrue(IntakeCommands.toggleActuate(intakeSub, 1));

    extreme.trigger.whileTrue(ShooterCommands.runShooter(shooterSub, 1));
    extreme.sideButton.onTrue(ShooterCommands.runTimedShooter(shooterSub, 1, 2));

  }

  // public Command getAutonomousCommand() {
  // return Autos.exampleAuto(m_exampleSubsystem);
  // }
}






// beware the watermelon man
// how bad can the watermelon man possibly be?