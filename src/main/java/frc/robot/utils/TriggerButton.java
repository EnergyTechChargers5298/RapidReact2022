// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Button;


public class TriggerButton extends Button {

    private XboxController controller;
    private String side;
    public static final String Left = "LEFT";
    public static final String Right = "RIGHT";
    
    /**
     * Turns trigger into button
     * @author Cat Ears and Tahlei
     * @param controller
     * @param hand
     */
    public TriggerButton(XboxController controller, String side){

        this.controller = controller;
        this.side = side;
        //this.isLeft = isLeft;
    }

    @Override
    public boolean get(){
        // After press 0.5 on trigger value is true
       if(side.equals(Left)){
        return controller.getLeftTriggerAxis() >= 0.5;
       }
       else if (side.equals(Right)){
        return controller.getRightTriggerAxis() >= 0.5;
       }
       return false;
    }
}
