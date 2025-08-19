// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.VelocityTorqueCurrentFOC;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

//基类：基础的类型
//构造函数来初始化子系统 读取电机固定参数

public class drive extends SubsystemBase {

  // 控制
  //父子类
  private final TalonFX m_test_motor1 = new TalonFX(11, "rio");
  // private final TalonFX m_test_motor2 = new TalonFX(2, "rio");

  private final TalonFX m_test_motor3 = new TalonFX(3, "rio");
  private final TalonFX m_test_motor4 = new TalonFX(4, "rio");
  //控制请求
  private final VelocityTorqueCurrentFOC m_test_motor_request = new VelocityTorqueCurrentFOC(0.0);

  //实际控制
  //封装出来的方法
  //控制电机 1. 位置
  //        2. 速度

  //newPosition能够将高级的控制请求和
  public void setmotorBVelocity(double Velocity) {
    m_test_motor1.setControl(m_test_motor_request.withVelocity(Velocity));
    // m_test_motor2.setControl(m_test_motor_request.withPosition(position));
  }

  public void setmotorAVelocity(double Velocity) {
    m_test_motor3.setControl(m_test_motor_request.withVelocity(Velocity));
    m_test_motor4.setControl(m_test_motor_request.withVelocity(Velocity));
  }

  //控制电压的command
  public Command motorB_Velocity_Command(double Velocity){
    return run(()->{
      setmotorBVelocity(Velocity); // Set the motor to move at 1000 units per second
    });
  }
  public Command motorA_Velocity_Command(double Velocity){
    return run(()->{
      setmotorAVelocity(Velocity); // Set the motor to move at 1000 units per second
    });
  }

  public Command motor_Velocity_Command(double Velocity){
    return runEnd(()->{
      setmotorBVelocity(Velocity);
      },
      ()->{
        setmotorBVelocity(0);
      });
  }
  

  /** Creates a new ExampleSubsystem. */
  public drive() {
 var motorConfigs = new TalonFXConfiguration();

      //每个电机都需要的固定参数
      motorConfigs.Slot0.kS = 1.5; //v10
      motorConfigs.Slot0.kV = 0.0;
      motorConfigs.Slot0.kA = 0;
      motorConfigs.Slot0.kP = 7;
      motorConfigs.Slot0.kI = 0;
      motorConfigs.Slot0.kD = 0.1;

      //涉及到高级控制方法才用到的参数
      motorConfigs.MotionMagic.MotionMagicAcceleration = 100; // Acceleration is around 40 rps/s
      motorConfigs.MotionMagic.MotionMagicCruiseVelocity = 100; // Unlimited cruise velocity
      motorConfigs.MotionMagic.MotionMagicExpo_kV = 0.12; // kV is around 0.12 V/rps
      motorConfigs.MotionMagic.MotionMagicExpo_kA = 0.1; // Use a slower kA of 0.1 V/(rps/s)
      motorConfigs.MotionMagic.MotionMagicJerk = 0; // Jerk is around 0

      m_test_motor1.getConfigurator().apply(motorConfigs);
      //m_test_motor2.getConfigurator().apply(motorConfigs);
      m_test_motor3.getConfigurator().apply(motorConfigs);
      m_test_motor4.getConfigurator().apply(motorConfigs);
  }


}

//motorConfigs控制多个数据 下一级 slot0 一个槽 下一级 kS是真正的电机参数并赋予了参数
//假设电机最高12v 1000转/分钟
//原理是6v 转速就变成500转/分钟
//电机的控制是通过电压来控制的
//但这种方法是不准确的（开环）因为有可能在450-550之间徘徊

//电机的控制有两种：开环和闭环
//闭环，一个闭合的系统 相对准确，当他不准确是自己也知道
//知道自己当前状态，我们给他预期的状态和当前的差距
//所以电机本身可以来调整自己（only for 闭环）因为会一直告诉他真正准确的数据用于调整

//闭环有反馈 开环没有，所以闭环会不断调整自己来达到预期的状态

//想控制参数但有可能不稳定 怎么办
//位置控制到100可能也在90-100之间徘徊，不稳定
//而且电机反复的小波动，碰撞也会损坏电机



//核心：拆分，把复杂的东西变简单

//subsystem：

//控制机器人
//把所有部分的控制全部写在一起

//子系统 主系统
//主系统 = 整个机器人 + 子系统

//地盘，升降，发射 = 子系统
//控制时把小的部分组合在一起就能控制机器人

//command：
//命令，指令：告诉机器人执行什么人物

//封装好了自己的方法
//command调用写好的方法实现机器人的控制

//简单的command完全类似于一个封装的结构
//command把东西都放进来然后直接运用到程序里 类似于俄罗斯套娃
//复杂的command = 多个简单的command组合起来

//frc变成不同于其他编程的写法
//i. 其他正常的循环 = for，while
//frc里面没有显示的for，while
//frc用的是periodic() 默认循环20毫秒/次
//for，while运行的时间不一定完全一样 受限于电脑和配置的当前运行情况
//periodic() = 20毫秒/次
//frc的控制是基于时间片段的
//. = 往下一级



//电机参数的调试方法：
//1. Set all gains to zero.

//2. Determine Kg if using an elevator or arm.
//克服重力的参数，kg从0开始逐渐增加，直到松手电梯能够大概稳定在当前位置不会下坠

//3. Select the appropriate Static Feedforward Sign for your closed-loop type.
//如果是速度控制就用velocitysign，位置控制就用closedloopsign

//4. Increase Ks until just before the motor moves.
//逐步增加 Ks 直到电机微微有反应 处在动和不动的临界点 （克服摩擦力）

//5. If using velocity setpoints, increase Kv until the output velocity closely matches the velocity setpoints.
//如果你使用速度控制，并且需要设定速度到某个值，那你就可以逐渐增加Kv知道速度达到了你的设定值
//当我的速度不够的时候 我就可以用Kv来提高速度到预期值

// Increase Kp until the output starts to oscillate around the setpoint.
//逐步提高kp值直到当前的位置（设定的速度）开始在设定的位置（设定速度）附近震动
//取kp的前一个值让他震不起来

// Increase Kd as much as possible without introducing jittering to the response.
//逐步增加 Kd 直到引入了新的震动
//取kp的前一个值让他震不起来
//通俗理解：Kp决定了电机的力量大还是小（e.g. 当你用力握拳手会颤抖）
//所以Kp要尽可能地大但不能震颤

//一般更Kp重要 Kd用的少