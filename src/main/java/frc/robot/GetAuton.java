package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;

// BINARY SWITCH LAYOUT
// Bit0:    If 0 then Processor Side (and first score left on reef),  else if 1 then Cage Side (and first score right on reef)
// Bit1:    If 0 then start at Center,  else if 1 then start at Processor Side OR Cage Side (Bit0)
// Bits2-3: Number of coral to score (00 = No Score, 01 = Single, 10 = Double, 11 = Triple)
// Bit4:    Fidgbit (Unused)
//

/*
    43210
 0: 00000 Do Nothing
 1: 00001 Center No Score
 2: 00010 Processor Side No Score
 3: 00011 Cage Side No Score
 4: 00100 Center Processor Side Score L4 Single
 5: 00101 Center Cage Side Score L4 Single (sort of a duplicate but not)
 6: 00110 Processor Side Score L4 Single
 7: 00111 Cage Side Score L4 Single
 8: 01000 Center Processor Side Score L4 Double
 9: 01001 Center Cage Side Score L4 Double
10: 01010 Processor Side Score L4 Double
11: 01011 Cage Side Score L4 Double
12: 01100 Center Processor Side Score L4 Triple
13: 01101 Center Cage Side Score L4 Triple
14: 01110 Processor Side Score L4 Triple
15: 01111 Cage Side Score L4 Triple
16: 10000 
17: 10001 
18: 10010 
19: 10011 
20: 10100 
21: 10101 
22: 10110 
23: 10111 
24: 11000 
25: 11001 
26: 11010 
27: 11011 
28: 11100 
29: 11101 
30: 11110 
31: 11111 
*/


public class GetAuton {
  public static ArrayList<String> autonList =
      new ArrayList<>(
          Arrays.asList(
              /* 0*/ "Do Nothing",
              /* 1*/ "Center No Score",
              /* 2*/ "Processor Side No Score",
              /* 3*/ "Cage Side No Score",
              /* 4*/ "Center Processor Side Score L4 Single",
              /* 5*/ "Center Cage Side Score L4 Single",
              /* 6*/ "Processor Side Score L4 Single",
              /* 7*/ "Cage Side Score L4 Single",
              /* 8*/ "Center Processor Side Score L4 Double",
              /* 9*/ "Center Cage Side Score L4 Double",
              /*10*/ "Processor Side Score L4 Double",
              /*11*/ "Cage Side Score L4 Double",
              /*12*/ "Center Processor Side Score L4 Triple",
              /*13*/ "Center Cage Side Score L4 Triple",
              /*14*/ "Processor Side Score L4 Triple",
              /*15*/ "Cage Side Score L4 Triple"
              /*16*/ 
              ));

  public static String getAutonName(int index) {
    return index > autonList.size() - 1 ? "Do Nothing" : autonList.get(index);
  }
}
