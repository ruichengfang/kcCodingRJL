// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.SensorDirectionValue;
import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//基类

public class Drive extends SubsystemBase {

  private final TalonFX m_test_motor = new TalonFX(5, "rio");
  private final TalonFX m_test_motor2 = new TalonFX(6, "rio");
  // private final TalonFX m_test_motor3 = new TalonFX(3, "rio");
  // private final TalonFX m_test_motor4 = new TalonFX(4, "rio");
  private final MotionMagicVoltage m_test_motor_request = new MotionMagicVoltage(0);
  // private final MotionMagicVoltage m_test_motor_request2 = new MotionMagicVoltage(0);
  private final VelocityTorqueCurrentFOC m_test_motor_request2 = new VelocityTorqueCurrentFOC(0);
  // private final VelocityTorqueCurrentFOC m_test_motor_request4 = new VelocityTorqueCurrentFOC(0);

  private final CANcoder cancoder_fl = new CANcoder(3,"rio");

//定义预期位置
double expected_position = 50.0;
double expected_position2 = 0;

//定义现在的位置
double current_position = 0.0;
double current_position2 = 50.0;

double error = 1.0;

//如何判断电机到达位置
//if(电机位置==多少){
//  执行的操作
//}

//区间
//error
//电机位置和实际位置的误差
//误差一定范围，就认为到位了

//实际控制
//封装出来的方法
//控制电机 1.控制电机的位置
//        2.控制电机的速度
//高级的控制方法    本质就是优化速度控制和位置控制

//withPosition()能够将高级的控制请求和底层的位置控制建立联系
//withVelocity()能够将高级的控制请求和底层的速度控制建立联系

  public void setmotorPosition(double pos) {
    m_test_motor.setControl(m_test_motor_request.withPosition(pos));
  }

  public void setmotorVelocity2(double vol) {
    m_test_motor2.setControl(m_test_motor_request2.withVelocity(vol));

  }

public boolean isAtPosition(){
  current_position = m_test_motor.getPosition().getValueAsDouble();
  return Math.abs(expected_position - current_position) <= error;
}
public boolean isAtPosition2(){
  current_position = m_test_motor.getPosition().getValueAsDouble();
  return Math.abs(expected_position2 - current_position) <= error;
}
//andThen()
//until()
//run()
//runOnce()
//runEnd()

//小的command组合成大的command

//whileTrue
//onTrue


  public Command Motor_Position_Command(double Positon, double Velocity){
    return run(()->{
                          setmotorPosition(Positon); 
                          setmotorVelocity2(Velocity); // Set the motor to move at 1000 units per second
                        }).until(()->isAtPosition());
  }

  public Command Motor_Position_Command_end(double Positon, double Velocity){
    return run(()->{
                          setmotorPosition(Positon); 
                          setmotorVelocity2(Velocity); // Set the motor to move at 1000 units per second
                        }).until(()->isAtPosition2());
  }
  public Command Motor_Velocity_Command2(double Velocity){
    return runOnce(()->{
                          setmotorVelocity2(Velocity); // Set the motor to move at 1000 units per second
                       });
        }
  // public Command Motor_Voltage_Command2(double voltage){
  //   return run(()->{
  //     setmotorVoltage2(voltage); // Set the motor to move at 1000 units per second
  //   });
  // }
  /** Creates a new ExampleSubsystem. */
  public Drive() {


      //CANcoder配置
      var motorEncoderConfigs = new CANcoderConfiguration();
      motorEncoderConfigs.MagnetSensor.MagnetOffset=0.0; //offset
      motorEncoderConfigs.MagnetSensor.AbsoluteSensorDiscontinuityPoint=0.5;//实际生活中的电机位置映射的什么范围
      motorEncoderConfigs.MagnetSensor.SensorDirection=SensorDirectionValue.Clockwise_Positive;
 
      cancoder_fl.getConfigurator().apply(motorEncoderConfigs);

      //少了一环：电机和cancoder建立联系

      var motorConfigs = new TalonFXConfiguration();
      motorConfigs.Slot0.kS = 0.14;
      motorConfigs.Slot0.kV = 0.0;
      motorConfigs.Slot0.kA = 0;
      motorConfigs.Slot0.kP = 10;
      motorConfigs.Slot0.kI = 0;
      motorConfigs.Slot0.kD = 0;
      motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
      motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
      motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
      motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
      motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0
     motorConfigs.Feedback.RotorToSensorRatio = 13;

      var motorConfigs2 = new TalonFXConfiguration();
      motorConfigs2.Slot0.kS = 1.85;
      motorConfigs2.Slot0.kV = 0.0;
      motorConfigs2.Slot0.kA = 0;
      motorConfigs2.Slot0.kP = 6;
      motorConfigs2.Slot0.kI = 0;
      motorConfigs2.Slot0.kD = 0.1;
      motorConfigs2.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
      motorConfigs2.MotionMagic.MotionMagicCruiseVelocity = 200; // Unlimited cruise velocity
      motorConfigs2.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
      motorConfigs2.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
      motorConfigs2.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

      //建立电机与cancoder的联系
      motorConfigs.Feedback.FeedbackRemoteSensorID = cancoder_fl.getDeviceID();
      motorConfigs.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.FusedCANcoder;

      m_test_motor.getConfigurator().apply(motorConfigs);
      m_test_motor2.getConfigurator().apply(motorConfigs2);
      // m_test_motor3.getConfigurator().apply(motorConfigs);
      // m_test_motor4.getConfigurator().apply(motorConfigs);


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