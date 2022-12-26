package avaz.advent_of_code_2022.day6;

import java.util.ArrayList;
import java.util.List;

public class TuningTrouble {

  public int findMarkerMessage(final String input) {
    return findMarker(input, 14);
  }

  public int findMarkerPacket(final String input) {
    return findMarker(input, 4);
  }

  public int findMarker(final String input, final int packetSize) {
    int marker = 0;
    boolean found = false;
    int i = 0;
    final List<String> seen = new ArrayList<>();
    while (!found && i < input.length()) {
      final String v = input.substring(i, i + 1);
      if (seen.contains(v)) {
        i = i - (seen.size() - seen.indexOf(v));
        seen.clear();
      }
      else {
        seen.add(v);
      }
      if (seen.size() == packetSize) {
        marker = i + 1;
        found = true;
      }
      i++;
    }
    return marker;
  }
}
