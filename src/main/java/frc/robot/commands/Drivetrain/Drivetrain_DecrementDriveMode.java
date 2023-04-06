package frc.robot.commands.Drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class Drivetrain_DecrementDriveMode extends CommandBase {
    
    Drivetrain m_drivetrain;

    public Drivetrain_DecrementDriveMode(Drivetrain drivetrain) {

        m_drivetrain = drivetrain;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_drivetrain.driveMode--;
        if (m_drivetrain.driveMode < 0) {
            m_drivetrain.driveMode = 4;
        }
        System.out.println("Drivemode num.  > " + String.valueOf(m_drivetrain.driveMode));
        System.out.println("Drive: SELECTED > " + m_drivetrain.driveModes[m_drivetrain.driveMode]);    
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
