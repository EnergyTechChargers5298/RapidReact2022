// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utility;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants.Dim;
import frc.robot.Constants.Shooters;

/** Add your docs here. */
public class Limelight {
    private NetworkTable table;

    public Limelight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }

    //Gets the number of valid targets (0 or 1)
    public boolean isValidTarget() {
        return table.getEntry("tv").getBoolean(false);
    }

    //Gets the horizontal offset of the center crosshair from the target
    public double getHorizontalOffset() {
        return table.getEntry("tx").getDouble(Integer.MAX_VALUE);
    }

    //Gets the vertical offset of the center crosshair from the target
    public double getVerticalOffset() {
        return table.getEntry("ty").getDouble(Integer.MAX_VALUE);
    }

    //Gets the estimated distance from the robot to the target
    public double getEstimatedDistance() {
        return (Dim.GOAL_HEIGHT_INCH - Dim.ROBOT_HEIGHT_INCH) / Math.tan(Units.degreesToRadians(Shooters.LIMELIGHT_ANG_DEG) + Units.degreesToRadians(getVerticalOffset()));
    }

    //Gets the active pipeline (0 to 9)
    public int getPipe() {
        return table.getEntry("getpipe").getNumber(-1).intValue();
    }

    //Sets the active pipeline (0 to 9)
    public void setPipe(int pipeNum) {
        table.getEntry("pipeline").setNumber(pipeNum);
    }

    //Sets the LED state. LEDs on = 3, LEDs off = 1
    public void led(boolean on) {
        if(on) {
            table.getEntry("ledMode").setNumber(3);
        }

        else {
            table.getEntry("ledMode").setNumber(1);
        }
    }

    //Set's the limelight's state of operation. Visiom Processing = 0, Driver Camera = 1
    public void setCamera(boolean vision) {
        if(vision) {
            table.getEntry("camMode").setNumber(0);
        }

        else {
            table.getEntry("camMode").setNumber(1);
        } 
    }
}