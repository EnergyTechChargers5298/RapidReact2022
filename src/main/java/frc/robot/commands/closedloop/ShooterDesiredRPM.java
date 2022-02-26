// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.closedloop;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.Control;
import frc.robot.subsystems.Shooter;

public class ShooterDesiredRPM extends CommandBase {

  private SimpleMotorFeedforward feedforward;
  private PIDController controller;
  private Shooter shooter;
  private double desiredRPM;

  /** Creates a new ShooterDesiredRPM. */
  public ShooterDesiredRPM(Shooter shooter, double desiredRPM) {

    this.shooter = shooter;
    this.feedforward = new SimpleMotorFeedforward(Control.FLYWHEEL_KSV[0], Control.FLYWHEEL_KSV[1]);
    this.controller = new PIDController(Control.FLYWHEEL_PID[0], Control.FLYWHEEL_PID[1], Control.FLYWHEEL_PID[2]);
    this.desiredRPM = desiredRPM;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.stopFly();
    controller.setSetpoint(desiredRPM);
    controller.setTolerance(Control.FLYWHEEL_TOLERANCE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double volts = feedforward.calculate(desiredRPM);
    SmartDashboard.putNumber("FLYWHEEL VOLTS", volts);
    double errorFix = controller.calculate(shooter.getVelocity());
    shooter.flyVolt(volts + errorFix);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopFly();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
