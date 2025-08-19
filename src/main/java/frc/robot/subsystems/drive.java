// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//基类

public class Drive extends SubsystemBase {

  private final TalonFX m_test_motor = new TalonFX(1, "rio");
  private final TalonFX m_test_motor2 = new TalonFX(2, "rio");
  private final TalonFX m_test_motor3 = new TalonFX(3, "rio");
  private final TalonFX m_test_motor4 = new TalonFX(4, "rio");
  private final VoltageOut m_test_motor_request = new VoltageOut(0.0);

//实际控制
//封装出来的方法
//控制电机 1.控制电机的位置
//        2.控制电机的速度
//高级的控制方法    本质就是优化速度控制和位置控制

//withPosition()能够将高级的控制请求和底层的位置控制建立联系
//withVelocity()能够将高级的控制请求和底层的速度控制建立联系

  public void setmotorVoltage(double vol) {
    m_test_motor.setControl(m_test_motor_request.withOutput(vol));
    m_test_motor2.setControl(m_test_motor_request.withOutput(vol));
  }

  public void setmotorVoltage2(double vol) {
    m_test_motor3.setControl(m_test_motor_request.withOutput(vol));
    m_test_motor4.setControl(m_test_motor_request.withOutput(vol));
  }

  public Command Motor_Voltage_Command(double voltage){
    return run(()->{
      setmotorVoltage(voltage); // Set the motor to move at 1000 units per second
    });
  }
  public Command Motor_Voltage_Command2(double voltage){
    return runEnd( ()->{
                          setmotorVoltage2(voltage); // Set the motor to move at 1000 units per second
                       },
                  ()->{
                          setmotorVoltage2(0); // Set the motor to move at 1000 units per second
                      });
        }
  // public Command Motor_Voltage_Command2(double voltage){
  //   return run(()->{
  //     setmotorVoltage2(voltage); // Set the motor to move at 1000 units per second
  //   });
  // }
  /** Creates a new ExampleSubsystem. */
  public Drive() {
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

      m_test_motor.getConfigurator().apply(motorConfigs);
      m_test_motor2.getConfigurator().apply(motorConfigs);
      m_test_motor3.getConfigurator().apply(motorConfigs);
      m_test_motor4.getConfigurator().apply(motorConfigs);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}

//拆分：把复杂的问题简单化


//subsystem：
//控制机器人

//子系统：主系统（整个机器人）-> 子系统（机器人的某个部分）

// 把机器人各个部分拆分开
// 地盘，抬升结构，发射结构

//把小的部分联合起来，就能实现整个机器人的控制


//command：
//命令，指令：告诉机器人执行什么动作
//能够把我们的代码转换成机器人的实际动作

//封装好了自己的方法
//command里面调用自己写好的方法，实现机器人的控制

//简单的command的写法完全类似于一个方法的封装
//复杂的command：多个简单的command组合起来


//FRC不同于其他编程的一些特点

//1.其他情况先的编程，for/while
//但是在FRC里面，没有显示的for和while，每一次运行时间不一定完全一样，受限于电脑当时的情况

//periodic():每隔20毫秒轮询一次
//periodic():检查仪一下的视觉检测结果，那我就可以在periodic里面写一个for或者while循环
//来遍历我的检测结果

//每时每刻都在运动，能不能运行一次就结束？  X
//保持机器人时刻都在运动，所以periodic()