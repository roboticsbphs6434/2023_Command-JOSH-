package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class Arm_Movement extends CommandBase {

    Arm m_arm;
    String m_moveRequest;
    boolean armMovement;

    public Arm_Movement(Arm arm, String moveRequest) {
        m_arm = arm;
        m_moveRequest = moveRequest;

        addRequirements(arm);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      armMovement = true;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
      if (m_moveRequest == "Home" ) {
        armMovement = m_arm.smoothArm(0.0);
      } else if (m_moveRequest == "Delivery" ) {
        armMovement = m_arm.smoothArm(100.0);
      } else if (m_moveRequest == "PickUp" ) {
        armMovement = m_arm.smoothArm(120.0);
      } else if (m_moveRequest == "Ground" ) {
        armMovement = m_arm.smoothArm(135.0);
      } else {
        m_arm.armOff();
        armMovement = false;
      }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
      m_arm.armOff();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        return false;

    }
}
