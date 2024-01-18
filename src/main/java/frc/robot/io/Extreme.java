    package frc.robot.io;

    import edu.wpi.first.wpilibj.Joystick;
    import edu.wpi.first.wpilibj2.command.button.JoystickButton;
    import edu.wpi.first.wpilibj2.command.button.POVButton;
    import edu.wpi.first.wpilibj2.command.button.Trigger;
    import frc.robot.Constants.IoConstants;
    
    public class Extreme {
      // Joystick
      private final Joystick joystick;
    
      // Buttons
      public final Trigger trigger;
      public final Trigger sideButton;
      public final Trigger joystickBottomLeft;
      public final Trigger joystickBottomRight;
      public final Trigger joystickTopLeft;
      public final Trigger joystickTopRight;
      public final Trigger baseFrontLeft;
      public final Trigger baseFrontRight;
      public final Trigger baseMiddleLeft;
      public final Trigger baseMiddleRight;
      public final Trigger baseBackLeft;
      public final Trigger baseBackRight;
      public final Trigger joystickUp;
      public final Trigger joystickUpRight;
      public final Trigger joystickRight;
      public final Trigger joystickDownRight;
      public final Trigger joystickDown;
      public final Trigger joystickDownLeft; 
      public final Trigger joystickLeft;
      public final Trigger joystickUpLeft;
    
      public Extreme(int port) {
        joystick = new Joystick(port);
    
        trigger = new JoystickButton(joystick, IoConstants.EXTREME_TRIGGER_PORT);
        sideButton = new JoystickButton(joystick, IoConstants.EXTREME_SIDE_BUTTON_PORT);
        joystickBottomLeft = new JoystickButton(joystick, IoConstants.EXTREME_JOYSTICK_BOTTOM_LEFT_PORT);
        joystickBottomRight = new JoystickButton(joystick, IoConstants.EXTREME_JOYSTICK_BOTTOM_RIGHT_PORT);
        joystickTopLeft = new JoystickButton(joystick, IoConstants.EXTREME_JOYSTICK_TOP_LEFT_PORT);
        joystickTopRight = new JoystickButton(joystick, IoConstants.EXTREME_JOYSTICK_TOP_RIGHT_PORT);
        baseFrontLeft = new JoystickButton(joystick, IoConstants.EXTREME_BASE_FRONT_LEFT_PORT);
        baseFrontRight = new JoystickButton(joystick, IoConstants.EXTREME_BASE_FRONT_RIGHT_PORT);
        baseMiddleLeft = new JoystickButton(joystick, IoConstants.EXTREME_BASE_MIDDLE_LEFT_PORT);
        baseMiddleRight = new JoystickButton(joystick, IoConstants.EXTREME_BASE_MIDDLE_RIGHT_PORT);
        baseBackLeft = new JoystickButton(joystick, IoConstants.EXTREME_BASE_BACK_LEFT_PORT);
        baseBackRight = new JoystickButton(joystick, IoConstants.EXTREME_BASE_BACK_RIGHT_PORT);
        joystickUp = new POVButton(joystick, IoConstants.EXTREME_POV_UP_DEGREES);
        joystickUpRight = new POVButton(joystick, IoConstants.EXTREME_POV_UP_RIGHT_DEGREES);
        joystickRight = new POVButton(joystick, IoConstants.EXTREME_POV_RIGHT_DEGREES);
        joystickDownRight = new POVButton(joystick, IoConstants.EXTREME_POV_DOWN_RIGHT_DEGREES);
        joystickDown = new POVButton(joystick, IoConstants.EXTREME_POV_DOWN_DEGREES);
        joystickDownLeft = new POVButton(joystick, IoConstants.EXTREME_POV_DOWN_LEFT_DEGREES);
        joystickLeft = new POVButton(joystick, IoConstants.EXTREME_POV_LEFT_DEGREES);
        joystickUpLeft = new POVButton(joystick, IoConstants.EXTREME_POV_UP_LEFT_DEGREES);
      }
    
      /** @return StickX */
      public double getStickX() {
        return joystick.getRawAxis(IoConstants.EXTREME_STICK_X_AXIS_ID);
      }
    
      /** @return StickY */
      public double getStickY() {
        return joystick.getRawAxis(IoConstants.EXTREME_STICK_Y_AXIS_ID);
      }
    
      /** @return StickZ */
      public double getStickZ() {
        return joystick.getRawAxis(IoConstants.EXTREME_STICK_Z_AXIS_ID);
      }
    
      /** @return Slider */
      public double getSlider() {
        return joystick.getRawAxis(IoConstants.EXTREME_SLIDER_AXIS_ID);
      }
    
    }  
