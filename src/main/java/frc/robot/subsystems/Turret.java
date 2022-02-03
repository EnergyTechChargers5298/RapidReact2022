// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Turret extends SubsystemBase {

  // Creates a motor that rotates the turret
  private CANSparkMax turretMotor = new CANSparkMax(Constants.TURRET_MOTOR_PORT, MotorType.kBrushless);

  /** Creates a new Turret. */
  public Turret() {
    // Controls the inversion so that the right is always positive
    turretMotor.setInverted(Constants.TURRET_INVERSION);
  }

  /**
   * Moves the motor that rotates the turret left
   * @author Niko
   */
  public void moveTurretLeft() {
    turretMotor.set(-Constants.TURRET_SPEED);
  }

  /**
   * Moves the motor that rotates the turret right
   * @author Niko
   */
  public void moveTurretRight() {
    turretMotor.set(Constants.TURRET_SPEED);
  }

  /**
   * Stops the turret motor
   * @author Niko
   */
  public void stopTurret() {
    turretMotor.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
