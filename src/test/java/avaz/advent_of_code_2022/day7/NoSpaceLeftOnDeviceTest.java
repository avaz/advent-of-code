package avaz.advent_of_code_2022.day7;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class NoSpaceLeftOnDeviceTest {

  @ParameterizedTest
  @CsvSource({
    "src/main/resources/day7/input_example.txt,95437",
    "src/main/resources/day7/input.txt,2104783",
  })
  void findDirectoriesSmallerThan(String input, int expected) throws IOException {
    final List<String> lines = Files.readAllLines(Path.of(input));
    final NoSpaceLeftOnDevice.FileSystem fileSystem = NoSpaceLeftOnDevice.FileSystem.create(lines, 70000000);
    final List<NoSpaceLeftOnDevice.FileSystemItem> items = fileSystem.itemsSizeLessThan(fileSystem.getRoot(), 100000);
    assertEquals(expected, items.stream().mapToInt(NoSpaceLeftOnDevice.FileSystemItem::getSize).sum());
  }

  @ParameterizedTest
  @CsvSource({
    "src/main/resources/day7/input_example.txt,24933642",
    "src/main/resources/day7/input.txt,5883165",
  })
  void testFreeUpEnoughSpace(String input, int expected) throws IOException {
    final List<String> lines = Files.readAllLines(Path.of(input));
    final NoSpaceLeftOnDevice.FileSystem fileSystem = NoSpaceLeftOnDevice.FileSystem.create(lines, 70000000);
    final List<NoSpaceLeftOnDevice.FileSystemItem> items = fileSystem.dirsWouldFreeUpTo(fileSystem.getRoot(), 30000000);
    final Optional<NoSpaceLeftOnDevice.FileSystemItem> item = items.stream().min(Comparator.comparingInt(NoSpaceLeftOnDevice.FileSystemItem::getSize));
    assertFalse(item.isEmpty());
    assertEquals(expected, item.get().getSize());
  }
}