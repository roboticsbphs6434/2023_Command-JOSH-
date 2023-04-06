package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Drivetrain_DriveSlowToggle extends CommandBase {
    
    Drivetrain m_drivetrain;

    public Drivetrain_DriveSlowToggle(Drivetrain drivetrain) {

        m_drivetrain = drivetrain;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (m_drivetrain.driveSlow) {
            m_drivetrain.driveSlow = false;
            m_drivetrain.driveRmax = 0.75;
            m_drivetrain.driveSmax = 1.00;
          } else {
            m_drivetrain.driveSlow = true;
            m_drivetrain.driveRmax = 0.55;
            m_drivetrain.driveSmax = 0.6;
          }
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return true;
    }
    @Override
    public boolean runsWhenDisabled()
    {
        return true;
    }
}
