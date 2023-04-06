package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Drivetrain_DriveTime extends CommandBase {
    
    Drivetrain m_drivetrain;

    Timer m_timer;

    Double m_time;

    public Drivetrain_DriveTime(Drivetrain drivetrain, double time) {

        m_drivetrain = drivetrain;
        m_time = time;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_timer.start();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_drivetrain.driveSpeedTank(0.4);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_drivetrain.driveSpeedTank(0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return m_timer.get()>m_time;
    }
}
