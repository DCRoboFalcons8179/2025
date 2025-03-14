package frc.robot;

import com.pathplanner.lib.commands.PathPlannerAuto;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.ArrayList;
import java.util.Arrays;

// BINARY SWITCH LAYOUT
// Bit0: If 0 then CenterStart,   else if 1 then either ProcessorStart or CageStart
// Bit1: If 0 then ProcessorSide, else if 1 then CageSide
// Bit2: If 0 then Basic,         else if 1 then Complex
// Bit3: If 0 then auto routine,  else if 1 then CommandTest
// Bit4: Unused
//
//     43210
//  0: 00000 DoNothing
//  1: 00001 StartProcessorToTrophBasic
//  2: 00010 CenterStartToTrophBasic (duplicate)
//  3: 00011 StartCageToTrophBasic
//  4: 00100 CenterStartToProcessorSideComplex
//  5: 00101 ProcessorStartToProcessorSideComplex
//  6: 00110 CenterStartToCageSideComplex
//  7: 00111 CageStartToCageSideComplex
//  8: 01000 CommandTestResetAll
//  9: 01001 CommandTestScoreTroph
// 10: 01010 CommandTestHumanCoral
// 11: 01011
// 12: 01100
// 13: 01101
// 14: 01110
// 15: 01111
// 16: 10000
// 17: 10001
// 18: 10010
// 19: 10011
// 20: 10100
// 21: 10101
// 22: 10110
// 23: 10111
// 24: 11000
// 25: 11001
// 26: 11010
// 27: 11011
// 28: 11100
// 29: 11101
// 30: 11110
// 31: 11111

public class GetAuton {

  public static ArrayList<String> autonList =
      new ArrayList<>(
          Arrays.asList(
              "DoNothing",
              "StartProcessorToTrophBasic",
              "CenterStartToTrophBasic",
              "StartCageToTrophBasic",
              "CenterStartToProcessorSideComplex",
              "ProcessorStartToProcessorSideComplex",
              "CenterStartToCageSideComplex",
              "CageStartToCageSideComplex",
              "CommandTestResetAll",
              "CommandTestScoreTroph",
              "CommandTestHumanCoral"));

  //   {
  //     {
  //       set(0, "DoNothing");
  //       set(1, "StartProcessorToTrophBasic");
  //       set(2, "CenterStartToTrophBasic");
  //       set(3, "StartCageToTrophBasic");
  //       set(4, "CenterStartToProcessorSideComplex");
  //       set(5, "ProcessorStartToProcessorSideComplex");
  //       set(6, "CenterStartToCageSideComplex");
  //       set(7, "CageStartToCageSideComplex");
  //       set(8, "CommandTestResetAll");
  //       set(9, "CommandTestScoreTroph");
  //       set(10, "CommandTestHumanCoral");
  //     }
  //   };

  public static String getAutonName(int index) {
    return index > autonList.size() - 1 ? "DoNothing" : autonList.get(index);
  }

  public static Command getAuton(int autonID) {
    return new PathPlannerAuto(getAuton(autonID));

    // switch (autonID) {
    //   case 0:
    //     return new PathPlannerAuto("DoNothing");
    //   case 1:
    //     return new PathPlannerAuto("StartProcessorToTrophBasic");
    //   case 2:
    //     return new PathPlannerAuto("CenterStartToTrophBasic");
    //   case 3:
    //     return new PathPlannerAuto("StartCageToTrophBasic");
    //   case 4:
    //     return new PathPlannerAuto("CenterStartToProcessorSideComplex");
    //   case 5:
    //     return new PathPlannerAuto("ProcessorStartToProcessorSideComplex");
    //   case 6:
    //     return new PathPlannerAuto("CenterStartToCageSideComplex");
    //   case 7:
    //     return new PathPlannerAuto("CageStartToCageSideComplex");
    //   case 8:
    //     return new PathPlannerAuto("CommandTestResetAll");
    //   case 9:
    //     return new PathPlannerAuto("CommandTestScoreTroph");
    //   case 10:
    //     return new PathPlannerAuto("CommandTestHumanCoral");
    //   default:
    //     return null;
    // }
  }
}
