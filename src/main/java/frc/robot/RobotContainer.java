package frc.robot;

import frc.robot.Constants.IoConstants;
import frc.robot.commands.AutoCommands;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.IntakeCommands;
import frc.robot.commands.ShooterCommands;
import frc.robot.io.Extreme;
import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.PIDTest;
import frc.robot.subsystems.ShooterSub;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final DriveSub driveSub = new DriveSub();
  private final IntakeSub intakeSub = new IntakeSub();
  private final ShooterSub shooterSub = new ShooterSub();
  private final Extreme extreme = new Extreme(1); // constants??
  private final CommandXboxController xbox = new CommandXboxController(IoConstants.XBOX_CONTROLLER_PORT);

  private final PIDTest pid = new PIDTest();

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    
    // Drivetrain
    driveSub.setDefaultCommand(driveSub.run(() -> driveSub.arcadeDrive(-xbox.getLeftY(), xbox.getLeftX())));
    xbox.leftBumper().onTrue(DriveCommands.toggleInverted(driveSub));
    extreme.baseBackLeft.onTrue(new DriveForwardCommand(driveSub, 10)); //pid things start here
    xbox.a().whileTrue(pid.run(() -> pid.setTargetPosition(10)));
    pid.setDefaultCommand(pid.run(() -> pid.runPIDMotor(MathUtil.applyDeadband(xbox.getLeftY(), 0.3))));

    // Intake
    xbox.rightBumper().whileTrue(IntakeCommands.runIntake(intakeSub, 1));

    // Shooter
    shooterSub.setDefaultCommand(shooterSub.run(() -> shooterSub.runShooterActuate(extreme.getStickY())));
    extreme.trigger.whileTrue(ShooterCommands.runShooter(shooterSub, 1));
    extreme.sideButton.onTrue(ShooterCommands.runTimedShooter(shooterSub, 0.3, 2));

    xbox.a().whileTrue(pid.run(()-> pid.setTargetPosition(10)));
    pid.setDefaultCommand(pid.run(()-> pid.runPIDMotor(MathUtil.applyDeadband(xbox.getLeftY(), 0.3))));
  }

  public Command autonomousCommand() {
    return AutoCommands.autoDriveAndTurn(driveSub, 0.5, 1) //theoretically be at amp
    //return AutoCommands.AutoDriveForward(driveSub, 0.5, 1)
    //.andThen(AutoCommands.AutoDriveLeft(driveSub, 0.5, 1)) 
      .andThen(ShooterCommands.runShooterActuate(shooterSub, 0.5))
      .andThen(ShooterCommands.runShooter(shooterSub, 1)).withTimeout(1) //shoot in amp
      .andThen(AutoCommands.autoDriveAndTurn(driveSub, -0.5, 1))
      .andThen(ShooterCommands.runShooterActuate(shooterSub, -0.5))
      .andThen(IntakeCommands.runIntake(intakeSub, 1).alongWith(ShooterCommands.runShooter(shooterSub, 0.3))).withTimeout(1); // intake another ring .. could make the intake and shooter one function
  }
}

// beware the watermelon man
// how bad can the watermelon man possibly be?