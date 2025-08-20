// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.signals.StaticFeedforwardSignValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class drive extends SubsystemBase {
  private final TalonFX test_motor1 = new TalonFX(11, "rio");
  //private final TalonFX test_motor2 = new TalonFX(2, "rio");
  // private final TalonFX test_motor3 = new TalonFX(3, "rio");
  // private final TalonFX test_motor4 = new TalonFX(4, "rio");
  private final VoltageOut drive_request = new VoltageOut(0.0);
  private final VoltageOut drive_request2 = new VoltageOut(0.0);
  private final VoltageOut drive_request3 = new VoltageOut(0.0);
  private final VoltageOut drive_request4 = new VoltageOut(0.0);

  
  //private final CANcoder motor_encoder = new CANcoder(4, "rio");
  private final MotionMagicVoltage position_request = new MotionMagicVoltage(0.0).withSlot(0);
  private final VelocityTorqueCurrentFOC velocity_request = new VelocityTorqueCurrentFOC(0.0);

  public void setVoltage1(double voltage) {
    test_motor1.setControl(drive_request.withOutput(voltage));
   // test_motor2.setControl(drive_request2.withOutput(voltage));
  }
  public void setVoltage2(double voltage) {
    // test_motor3.setControl(drive_request3.withOutput(voltage));
    // test_motor4.setControl(drive_request4.withOutput(voltage));
  }
  public void setposition(double position){
    test_motor1.setControl(position_request.withPosition(position));
    //test_motor2.setControl(position_request.withPosition(position));
    //test_motor3.setControl(position_request.withPosition(position));
    //test_motor4.setControl(position_request.withPosition(position));
  }
  public void setvelocity(double velocity){
    test_motor1.setControl(velocity_request.withVelocity(velocity));
    //test_motor2.setControl(velocity_request.withPosition(velocity));
    //test_motor3.setControl(velocity_request.withPosition(velocity));
    //test_motor4.setControl(velocity_request.withPosition(velocity));
  }

  public drive() {
    var motorEncoderConfigs = new CANcoderConfiguration();
    motorEncoderConfigs.MagnetSensor.MagnetOffset=0.0;//offset
    motorEncoderConfigs.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;
    motorEncoderConfigs.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;

    //motor_encoder.getConfigurator().apply(motorEncoderConfigs);

      
    var motorConfigs = new TalonFXConfiguration();

    motorConfigs.Slot0.kS = 0.14;
    motorConfigs.Slot0.kV = 0.0;
    motorConfigs.Slot0.kA = 0;
    motorConfigs.Slot0.kP = 5;
    motorConfigs.Slot0.kI = 0;
    motorConfigs.Slot0.kD = 0.1;

    motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
    motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
    motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
    motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
    motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

    motorConfigs.Slot0.GravityType = GravityTypeValue.Arm_Cosine;
    motorConfigs.Slot0.StaticFeedforwardSign = StaticFeedforwardSignValue.UseClosedLoopSign;

    test_motor1.getConfigurator().apply(motorConfigs);
    // test_motor2.getConfigurator().apply(motorConfigs);
    // test_motor3.getConfigurator().apply(motorConfigs);
    // test_motor4.getConfigurator().apply(motorConfigs);

  
    // motorConfigs.Feedback.FeedbackRemoteSensorID = motor_encoder.getDeviceID();
    // motorConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;
    // motorConfigs.Feedback.RotorToSensorRatio = 13;

    // test_motor1.getConfigurator().apply(motorConfigs);
    // test_motor2.getConfigurator().apply(motorConfigs);
    // test_motor3.getConfigurator().apply(motorConfigs);
    // test_motor4.getConfigurator().apply(motorConfigs);
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
    return runOnce(()-> {
      setVoltage1(voltage);
      setVoltage2(voltage);
    });
  }
  public Command setpositionCommand(double position){
    return runOnce(()-> {
      setposition(position);
    });
  }
  public Command setpositionCommand2(double position){
    return runEnd(() -> {
      setposition(position);
    }, () -> {
      setposition(0.0);
    });
  }
  public Command setvelocityCommand(double velocity){
    return runEnd(() -> {
      setvelocity(velocity);
    }, () -> {
      setvelocity(0.0);
    });
  }
}
/*电机参数调试：
Kg - output to overcome gravity (output)

Ks - Velocity Sign: unused; Closed-Loop Sign: output to overcome static friction (output)

Kv - unused, as there is no target velocity

Ka - unused, as there is no target acceleration

Kp - output per unit of error in position (output/rotation)
  决定了电机的力量大小，并在力量增加过程中开始震动并增大
Ki - output per unit of integrated error in position (output/(rotation*s))
  一般不用
Kd - output per unit of error derivative in position (output/rps)


1. Set all gains to zero.

2. Determine Kg if using an elevator or arm.
  克服重力的参数，kg从0开始，逐渐增加，直到松手时电机不动。
3. Select the appropriate Static Feedforward Sign for your closed-loop type.
  如果是速度控制选usevelositysign,如果是位置控制选closedloopsign
4. Increase ks until just before the motor moves.
  逐步增加ks，直到电机微微有反应。
5. If using velocity setpoints, increase kv until the output velocity closely matches the velocity setpoints.
  若使用速度控制，且需要在以特定速度，逐步增加kv，直到输出速度与设定速度接近。
  （kv是一个放大系数，用于提高速度到目标速度）
6. Increase kp until the output starts to oscillate around the setpoint.
  逐步增加kp，直到当前位置/速度开始在设定点附近振荡。
7. Increase kd as much as possible without introducing jittering to the response.
  逐步增加kd，直到引入新的震动。














 */