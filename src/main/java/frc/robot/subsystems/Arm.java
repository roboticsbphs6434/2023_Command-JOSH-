package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
  public CANSparkMax CANarmSpark;
  public RelativeEncoder armEncoder;
  public double armSpeed = 0.2;
  public double m_destAngle;

  public Arm() {
    CANarmSpark = new CANSparkMax(10, MotorType.kBrushless);
    CANarmSpark.setIdleMode(IdleMode.kBrake);
    CANarmSpark.setSmartCurrentLimit(10);
    armEncoder = CANarmSpark.getEncoder();
    armSpeed = 0;

  }

  // Open point val: 143.52
  // Close point val: 5


  public void periodic() {}

  public boolean moveArm(double destAngle, double armSpeed) {
    double direction;
    double tolerance = 5.0;
    double maxTol = destAngle + tolerance;
    double minTol = destAngle - tolerance;
    if (armEncoder.getPosition() < destAngle) {
      direction = +1.0;
    } else {
      direction = -1.0; 
    }   
    if (armEncoder.getPosition() > maxTol || armEncoder.getPosition() < minTol ) {
      CANarmSpark.set(armSpeed * direction); 
      return true;
    } else if (destAngle == 0.0) {
       return false;
    } else {
      CANarmSpark.set(0.0); 
      return true;
    }
  }

  public boolean smoothArm(double destAngle) {
    double armMaxSpeed = 0.4;
    double targetVel = 700;
    double toleranceVel = 200;
    double armdirection;
    double toleranceAngle = 5.0;
    double maxAngle = destAngle + toleranceAngle;
    double minAngle = destAngle - toleranceAngle;
    double curAngle = armEncoder.getPosition();
    double curVel = armEncoder.getVelocity();
    double absVel = Math.abs(curVel);
    double absArmSpeed = Math.abs(armSpeed);

    if (curAngle < destAngle) {
      armdirection = +1.0;
    } else {
      armdirection = -1.0; 
    }
    double maxVel = (targetVel + toleranceVel);
    double minVel = (targetVel - toleranceVel);

    if (absVel > maxVel) absArmSpeed -= 0.0005;
    if (absVel < minVel) absArmSpeed += 0.0005;

    if (absArmSpeed > armMaxSpeed ) absArmSpeed = armMaxSpeed;
    armSpeed = absArmSpeed * armdirection;
    SmartDashboard.putNumber("Cur. Angle", curAngle);
    SmartDashboard.putNumber("Dest. Angle", destAngle);
    SmartDashboard.putNumber("Arm Speed", armSpeed);
    SmartDashboard.putNumber("Arm Dir", armdirection);
    SmartDashboard.putNumber("Arm Vel", curVel);
    SmartDashboard.putNumber("Min Vel.", minVel);
    SmartDashboard.putNumber("Max Vel.", maxVel);
    if (curAngle > maxAngle || curAngle < minAngle ) {
      CANarmSpark.set(armSpeed); 
      return true;
    } else if (destAngle == 0.0) {
      armSpeed = 0.2;
      return false;
    } else {
      armSpeed = 0.2;
      CANarmSpark.set(0.0); 
      return true;
    }
  }
  
  public void armOff() {
    CANarmSpark.set(Math.abs(0));
  }
}