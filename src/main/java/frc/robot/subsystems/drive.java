// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class drive extends SubsystemBase {
  private final TalonFX test_motor1 = new TalonFX(1, "rio");
  private final TalonFX test_motor2 = new TalonFX(2, "rio");
  private final TalonFX test_motor3 = new TalonFX(3, "rio");
  private final TalonFX test_motor4 = new TalonFX(4, "rio");
  private final VoltageOut drive_request = new VoltageOut(0.0);
  private final VoltageOut drive_request2 = new VoltageOut(0.0);
  private final VoltageOut drive_request3 = new VoltageOut(0.0);
  private final VoltageOut drive_request4 = new VoltageOut(0.0);
  public void setVoltage1(double voltage) {
    test_motor1.setControl(drive_request.withOutput(voltage));
    test_motor2.setControl(drive_request2.withOutput(voltage));
  }
  public void setVoltage2(double voltage) {
    test_motor3.setControl(drive_request3.withOutput(voltage));
    test_motor4.setControl(drive_request4.withOutput(voltage));
  }
  public drive() {
    var motorConfigs = new TalonFXConfiguration();

    motorConfigs.Slot0.kS = 0.2;
    motorConfigs.Slot0.kV = 0.0;
    motorConfigs.Slot0.kA = 0;
    motorConfigs.Slot0.kP = 3;
    motorConfigs.Slot0.kI = 0;
    motorConfigs.Slot0.kD = 0;
    motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
    motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
    motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
    motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
    motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

    test_motor1.getConfigurator().apply(motorConfigs);
    test_motor2.getConfigurator().apply(motorConfigs);
    test_motor3.getConfigurator().apply(motorConfigs);
    test_motor4.getConfigurator().apply(motorConfigs);


  }
  public Command motorCommand1(double voltage){
    return runEnd(
      () -> {
        setVoltage1(voltage);
      },
      () -> {
        setVoltage1(0.0);  
      }
    );
  }
  public Command motorCommand2(double voltage){
    return runEnd(
      () -> {
        setVoltage2(voltage);
      },
      () -> {
        setVoltage2(0.0);  
      }
    );
  }
  public Command motorCommand(double voltage){
    return run(()-> {
      setVoltage1(voltage);
      setVoltage2(voltage);
    });
  }
}