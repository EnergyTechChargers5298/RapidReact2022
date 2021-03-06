// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autoFunctions.AutoIntakeToLoad;
import frc.robot.commands.autoFunctions.AutoShootCargo;
import frc.robot.commands.basic.cargo.IntakeChomp;
import frc.robot.commands.basic.cargo.IntakeRetract;
import frc.robot.commands.basic.shoot.TurretAuto;
import frc.robot.commands.closedloop.TurnToAnglePID;
import frc.robot.commands.trajectory.TrajectoryCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Loader;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoTurnShoot extends SequentialCommandGroup {
  /** Creates a new AutoTurnShoot. */
  public AutoTurnShoot(Drivetrain drivetrain, Intake intake, Loader loader, Feeder feeder, Turret turret, Shooter shooter) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      new IntakeChomp(intake),
      new ParallelRaceGroup(
        new TrajectoryCommand(drivetrain).driveStraight(1.5),
        new AutoIntakeToLoad(intake, loader)),
      new TrajectoryCommand(drivetrain).driveBack(new Pose2d(1, 1, new Rotation2d()), 0.75),
      new TurnToAnglePID(drivetrain, 180),
      new TrajectoryCommand(drivetrain).driveBack(new Pose2d(1, 1, new Rotation2d()), 0.75),
      new TurretAuto(turret),
      new AutoShootCargo(shooter, feeder, loader)
    );
  }
}
