package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Autonomous;
import frc.robot.subsystems.Drivetrain;

public class autonomousCommand extends CommandBase {
    // Called when the command is initially scheduled.

    // Auto
    private Autonomous auto;

    private String autoSelected;
    private final SendableChooser<String> autoChooser = new SendableChooser<>();
    
    private static final String deliverExit = "Deliver & Exit";
    private static final String defaultAuto = "Default";
    private static final String JoshAuto = "JoshAuto";
    private static final String driveOut = "Drive Out";
    
    private Timer autoTimer;    
    
    Drivetrain m_drivetrain;

    int state = 0;
    
    public autonomousCommand(Drivetrain drivetrain)
    {
        m_drivetrain = drivetrain;
        addRequirements(drivetrain);
        auto = new Autonomous(drivetrain);
        autoTimer = new Timer();
        autoChooser.setDefaultOption("Default Auto", defaultAuto);
        autoChooser.addOption("Deliver & Exit", deliverExit);
        autoChooser.addOption("JoshAuto", JoshAuto);
        autoChooser.addOption("Drive Out", driveOut);
        SmartDashboard.putData("Auto Chooser", autoChooser);
        
    }

    @Override
    public void initialize() {
        Shuffleboard.selectTab("Autonomous");
        autoSelected = autoChooser.getSelected();
        System.out.println("Auto selected: " + autoSelected);
        autoTimer.reset();
        autoTimer.start();
        m_drivetrain.resetEncoder();
        m_drivetrain.resetGyro();
        state = 0;
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        SmartDashboard.putString("Auto Timer",
          "Auto Timer = " + String.format("%.2f", autoTimer.get()));
      
                 /** *********************** NOTE ******************************
       * During both teleopPeriodic and autonomousPeriodic the drive
       * train needs to be updated regularly (every loop/period).
       * If the drivetrain is not updated often enough an error will be 
       * reported.
       * 
       *  DifferentialDrive... output not updated often enough.
       */
      
      // auto.driveOff();
       switch (autoSelected) {
        // case deliverExit:
          // if(drivetrain.leftEncoder.getDistance() < auto.driveDistance(-0.3) && state == 0) {
          //   drivetrain.drive.tankDrive(-0.5, -0.5);
          // } else if (drivetrain.leftEncoder.getDistance() < auto.driveDistance(1.5) && state == 1) {
          //   state = 1;
          //   drivetrain.drive.tankDrive(0.5, 0.5);
          // } else {
          //   drivetrain.drive.tankDrive(0, 0);
          // }
        case JoshAuto:
            if(m_drivetrain.leftEncoder.getDistance() < auto.driveDistance(1.5)) {
              // drivetrain.drive.tankDrive(0.5, 0.5);
              auto.driveStraight(0.5);
            }else {
              m_drivetrain.drive.tankDrive(0, 0);
            }
          // if (drivetrain.distanceAVG() <= auto.driveDistance(1) && state == 0) {
          //   auto.driveStraight(0.2);
          // }
          // else if (drivetrain.distanceAVG() <= auto.driveDistance(-0.3) && state == 1) {
          //   state = 1;
          //   auto.driveStraight(-0.2);
          // }
          // else if (drivetrain.navx.getAngle() <= 90 && state == 2) { // Turn in direction closer to 90 degrees.
          //   state = 2;
          //   if (drivetrain.navx.getAngle() > 90) {
          //     auto.turnLeft(0.2);
          //   }
          //   else if (drivetrain.navx.getAngle() < 90) {
          //     auto.turnRight(0.2);
          //   }
          //   else {
          //     auto.driveOff();
          //   }
          // } 
        case driveOut:
          if (autoTimer.get() < 5.0) {
            auto.driveStraight(0.2);
          } else {
            auto.driveOff();
            autoTimer.stop();
          }
          break;
        case defaultAuto:
        default:
          auto.driveOff();
          autoTimer.stop();
          break;
       }
    }
    

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
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
