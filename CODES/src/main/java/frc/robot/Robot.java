package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.ColorSwitch.PossibleColor;

public class Robot extends TimedRobot {

  private UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
  private ColorSensorV3 cs = new ColorSensorV3(Port.kOnboard);
  private SpeedController servo = new Talon(9);

  private Joystick js;

  private SpeedController tm1 = new Talon(8);
  private SpeedController tm2 = new Talon(7);
  private ReversibleChassisControl rcc = new ReversibleChassisControl(3, 0, 4, 6);
  /*
   * private SpeedController mc1 = new Talon(0); private SpeedController mc2 = new
   * Talon(3); private SpeedController mc3 = new Talon(4); private SpeedController
   * mc4 = new Talon(6);
   */

  @Override
  public void robotInit() {
    camera.setResolution(400, 300);
    camera.setFPS(120);
    js = new Joystick(2);
    SmartDashboard.putNumber("DriveSpeed", 1);
    SmartDashboard.putNumber("TestSpeed", 1);
  }

  private double driveSpeed = 1.0d;
  private double testSpeed = 1.0d;

  @Override
  public void teleopPeriodic() {
    driveSpeed = SmartDashboard.getNumber("DriveSpeed", 1);
    testSpeed = SmartDashboard.getNumber("TestSpeed", 1);
    SmartDashboard.putString("CS", ColorSwitch.inputColor(cs.getColor()).toString());
    if (js.getRawButton(1)) {
      tm1.set(driveSpeed);
    } else {
      tm1.set(0);
    }
    if (js.getRawButton(2)) {
      tm2.set(testSpeed);
    } else {
      tm2.set(0);
    }
    if (js.getRawButton(5)) {
      servo.set(-0.5);
    } else if (js.getRawButton(6)) {
      servo.set(.5);
    } else {
      servo.set(0.0d);
    }
    if (js.getRawButton(3)) {
      String gameData = DriverStation.getInstance().getGameSpecificMessage();
      PossibleColor color = PossibleColor.WTF;
      if (gameData.length() > 0) {
        switch (gameData.charAt(0)) {
        case 'B':
          color = PossibleColor.RED;
          break;
        case 'G':
          color = PossibleColor.YELLOW;
          break;
        case 'R':
          color = PossibleColor.BLUE;
          break;
        case 'Y':
          color = PossibleColor.GREEN;
          break;
        default:
          break;
        }
      }
      tm1.set(1.0);
      boolean antiBreak = true;
      while (antiBreak) {
        if (ColorSwitch.inputColor(cs.getColor()) == color) {
          Timer.delay(.5);
          if (ColorSwitch.inputColor(cs.getColor()) == color)
            antiBreak = false;
        }
      }
      tm1.set(0.0);
    }
    rcc.tankDrive(-js.getRawAxis(1) * driveSpeed, -js.getRawAxis(5) * driveSpeed);
    if (js.getRawButton(7)) {
      rcc.StraightForward = true;
    }
    if (js.getRawButton(8)) {
      rcc.StraightForward = false;
    }
  }
}
