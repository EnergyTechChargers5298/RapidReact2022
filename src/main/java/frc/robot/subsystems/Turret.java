// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Shooters;
import frc.robot.subsystems.LEDStrip.LightFlag;
import frc.robot.utils.Limelight;
import frc.robot.utils.State.TurretState;


public class Turret extends SubsystemBase {

  // declares a motor that rotates the turret
  private CANSparkMax motorTurret;
  
  // state of turret
  private TurretState currentStatus;
  
  // declares encoder & limit switches
  private RelativeEncoder encoderTurret;
  private DigitalInput limitSwitchLeft;
  private DigitalInput limitSwitchRight;

  private boolean manual;
  
  
  /** Creates a new Turret. */
  public Turret() {
    // creates motor
    motorTurret = new CANSparkMax(Shooters.TURRET_MOTOR_PORT, MotorType.kBrushless);

    // controls the inversion so that the right is always positive
    motorTurret.setInverted(Shooters.TURRET_INVERSION);

    // creates the encoder for turret
    encoderTurret = motorTurret.getEncoder();
    
    // limit switch limits
    limitSwitchLeft = new DigitalInput(Shooters.TURRET_LEFT_LIMIT_PORT);
    limitSwitchRight = new DigitalInput(Shooters.TURRET_RIGHT_LIMIT_PORT);

    currentStatus = TurretState.OFF;
    manual = true;

    encoderTurret.setPosition(0.0);

    // get entry from network table
    NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry("Left Turret Limit Enabled").setBoolean(true);
    NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry("Right Turret Limit Enabled").setBoolean(true);
  }

  public void moveTurret(double speed){
    motorTurret.set(speed);
  }
  
  public void stopTurret() {
    motorTurret.set(0);
  }

  // right and left have been inverted in the telemetry the entire time so we did it like this to save the hassle 
  public boolean leftLimit() {
    // if(NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry("Right Turret Limit Enabled").getBoolean(false)) {
    //   return limitSwitchLeft.get();
    // }
    //  else {
    //    return false;
    //  } 
    return false;
  }

  public boolean rightLimit() {
    // if(NetworkTableInstance.getDefault().getTable("SmartDashboard").getEntry("Left Turret Limit Enabled").getBoolean(false)) {
    //   return limitSwitchRight.get();
    // }
    //   else {
    //     return false;
    //   }
    return false;
  }

  public double getTurretPosition() {
    return Shooters.TURRET_ENCODER_MULTIPLIER * encoderTurret.getPosition();
  }

  public void setState(TurretState state) {
    currentStatus = state;
  }

  public void setLights() {
    if(currentStatus != TurretState.OFF) {
      LEDStrip.request(LightFlag.LIMELIGHT, currentStatus.getStatusLight());
    }
  }

  public void setManual(boolean man) {
    manual = man;
  }

  public boolean getManual() {
    return manual;
  }

  public double getTurretDegrees() {
    return getTurretPosition() / 1.148;
  }

  public void updateTelemetry() {
    Limelight.updateTelemetry();
    SmartDashboard.putBoolean("Right Turret Limit", leftLimit());
    SmartDashboard.putBoolean("Left Turret Limit", rightLimit());
    SmartDashboard.putNumber("Turret Position", getTurretPosition());
    SmartDashboard.putNumber("Turret Degrees", getTurretDegrees());
    SmartDashboard.putBoolean("Turret Manual", getManual());
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateTelemetry();
  }
}
