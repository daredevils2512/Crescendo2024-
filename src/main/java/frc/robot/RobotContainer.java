package frc.robot;

import frc.robot.Constants.IoConstants;
import frc.robot.commands.AutoCommands;
import frc.robot.commands.ClimberCommands;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.IntakeCommands;
import frc.robot.commands.ShooterCommands;
import frc.robot.io.Extreme;
import frc.robot.subsystems.ClimberSub;
import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.ShooterArmSub;
import frc.robot.subsystems.ShooterSub;
import frc.robot.subsystems.ShooterArmSub.Position;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final DriveSub driveSub = new DriveSub();
  private final IntakeSub intakeSub = new IntakeSub();
  private final ShooterSub shooterSub = new ShooterSub();
  //private final ShooterArmSub shooterArmSub = new ShooterArmSub();
  private final ClimberSub climberSubRight = new ClimberSub(Constants.ClimberConstants.CLIMBER_MOTOR_RIGHT_ID, false);
  private final ClimberSub climberSubLeft = new ClimberSub(Constants.ClimberConstants.CLIMBER_MOTOR_LEFT_ID, true);

  private final Extreme extreme = new Extreme(1);
  private final CommandXboxController xbox = new CommandXboxController(IoConstants.XBOX_CONTROLLER_PORT);

  private enum Auto {
    None, DriveForward, BlueFullAuto, RedFullAuto, SpeakerAuto, StageAuto
  }

  private final SendableChooser<Auto> chooser = new SendableChooser<>();

  public RobotContainer() {
    configureBindings();

    chooser.setDefaultOption("Move Forward", Auto.DriveForward);
    chooser.addOption("None", Auto.None);
    chooser.addOption("Blue Full Auto", Auto.BlueFullAuto);
    chooser.addOption("Red Full Auto", Auto.RedFullAuto);
    chooser.addOption("Speaker Auto", Auto.SpeakerAuto);
    chooser.addOption("Stage Auto", Auto.StageAuto);
    SmartDashboard.putData("Auto", chooser);

  }

  private void configureBindings() {

    // Drivetrain
    DoubleSupplier move = () -> MathUtil.applyDeadband(-xbox.getRawAxis(1), 0.1); //Y axis
    DoubleSupplier turn = () -> MathUtil.applyDeadband(xbox.getRawAxis(0), 0.1); //X axis
    driveSub.setDefaultCommand(driveSub.run(() -> driveSub.arcadeDrive(move.getAsDouble(), turn.getAsDouble())));
    xbox.leftBumper().onTrue(DriveCommands.toggleInverted(driveSub));
    xbox.b().onTrue(DriveCommands.toggleFlip(driveSub));

    // Intake
    xbox.rightBumper().whileTrue(IntakeCommands.runIntakeToShooter(intakeSub, shooterSub, 1.0));
    xbox.rightBumper().onFalse(ShooterCommands.stopShooter(shooterSub));
    xbox.rightTrigger().whileTrue(IntakeCommands.runIntake(intakeSub, -1));
    xbox.leftTrigger().whileTrue(IntakeCommands.runIntake(intakeSub, 0.5));

    // Shooter
    //shooterArmSub.setDefaultCommand(shooterArmSub.run(() -> shooterArmSub.runShooterActuate(extreme.getStickY())));
    // extreme.trigger.whileTrue(ShooterCommands.runShooter(shooterSub, -0.6));
    extreme.sideButton.whileTrue(ShooterCommands.runShooter(shooterSub, -1));
    //extreme.baseFrontLeft.onTrue(ShooterCommands.setPosition(shooterArmSub, Position.Top, 1));
    //extreme.baseMiddleLeft.onTrue(ShooterCommands.setPosition(shooterArmSub, Position.Handoff, 1));
    //extreme.baseBackLeft.onTrue(ShooterCommands.setPosition(shooterArmSub, Position.Bottom, 1));
    //extreme.baseFrontRight.onTrue(shooterArmSub.runOnce(() -> {
    //}));

    //extreme.trigger.whileTrue(ShooterCommands.runShooter(shooterSub, -1.0));
    extreme.trigger.whileTrue(ShooterCommands.runLauncher(shooterSub,1.0));
    extreme.trigger.onFalse(ShooterCommands.runLauncher(shooterSub, 0));

    // Climber
    extreme.joystickTopRight.whileTrue(ClimberCommands.runClimber(climberSubLeft, 1));
    extreme.joystickBottomRight.whileTrue(ClimberCommands.runClimber(climberSubLeft, -1));
    extreme.joystickTopLeft.whileTrue(ClimberCommands.runClimber(climberSubRight, 1));
    extreme.joystickBottomLeft.whileTrue(ClimberCommands.runClimber(climberSubRight, -1));
    extreme.baseMiddleRight.whileTrue(
        ClimberCommands.runClimber(climberSubLeft, 1).alongWith(ClimberCommands.runClimber(climberSubRight, 1)));
    extreme.baseBackRight.whileTrue(
        ClimberCommands.runClimber(climberSubLeft, -1).alongWith(ClimberCommands.runClimber(climberSubRight, -1)));
  }

  public Command autonomousCommand() {
    switch (chooser.getSelected()) {

      case BlueFullAuto:
        return DriveCommands.setInverted(driveSub, false)
            .andThen((AutoCommands.autoDriveAndTurn(driveSub, -0.8, -0.56, 1.2))
            .andThen(AutoCommands.autoDriveForward(driveSub, 0.18, 0.4)))
            //.alongWith(ShooterCommands.setPosition(shooterArmSub, Position.Top, 0.8))
            .andThen((ShooterCommands.runShooter(shooterSub, -1).withTimeout(1)))
            //.andThen(
                //ShooterCommands.setPosition(shooterArmSub, Position.Handoff, 0.8)
//                .alongWith(AutoCommands.autoDriveAndTurn(driveSub, 0.4, -0.6, 1.3)))
            .andThen(IntakeCommands.runIntakeToShooter(intakeSub, shooterSub, 0.8).withTimeout(2));


      case RedFullAuto:
        return DriveCommands.setInverted(driveSub, false)
            .andThen(AutoCommands.autoDriveAndTurn(driveSub, -0.8, 0.56, 1.2)
            .andThen(AutoCommands.autoDriveForward(driveSub, 0.18, 0.4)))
            //.alongWith(ShooterCommands.setPosition(shooterArmSub, Position.Top, 0.8))
            .andThen(ShooterCommands.runShooter(shooterSub, -1).withTimeout(1))
            //.andThen(
            //    ShooterCommands.setPosition(shooterArmSub, Position.Handoff, 0.8)
            //        .alongWith(AutoCommands.autoDriveAndTurn(driveSub, 0.4, 0.6, 1.3)))
            .andThen(IntakeCommands.runIntakeToShooter(intakeSub, shooterSub, 0.8).withTimeout(2));

      case SpeakerAuto:
        return DriveCommands.setInverted(driveSub, true)
            .andThen(AutoCommands.autoDriveForward(driveSub, 0.4, 2));

      case StageAuto:
        return DriveCommands.setInverted(driveSub, true)
            .andThen(AutoCommands.autoDriveForward(driveSub, 0.4, 1.5));

      case DriveForward:
        return DriveCommands.setInverted(driveSub, true)
            .andThen(AutoCommands.autoDriveForward(driveSub, 0.5, 2));

      case None:
      default:
        return new Command() {
        };
    }
  }
}
