package avaz.advent_of_code_2022.day6;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TuningTroubleTest {

  @ParameterizedTest
  @CsvSource({
    "src/main/resources/day6/input_example1.txt,7",
    "src/main/resources/day6/input_example2.txt,5",
    "src/main/resources/day6/input_example3.txt,6",
    "src/main/resources/day6/input_example4.txt,10",
    "src/main/resources/day6/input_example5.txt,11",
    "src/main/resources/day6/input.txt,1833",
  })
  void findMarkerPacket(String input, int expected) throws IOException {
    final String s = Files.readString(Path.of(input));
    final TuningTrouble tuningTrouble = new TuningTrouble();
    final int marker = tuningTrouble.findMarkerPacket(s);
    assertEquals(expected, marker);
  }

  @ParameterizedTest
  @CsvSource({
    "src/main/resources/day6/input_example1.txt,19",
    "src/main/resources/day6/input_example2.txt,23",
    "src/main/resources/day6/input_example3.txt,23",
    "src/main/resources/day6/input_example4.txt,29",
    "src/main/resources/day6/input_example5.txt,26",
    "src/main/resources/day6/input.txt,3425",
  })
  void findMarkerMessage(String input, int expected) throws IOException {
    final String s = Files.readString(Path.of(input));
    final TuningTrouble tuningTrouble = new TuningTrouble();
    final int marker = tuningTrouble.findMarkerMessage(s);
    assertEquals(expected, marker);
  }
}