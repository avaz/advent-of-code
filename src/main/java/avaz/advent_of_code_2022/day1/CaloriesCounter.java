package avaz.advent_of_code_2022.day1;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CaloriesCounter {

  public static void main(String[] args) {
    final Path input = Path.of("src/main/resources/day1/input.txt");
    try {
      final List<String> lines = Files.readAllLines(input);
      int currentElf = 0;
      List<Integer> calories = new ArrayList<>();
      for (String line : lines) {
        if (!line.isBlank()) {
          currentElf += Integer.parseInt(line);
        }
        else {
          calories.add(currentElf);
          currentElf = 0;
        }
      }
      calories.add(currentElf);
      calories.stream().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).limit(3).forEach(System.out::println);
      final int sum = calories.stream().sorted(Comparator.reverseOrder()).limit(3).mapToInt(i -> i).sum();
      System.out.println(sum);
    }
    catch (Exception e) {
      System.out.println("Error reading file");
    }
  }
}
