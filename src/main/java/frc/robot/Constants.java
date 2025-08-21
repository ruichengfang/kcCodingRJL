// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class CANdleConstants {
    public static final int kCANdleID = 1;
    public static final int kLength = 60; //灯条长度
  }

  public static class drive {
    public static final int motor1ID = 5;
    public static final int motor2ID = 5;
    public static final int motor3ID = 5;
    public static final int motor4ID = 5;

    public static final int motor1CANCoderID = 3;
    
  }

  public static class CANdleSystem {
    public static final int candle1ID = 5;
  }
}

