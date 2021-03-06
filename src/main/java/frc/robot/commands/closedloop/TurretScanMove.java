// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.closedloop;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class TurretScanMove extends SequentialCommandGroup {
  private DoubleSupplier joy;
  /** Creates a new TurretScanMove. */
  public TurretScanMove(Turret turret, DoubleSupplier joy) {
    this.joy = joy;

    addCommands(
      new ConditionalCommand(
        new TurretScan(turret, TurretScan.LEFT), 
        new ConditionalCommand(
          new TurretScan(turret, TurretScan.RIGHT), 
          new InstantCommand(), 
          this::joyRight
        ), 
        this::joyLeft
      )
    );
  }

  public boolean joyLeft() {
    return joy.getAsDouble() < -0.5;
  }

  public boolean joyRight() {
    return joy.getAsDouble() > 0.5;
  } 
}
