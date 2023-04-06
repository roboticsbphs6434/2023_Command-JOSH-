package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class mainCommand extends CommandBase {

    //public XboxController controller;

    private Drivetrain m_drivetrain;
    private Integer driveMode = 0;

    /** Drive power: tank drive */
    private double driveLeft;
    private double driveRight;
    /** Drive power: all other drive modes */
    private double driveRotate;
    private double driveSpeed;
    private double driveCurveMod = 0.70; // Curve drive is too powerful.
    
    public double leftSpeed;
    public double rightSpeed;
    public double steeringAdjust;
    public double distanceAdjust;
    DoubleSupplier m_leftX, m_leftY, m_rightX, m_rightY;
    BooleanSupplier m_leftStick, m_rightStick;
    
    public mainCommand(Drivetrain drivetrain, DoubleSupplier leftX, DoubleSupplier leftY, DoubleSupplier rightX, DoubleSupplier rightY, BooleanSupplier leftStick, BooleanSupplier rightStick)
    {
      m_drivetrain = drivetrain;
      m_leftStick = leftStick;
      m_rightStick = rightStick;

      m_leftX = leftX;
      m_leftY = leftY;
      m_rightX = rightX;
      m_rightY = m_rightX;
      addRequirements(drivetrain);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
      Shuffleboard.selectTab("Tele Op");
      m_drivetrain.resetEncoder();
      m_drivetrain.resetGyro();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        /** NOTE: forward on the joystick produces a negative value.
       * Take care to invert the Y values from the joystick.
       */
      switch (m_drivetrain.driveModes[driveMode]) {
        case "Arcade2":
        case "Curvature2":
          if (m_drivetrain.driveDirection >= 0 ) {
            driveRotate = -m_rightX.getAsDouble() * m_drivetrain.driveRmax * m_drivetrain.driveDirection;
          } else {
            driveRotate = m_rightX.getAsDouble() * m_drivetrain.driveRmax * m_drivetrain.driveDirection;
          }
          driveSpeed = -m_leftY.getAsDouble() * m_drivetrain.driveSmax * m_drivetrain.driveDirection;
          SmartDashboard.putString("Drive Speed",
              "driveSpeed    = " + String.format("%.2f", driveSpeed));
          SmartDashboard.putString("Drive Rotate",
              "driveRotate = " + String.format("%.2f", driveRotate));
          break;
        case "Arcade1":
        case "Curvature1":
          if (m_drivetrain.driveDirection >= 0 ) {
            driveRotate = -m_leftX.getAsDouble() * m_drivetrain.driveRmax * m_drivetrain.driveDirection;
          } else {
            driveRotate = m_leftX.getAsDouble() * m_drivetrain.driveRmax * m_drivetrain.driveDirection;
          }
          driveSpeed = -m_leftY.getAsDouble() * m_drivetrain.driveSmax * m_drivetrain.driveDirection;
          SmartDashboard.putString("Drive Speed",
              "driveSpeed    = " + String.format("%.2f", driveSpeed));
          SmartDashboard.putString("Drive Rotate",
              "driveRotate = " + String.format("%.2f", driveRotate));
          break;
        case "Tank":
        default:
          if (m_drivetrain.driveDirection >= 0 ) {
            driveLeft = -m_leftY.getAsDouble() * m_drivetrain.driveSmax * m_drivetrain.driveDirection;
            driveRight = -m_rightY.getAsDouble() * m_drivetrain.driveSmax * m_drivetrain.driveDirection;
          } else {
            driveLeft = -m_rightY.getAsDouble() * m_drivetrain.driveSmax * m_drivetrain.driveDirection;
            driveRight = -m_leftY.getAsDouble() * m_drivetrain.driveSmax * m_drivetrain.driveDirection;
          }
        SmartDashboard.putString("Drive Left",
              "driveLeft = " + String.format("%.2f", driveLeft));
        SmartDashboard.putString("Drive Right",
              "driveRight  = " + String.format("%.2f", driveRight));
      }
      // Driver station: Basic Tab
      SmartDashboard.putString("Left Y Joystick",
          "LeftY = " + String.format("%.2f", m_leftY.getAsDouble()));
      SmartDashboard.putString("Left X Joystick",
          "LeftX = " + String.format("%.2f", m_leftX.getAsDouble()));
      SmartDashboard.putString("Right Y Joystick",
          "RightY = " + String.format("%.2f", m_rightY.getAsDouble()));
      SmartDashboard.putString("Right X Joystick",
          "RightX = " + String.format("%.2f", m_rightX.getAsDouble()));
      SmartDashboard.putString("Drive Direction",
          "driveDirection = " + String.format("%.2f", m_drivetrain.driveDirection));
      SmartDashboard.putString("Robot Angle",
          "Robot Angle = " + String.format("%.2f", m_drivetrain.robotBearing()));
      SmartDashboard.putString("Drivetrain Left Encoder", 
          "Left Encoder = " + String.format("%.3f", m_drivetrain.leftEncoder.getDistance()));
      SmartDashboard.putString("Drivetrain Right Encoder",
          "Right Encoder = " + String.format("%.3f", m_drivetrain.rightEncoder.getDistance()));
      SmartDashboard.putString("Distance Encoder Value",
          "Distance Encoder Value = " + String.format("%.3f", m_drivetrain.distanceAVG()));
      SmartDashboard.putString("Robot Angle", // TODO: Work on robot angle
          "Robot Angle = " + String.format("%.2f", m_drivetrain.navx.getAngle()));
      // // Neo data (ARM)
      // SmartDashboard.putString("NEO Encoder",
      //     "NEO Encoder = " + String.format("%.2f", arm.armEncoder.getPosition()));
      // SmartDashboard.putString("NEO Velocity",
      //     "NEO Velocity = " + String.format("%.2f", -arm.armEncoder.getVelocity()));
      // SmartDashboard.putBoolean("Drive Slow", driveSlow);

      switch (m_drivetrain.driveModes[driveMode]) {
        case "Arcade2":
        case "Arcade1":
        m_drivetrain.drive.arcadeDrive(
            driveSpeed,
            driveRotate,
            true
          );
          break;
        case "Curvature2":
          /** Curvature drive with a given forward and turn rate +
           * as well as a button for turning in-place.
           */
          m_drivetrain.drive.curvatureDrive(
            driveSpeed * driveCurveMod,
            driveRotate * driveCurveMod,
            m_rightStick.getAsBoolean()
          );
          break;
        case "Curvature1":
          /** Curvature drive with a given forward and turn rate +
           * as well as a button for turning in-place.
           */
          m_drivetrain.drive.curvatureDrive(
            driveSpeed * driveCurveMod,
            driveRotate * driveCurveMod,
            m_leftStick.getAsBoolean()
          );
          break;
        case "Tank":
        default:
          m_drivetrain.drive.tankDrive(
            driveLeft,
            driveRight,
            true
        );
      }
    }
}
