/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is a demo program showing the use of the RobotDrive class, specifically
 * it contains the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {

  private ColorSensorV3 cs = new ColorSensorV3(Port.kOnboard);
  
  private Joystick js;
  
  private SpeedController tm1 =  new Talon(6);
  
  /*
  private SpeedController mc1 = new Talon(0);
  private SpeedController mc2 = new Talon(1);
  private SpeedController mc3 = new Talon(3);
  private SpeedController mc4 = new Talon(4);
  private SpeedController motor = new Talon(5);*/

  @Override
  public void robotInit() {
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
    if(js.getRawButton(1)){
      tm1.set(driveSpeed);
    }else{
      tm1.set(0);
    }
    /*
    if(js.getRawButton(1)){
      mc1.set(driveSpeed);
    }else{
      mc1.set(0);
    }
    if(js.getRawButton(2)){
      mc2.set(driveSpeed);
    }else{
      mc2.set(0);
    }
    if(js.getRawButton(3)){
      mc3.set(driveSpeed);
    }else{
      mc3.set(0);
    }
    if(js.getRawButton(4)){
      mc4.set(driveSpeed);
    }else{
      mc4.set(0);
    }
    if(js.getRawButton(5)){
      motor.set(testSpeed);
    }else{
      motor.set(0);
    }*/
  }
}
