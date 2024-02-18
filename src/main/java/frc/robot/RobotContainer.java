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
  private static final String simpleAuto = "move forward";
  private static final String fullAuto = "full auto";
  private static final String middleAuto = "middle auto";
  private static final String farAuto = "far auto";
  private String autoSelected;
  private final SendableChooser<String> chooser = new SendableChooser<>();
  private final PIDTest pid = new PIDTest();

  public RobotContainer() {
    configureBindings();

    chooser.setDefaultOption("move forward", simpleAuto);
    chooser.addOption("full auto", fullAuto);
    chooser.addOption("middle auto", middleAuto);
    chooser.addOption("far Auto", farAuto);
    SmartDashboard.putData("Auto choices", chooser);
    autoSelected = chooser.getSelected();
    System.out.println("Auto selected: " + autoSelected);

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
    driveSub.setDefaultCommand(driveSub.run(() -> driveSub.arcadeDrive(-xbox.getLeftY(), xbox.getLeftX())));
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
    extreme.baseFrontLeft.onTrue(ShooterCommands.setPosition(shooterSub, Position.Top, 0.6));
    extreme.baseMiddleLeft.onTrue(ShooterCommands.setPosition(shooterSub, Position.Handoff, 0.6));
    extreme.baseBackLeft.onTrue(ShooterCommands.setPosition(shooterSub, Position.Bottom, 0.6));

    // Climber
    extreme.joystickTopRight.whileTrue(ClimberCommands.runClimberUp(climberSub, 0.5));
    extreme.joystickTopLeft.whileTrue(ClimberCommands.runClimberUp(climberSub, -0.5));
  }

  public void autonomousCommand() {
  switch (autoSelected) {
      case fullAuto:
        // !!!! please test this before running it, idk if it will break the robot or if it even works

         AutoCommands.autoDriveAndTurn(driveSub, 0.5, 2) // theoretically be at amp
            // return AutoCommands.AutoDriveForward(driveSub, 0.5, 1) // use this if ^ doesnt work
            // .andThen(AutoCommands.AutoDriveLeft(driveSub, 0.5, 1))
            .andThen(ShooterCommands.runTimedShooterActuate(shooterSub, 0.5, 2)) // change time till it gets to the top
            .andThen(ShooterCommands.runShooter(shooterSub, 1)).withTimeout(1) // shoot in amp
            .andThen(ShooterCommands.runTimedShooterActuate(shooterSub, -0.5, 2)) // maybe put after moving, might slam into the amp
            .andThen(AutoCommands.autoDriveAndTurn(driveSub, -0.5, 1))
            .andThen(IntakeCommands.runIntakeToShooter(intakeSub, shooterSub, 1).withTimeout(1.3)); // intake another ring
        break;

      case middleAuto:
         AutoCommands.autoDriveForward(driveSub, 0.5, 1);
        break;

      case farAuto: // make sure this is tuned so it doesnt slam into the stage
        AutoCommands.autoDriveForward(driveSub, 0.5, 1.5);
        break;

      case simpleAuto:
      default:
        AutoCommands.autoDriveForward(driveSub, 0.5, 2);
        break;
    }
  }
}
