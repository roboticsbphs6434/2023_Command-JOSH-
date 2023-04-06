package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Intake {
  public DoubleSolenoid intakeDoubleSolenoid;

  public Intake() {
    intakeDoubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);
  }

  public void open() {
    intakeDoubleSolenoid.set(Value.kForward);
  }

  public void close() {
    intakeDoubleSolenoid.set(Value.kReverse);
  }

  public void off() {
    intakeDoubleSolenoid.set(Value.kOff);
  }

  public void togglePiston() {
    if (intakeDoubleSolenoid.get() == Value.kForward) {
      System.out.println("Setting reverse");
      intakeDoubleSolenoid.set(Value.kReverse);
    } else {
      System.out.println("Setting forward");
      intakeDoubleSolenoid.set(Value.kForward);
    }
  }
}