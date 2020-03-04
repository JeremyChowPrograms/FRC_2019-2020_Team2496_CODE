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

  //private Joystick gjs;

  private Joystick jsl;

  private Joystick jsr;

  private SpeedController tm1 = new Talon(8);
  private SpeedController tm2 = new Talon(7);
  private SpeedController tm3 = new Talon(5);
  private ReversibleChassisControl rcc = new ReversibleChassisControl(3, 0, 4, 6);
 
  @Override
  public void robotInit() {
    camera.setResolution(400, 300);
    camera.setFPS(30);
   // gjs = new Joystick(2);
    jsl = new Joystick(0);
    jsr = new Joystick(1);
    SmartDashboard.putNumber("DriveSpeed", 1);
    SmartDashboard.putNumber("TestSpeed", 1);
  }

  private double driveSpeed = 1.0d;
  private double testSpeed = 1.0d;

  @Override
  public void autonomousInit(){
    rcc.StraightForward=true;
    rcc.tankDrive(0.5, 0.5);
    Timer.delay(
      4
    );
  }

  @Override
  public void autonomousPeriodic() {
    rcc.tankDrive(0, 0);
  }

  @Override
  public void disabledPeriodic() {
    SmartDashboard.putString("CS", ColorSwitch.inputColor(cs.getColor()).toString());
  }

  private boolean gAuthority = false;

  @Override
  public void teleopPeriodic() {
    driveSpeed = SmartDashboard.getNumber("DriveSpeed", 1);
    testSpeed = SmartDashboard.getNumber("TestSpeed", 1);
    SmartDashboard.putString("Drive State", rcc.StraightForward?"Forward":"Reverse");
    SmartDashboard.putString("CS", ColorSwitch.inputColor(cs.getColor()).toString());
    if (jsr.getRawButton(3)&&gAuthority==false) {
      tm1.set(1);
    } else if(jsr.getRawButton(1)&&gAuthority==false){
      tm1.set(-1);
    }else{
      tm1.set(0);
    }
    if (jsr.getRawButton(4)&&gAuthority==false) {
      servo.set(-0.5);
    } else if (jsr.getRawButton(5)&&gAuthority==false) {
      servo.set(.5);
    } else {
      servo.set(0.0d);
    }
    /*if (js.getRawButton(3)) {
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
    }*/
    if(jsl.getRawButton(1)&&gAuthority==false){
      tm3.set(1);
    }else
    tm3.set(0.0);
    if(gAuthority==false)
    rcc.tankDrive(jsl.getRawAxis(1) * (jsl.getRawAxis(2)-1)/2, jsr.getRawAxis(1) * (jsr.getRawAxis(2)-1)/2);
    else
    rcc.arcadeDrive(-jsr.getRawAxis(0)*driveSpeed, -jsr.getRawAxis(1)*driveSpeed);
    if (jsl.getRawButton(4)&&gAuthority==false) {
      rcc.StraightForward = true;
    }
    if (jsl.getRawButton(5)&&gAuthority==false) {
      rcc.StraightForward = false;
    }
    if(jsl.getRawButton(3)&&gAuthority==false)
    {
      tm2.set(.5);
    }else
      if(jsr.getRawButton(2)&&gAuthority==false){
        tm2.set(-.5);
      }else{
        tm2.set(0);
      }
/*
    if(gjs.getRawButton(1))
    gAuthority=true;
    if(gjs.getRawButton(2))
    gAuthority=false;/* */
  }
}
