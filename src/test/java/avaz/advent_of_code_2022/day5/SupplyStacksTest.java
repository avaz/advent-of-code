package avaz.advent_of_code_2022.day5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SupplyStacksTest {

  @ParameterizedTest
  @CsvSource({
    "src/main/resources/day5/input_example.txt,CMZ",
    "src/main/resources/day5/input.txt,FWNSHLDNZ",
  })
  void testReorganize9000(String input, String expected) throws IOException {
    final Path path = Path.of(input);
    final SupplyStacks supplyStacks = new SupplyStacks(path);
    final String result = supplyStacks.reorganize(new SupplyStacks.GiantCargoCrane9000());
    assertEquals(expected, result);
  }

  @ParameterizedTest
  @CsvSource({
    "src/main/resources/day5/input_example.txt,MCD",
    "src/main/resources/day5/input.txt,RNRGDNFQG",
  })
  void testReorganize9001(String input, String expected) throws IOException {
    final Path path = Path.of(input);
    final SupplyStacks supplyStacks = new SupplyStacks(path);
    final String result = supplyStacks.reorganize(new SupplyStacks.GiantCargoCrane9001());
    assertEquals(expected, result);
  }


}