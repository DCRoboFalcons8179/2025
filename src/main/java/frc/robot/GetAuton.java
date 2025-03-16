package frc.robot;

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

public class GetAuton {}
