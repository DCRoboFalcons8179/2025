package frc.robot;

import com.pathplanner.lib.auto.PathPlannerAutoBuilder;
import edu.wpi.first.wpilibj2.command.Command;

// BINARY SWITCH LAYOUT
// Bit0: If 0 then CenterStart,   else if 1 then either ProcessorStart or CageStart
// Bit1: If 0 then ProcessorSide, else if 1 then CageSide
// Bit2: If 0 then Basic,         else if 1 then Complex
// Bit3: If 0 then auto routine,  else if 1 then CommandTest
// Bit4: Unused
//
//     43210
//  0: 00000 DoNothing.auto
//  1: 00001 StartProcessorToTrophBasic.auto
//  2: 00010 CenterStartToTrophBasic.auto (duplicate)
//  3: 00011 StartCageToTrophBasic.auto
//  4: 00100 CenterStartToProcessorSideComplex.auto
//  5: 00101 ProcessorStartToProcessorSideComplex.auto
//  6: 00110 CenterStartToCageSideComplex.auto
//  7: 00111 CageStartToCageSideComplex.auto
//  8: 01000 CommandTestResetAll.auto
//  9: 01001 CommandTestScoreTroph.auto
// 10: 01010 CommandTestHumanCoral.auto
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
  public static Command getAuton(int autonID) {

    switch (autonID) {
      case 0:
        return PathPlannerAutoBuilder.buildAuto("DoNothing.auto");
      case 1:
        return PathPlannerAutoBuilder.buildAuto("StartProcessorToTrophBasic.auto");
      case 2:
        return PathPlannerAutoBuilder.buildAuto("CenterStartToTrophBasic.auto");
      case 3:
        return PathPlannerAutoBuilder.buildAuto("StartCageToTrophBasic.auto");
      case 4:
        return PathPlannerAutoBuilder.buildAuto("CenterStartToProcessorSideComplex.auto");
      case 5:
        return PathPlannerAutoBuilder.buildAuto("ProcessorStartToProcessorSideComplex.auto");
      case 6:
        return PathPlannerAutoBuilder.buildAuto("CenterStartToCageSideComplex.auto");
      case 7:
        return PathPlannerAutoBuilder.buildAuto("CageStartToCageSideComplex.auto");
      case 8:
        return PathPlannerAutoBuilder.buildAuto("CommandTestResetAll.auto");
      case 9:
        return PathPlannerAutoBuilder.buildAuto("CommandTestScoreTroph.auto");
      case 10:
        return PathPlannerAutoBuilder.buildAuto("CommandTestHumanCoral.auto");
      default:
        return null;
    }
  }
}
