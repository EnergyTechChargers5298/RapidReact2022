// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

public class ShiftSpeed extends CommandBase {

  // Drivetrain subsystem needed for this command
  private Drivetrain drivetrain;

  /** Creates a new ShiftSpeed. */
  public ShiftSpeed(Drivetrain drivetrain) {
    // Stores drivetrain which is provided by RobotContainer
    this.drivetrain = drivetrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Shifts to speed mode
    drivetrain.shiftSpeed();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // It is true because this command only runs once
    return true;
  }
}
