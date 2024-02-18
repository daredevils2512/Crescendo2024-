
package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;

public final class Constants {

  public static class DrivetrainConstants {
    // Motors
    public static final int DRIVE_FRONT_LEFT_ID = 1; //BACK
    public static final int DRIVE_BACK_LEFT_ID = 2; //FRONT
    public static final int DRIVE_FRONT_RIGHT_ID = 3;
    public static final int DRIVE_BACK_RIGHT_ID = 4;

    // Encoders
    public static final int LEFT_ENCODER_A = 0;
    public static final int LEFT_ENCODER_B = 1;
    public static final int RIGHT_ENCODER_A = 2; //2, but for testing its 10
    public static final int RIGHT_ENCODER_B = 3;
    public static final double PULSES_PER_ROTATIONS = 256;
    public static final double WHEEL_CIRCUMFERENCE = 6* Math.PI;
    public static final double DISTANCE_PER_PULSE = WHEEL_CIRCUMFERENCE/PULSES_PER_ROTATIONS;
  }

  public static class IntakeConstants {
    // Motors
    public static final int INTAKE_MOTOR_ID = 5;

    // Encoders
    public static final int INTAKE_ENCODER_A = 4;
    public static final int INTAKE_ENCODER_B = 5;
    public static final int INTAKE_LIMIT_SWITCH_TOP = 6;
    public static final int INTAKE_LIMIT_SWITCH_BOTTOM = 7;

  }

  public static class ShooterConstants {
    //Motors
    public static final int SHOOTER_MOTOR_ID = 6;
    public static final int SHOOTER_ACTUATE_MOTOR_ID = 7;

    //Encoders
    public static final int SHOOTER_ENCODER_A = 8; 
    public static final int SHOOTER_ENCODER_B = 9;
    public static final int SHOOTER_LIMIT_SWITCH_TOP = 10;
    public static final int SHOOTER_LIMIT_SWITCH_BOTTOM = 11;
    public static final double ENCODER_POSITION = 2;
    public static final double TOLERANCE = 0.3;

  }

  public static class ClimberConstants {
    //Motors
    public static final int CLIMBER_MOTOR_ID = 8;
    
    //Encoders
    public static final int CLIMBER_LIMIT_SWITCH_TOP = 12;
    public static final int CLIMBER_LIMIT_SWITCH_BOTTOM = 13;
  }

  public static class IoConstants {
    // Control Board
    public static final int XBOX_CONTROLLER_PORT = 0;
    public static final int EXTREME_PORT = 1;
    public static final int BUTTON_BOX_PORT = 2;

    // Xbox Controller
    public static final int XBOX_POV_UP_DEGREES = 0;
    public static final int XBOX_POV_UP_RIGHT_DEGREES = 45;
    public static final int XBOX_POV_RIGHT_DEGREES = 90;
    public static final int XBOX_POV_DOWN_RIGHT_DEGREES = 135;
    public static final int XBOX_POV_DOWN_DEGREES = 180;
    public static final int XBOX_POV_DOWN_LEFT_DEGREES = 225;
    public static final int XBOX_POV_LEFT_DEGREES = 270;
    public static final int XBOX_POV_UP_LEFT_DEGREES = 315;
    public static final int XBOX_POV_RELEASED_DEGREES = -1;
    public static final RumbleType XBOX_LEFT_RUMBLE = RumbleType.kLeftRumble;
    public static final RumbleType XBOX_RIGHT_RUMBLE = RumbleType.kRightRumble;

    // Extreme
    public static final int EXTREME_TRIGGER_PORT = 1;
    public static final int EXTREME_SIDE_BUTTON_PORT = 2;
    public static final int EXTREME_JOYSTICK_BOTTOM_LEFT_PORT = 3;
    public static final int EXTREME_JOYSTICK_BOTTOM_RIGHT_PORT = 4;
    public static final int EXTREME_JOYSTICK_TOP_LEFT_PORT = 5;
    public static final int EXTREME_JOYSTICK_TOP_RIGHT_PORT = 6;
    public static final int EXTREME_BASE_FRONT_LEFT_PORT = 7;
    public static final int EXTREME_BASE_FRONT_RIGHT_PORT = 8;
    public static final int EXTREME_BASE_MIDDLE_LEFT_PORT = 9;
    public static final int EXTREME_BASE_MIDDLE_RIGHT_PORT = 10;
    public static final int EXTREME_BASE_BACK_LEFT_PORT = 11;
    public static final int EXTREME_BASE_BACK_RIGHT_PORT = 12;
    public static final int EXTREME_STICK_X_AXIS_ID = 0;
    public static final int EXTREME_STICK_Y_AXIS_ID = 1;
    public static final int EXTREME_STICK_Z_AXIS_ID = 2;
    public static final int EXTREME_SLIDER_AXIS_ID = 3;
    public static final int EXTREME_POV_UP_DEGREES = 0;
    public static final int EXTREME_POV_UP_RIGHT_DEGREES = 45;
    public static final int EXTREME_POV_RIGHT_DEGREES = 90;
    public static final int EXTREME_POV_DOWN_RIGHT_DEGREES = 135;
    public static final int EXTREME_POV_DOWN_DEGREES = 180;
    public static final int EXTREME_POV_DOWN_LEFT_DEGREES = 225;
    public static final int EXTREME_POV_LEFT_DEGREES = 270;
    public static final int EXTREME_POV_UP_LEFT_DEGREES = 315;
  }
}
