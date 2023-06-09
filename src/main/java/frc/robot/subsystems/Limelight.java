package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Limelight {
  //private Robot robot;
  public double ledMode;

  public void setPipeline(int pipeline) {
    //robot.tableLimelight.getEntry("Pipeline").setNumber(pipeline);
    SmartDashboard.putNumber("Pipeline Mode", pipeline);
  }

  public void switchLED(double ledMode) {
    //robot.tableLimelight.getEntry("ledMode").setDouble(ledMode);
    SmartDashboard.putString("LED Mode", "Off");
  }
}