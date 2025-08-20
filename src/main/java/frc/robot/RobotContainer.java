// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.subsystems.drive;
import frc.robot.subsystems.CANdleSystem;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private final drive test_drive = new drive();
  private final CANdleSystem test_candleSystem = new CANdleSystem();

  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    m_driverController.a().onTrue(test_drive.homeworkCommand(10)
        .andThen(test_candleSystem.setAnimateFire())
        .andThen(test_drive.setvelocityCommand(0)));
    m_driverController.b().onTrue(test_drive.homeworkCommand(0)
        .andThen(test_candleSystem.setAnimateLarson())
        .andThen(test_drive.setvelocityCommand(0)));
  }

}