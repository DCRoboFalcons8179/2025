package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;

// BINARY SWITCH LAYOUT
// Bit0:    If 0 then Processor Side (and first score left on reef),  else if 1 then Cage Side (and
// first score right on reef)
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

/**
 * Table for Autons to Run
 *
 * <p><b>Bit Encoding:</b> (Bit 43210)
 *
 * <table border="1">
 *   <tr><th>Value</th><th>Bit Encoding</th><th>Auton Name</th></tr>
 *   <tr><td>0</td><td>00000</td><td>Do Nothing</td></tr>
 *   <tr><td>1</td><td>00001</td><td>Center No Score</td></tr>
 *   <tr><td>2</td><td>00010</td><td>Processor Side No Score</td></tr>
 *   <tr><td>3</td><td>00011</td><td>Cage Side No Score</td></tr>
 *   <tr><td>4</td><td>00100</td><td>Center Processor Side Score L4 Single</td></tr>
 *   <tr><td>5</td><td>00101</td><td>Center Cage Side Score L4 Single</td></tr>
 *   <tr><td>6</td><td>00110</td><td>Processor Side Score L4 Single</td></tr>
 *   <tr><td>7</td><td>00111</td><td>Cage Side Score L4 Single</td></tr>
 *   <tr><td>8</td><td>01000</td><td>Center Processor Side Score L4 Double</td></tr>
 *   <tr><td>9</td><td>01001</td><td>Center Cage Side Score L4 Double</td></tr>
 *   <tr><td>10</td><td>01010</td><td>Processor Side Score L4 Double</td></tr>
 *   <tr><td>11</td><td>01011</td><td>Cage Side Score L4 Double</td></tr>
 *   <tr><td>12</td><td>01100</td><td>Center Processor Side Score L4 Triple</td></tr>
 *   <tr><td>13</td><td>01101</td><td>Center Cage Side Score L4 Triple</td></tr>
 *   <tr><td>14</td><td>01110</td><td>Processor Side Score L4 Triple</td></tr>
 *   <tr><td>15</td><td>01111</td><td>Cage Side Score L4 Triple</td></tr>
 *   <tr><td>16</td><td>10000</td><td>---</td></tr>
 *   <tr><td>17</td><td>10001</td><td>---</td></tr>
 *   <tr><td>18</td><td>10010</td><td>---</td></tr>
 *   <tr><td>19</td><td>10011</td><td>---</td></tr>
 *   <tr><td>20</td><td>10100</td><td>---</td></tr>
 *   <tr><td>21</td><td>10101</td><td>---</td></tr>
 *   <tr><td>22</td><td>10110</td><td>---</td></tr>
 *   <tr><td>23</td><td>10111</td><td>---</td></tr>
 *   <tr><td>24</td><td>11000</td><td>---</td></tr>
 *   <tr><td>25</td><td>11001</td><td>---</td></tr>
 *   <tr><td>26</td><td>11010</td><td>---</td></tr>
 *   <tr><td>27</td><td>11011</td><td>---</td></tr>
 *   <tr><td>28</td><td>11100</td><td>---</td></tr>
 *   <tr><td>29</td><td>11101</td><td>---</td></tr>
 *   <tr><td>30</td><td>11110</td><td>---</td></tr>
 *   <tr><td>31</td><td>11111</td><td>---</td></tr>
 * </table>
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
