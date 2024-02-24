package frc.robot;

import frc.robot.Constants.IoConstants;
import frc.robot.commands.AutoCommands;
import frc.robot.commands.ClimberCommands;
import frc.robot.commands.DriveCommands;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.IntakeCommands;
import frc.robot.commands.ShooterCommands;
import frc.robot.io.Extreme;
import frc.robot.subsystems.ClimberSub;
import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.IntakeSub;
import frc.robot.subsystems.PIDTest;
import frc.robot.subsystems.ShooterSub;
import frc.robot.subsystems.ShooterSub.Position;

import java.util.function.DoubleSupplier;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.MjpegServer;
import edu.wpi.first.cscore.UsbCamera;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
  private final DriveSub driveSub = new DriveSub();
  private final IntakeSub intakeSub = new IntakeSub();
  private final ShooterSub shooterSub = new ShooterSub();
  private final ClimberSub climberSub = new ClimberSub();
  private final Extreme extreme = new Extreme(1); // constants??
  private final CommandXboxController xbox = new CommandXboxController(IoConstants.XBOX_CONTROLLER_PORT);
  private enum Auto {
    None, DriveForward, FullAuto, SpeakerAuto, StageAuto
  }
  private final SendableChooser<Auto> chooser = new SendableChooser<>();
  private final PIDTest pid = new PIDTest();

  public RobotContainer() {
    configureBindings();

    chooser.setDefaultOption("Move Forward", Auto.DriveForward);
    chooser.addOption("None", Auto.None);
    chooser.addOption("Full Auto", Auto.FullAuto);
    chooser.addOption("Speaker Auto", Auto.SpeakerAuto);
    chooser.addOption("Stage Auto", Auto.StageAuto);
    SmartDashboard.putData("Auto", chooser);

    UsbCamera intakeCamera = CameraServer.startAutomaticCapture(1);
    intakeCamera.setResolution(640, 480);
    UsbCamera shooterCamera = CameraServer.startAutomaticCapture(2);
    shooterCamera.setResolution(640, 480);

    // MjpegServer server = new MjpegServer(null, 14);
    // server.setSource(intakeCamera);
    CameraServer.getServer();

    // if (leftBumper.false) {
    // intakeCamera.getVideoMode;
    // }else (leftBumper.true) {
    // shooterCamera.getVideoMode();
    // }

    // if (driveSub.arcadeDrive(move, -turn)){
    // set.intakeCamera;
    // } else (driveSub.arcadeDrive(-move, turn)){
    // get.shooterCamera;
    // }

  }

  private void configureBindings() {

    // Drivetrain
    DoubleSupplier move = () -> MathUtil.applyDeadband(-xbox.getLeftY(), 0.1);
    DoubleSupplier turn = () -> MathUtil.applyDeadband(xbox.getLeftX(), 0.1);
    driveSub.setDefaultCommand(driveSub.run(() -> driveSub.arcadeDrive(move.getAsDouble(), turn.getAsDouble())));
    xbox.leftBumper().onTrue(DriveCommands.toggleInverted(driveSub));

    // xbox.a().whileTrue(pid.run(()-> pid.setTargetPosition(10)));
    // pid.setDefaultCommand(pid.run(()-> pid.runPIDMotor(MathUtil.applyDeadband(xbox.getLeftY(), 0.3))));
    // extreme.baseBackRight.onTrue(new DriveForwardCommand(driveSub, 10));

    // Intake
    xbox.rightBumper().whileTrue(IntakeCommands.runIntakeToShooter(intakeSub, shooterSub, 0.5));
    xbox.rightTrigger().whileTrue(IntakeCommands.runIntake(intakeSub, 0.5));

    // Shooter
    shooterSub.setDefaultCommand(shooterSub.run(() -> shooterSub.runShooterActuate(extreme.getStickY())));
    extreme.trigger.whileTrue(ShooterCommands.runShooterOut(shooterSub, 1)); // shoot
    extreme.sideButton.whileTrue(ShooterCommands.runShooterIn(shooterSub, -1)); // intake to shooter
    // extreme.baseFrontLeft.onTrue(ShooterCommands.setPosition(shooterSub, Position.Top, 0.6));
    // extreme.baseMiddleLeft.onTrue(ShooterCommands.setPosition(shooterSub, Position.Handoff, 0.6));
    // extreme.baseBackLeft.onTrue(ShooterCommands.setPosition(shooterSub, Position.Bottom, 0.6));

    // Climber
    extreme.joystickTopRight.whileTrue(ClimberCommands.runClimberUp(climberSub, 0.5));
    extreme.joystickTopLeft.whileTrue(ClimberCommands.runClimberUp(climberSub, -0.5));
  }

  public Command autonomousCommand() {
  switch (chooser.getSelected()) {
      case FullAuto:
          return AutoCommands.autoDriveAndTurn(driveSub, 0.5, 2) // theoretically be at amp
            // return AutoCommands.AutoDriveForward(driveSub, 0.5, 1) // use this if ^ doesnt work
            // .andThen(AutoCommands.AutoDriveLeft(driveSub, 0.5, 1))
            .andThen(ShooterCommands.runTimedShooterActuate(shooterSub, 0.5, 2)) // change time till it gets to the top
            .andThen(ShooterCommands.runShooter(shooterSub, 1)).withTimeout(1) // shoot in amp
            .andThen(ShooterCommands.runTimedShooterActuate(shooterSub, -0.5, 2)) // maybe put after moving, might slam into the amp
            .andThen(AutoCommands.autoDriveAndTurn(driveSub, -0.5, 1))
            .andThen(IntakeCommands.runIntakeToShooter(intakeSub, shooterSub, 1).withTimeout(1.3)); // intake another ring

      case SpeakerAuto:
        return  AutoCommands.autoDriveForward(driveSub, 0.3, 1);
      

      case StageAuto: 
        return AutoCommands.autoDriveForward(driveSub, 0.4, 1.5);
      

      case DriveForward:
        return AutoCommands.autoDriveForward(driveSub, 0.5, 2);
       

      case None:
      default:
        return new Command(){};
    }
  }
}
