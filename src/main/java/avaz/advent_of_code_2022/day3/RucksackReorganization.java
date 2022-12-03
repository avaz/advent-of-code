package avaz.advent_of_code_2022.day3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class RucksackReorganization {

  public static void main(String[] args) throws IOException {
    class Rucksack {
      final String value;

      Rucksack(final String value) {
        this.value = value;
      }

      public char findRepeatedCharInTwoHalf() {
        final var firstHalf = value.substring(0, value.length() / 2);
        final var secondHalf = value.substring(value.length() / 2);
        char repeatedChar = ' ';
        boolean found = false;
        int i = 0;
        while (!found && i < firstHalf.length()) {
          final var charToFind = firstHalf.charAt(i);
          if (secondHalf.contains(String.valueOf(charToFind))) {
            repeatedChar = charToFind;
            found = true;
          }
          i++;
        }
        return repeatedChar;
      }

      public boolean containsChar(final char charToFind) {
        return value.contains(String.valueOf(charToFind));
      }
    }

    class RucksackGroup {
      final List<Rucksack> rucksacks;

      RucksackGroup() {
        rucksacks = new ArrayList<>(3);
      }

      public void addRucksack(final Rucksack rucksack) {
        rucksacks.add(rucksack);
      }

      public char findBadge() {
        final String value = rucksacks.get(0).value;
        int i = 0;
        boolean found = false;
        char badge = ' ';
        while (!found && i < value.length()) {
          final var charToFind = value.charAt(i);
          if (rucksacks.get(1).containsChar(charToFind) && rucksacks.get(2).containsChar(charToFind)) {
            badge = charToFind;
          }
          i++;
        }
        return badge;
      }

    }

    Map<String, Integer> priorities = new HashMap<>();
    for (int i = 0; i < 26; i++) {
      priorities.put(String.valueOf((char) (i + 97)), i + 1);
      priorities.put(String.valueOf((char) (i + 65)), i + 27);
    }
    final Path input = Path.of("src/main/resources/day3/input.txt");
    // Part One
    try (final Stream<String> lines = Files.lines(input)) {
      final var result = lines
        .map(Rucksack::new)
        .map(Rucksack::findRepeatedCharInTwoHalf)
        .map(String::valueOf)
        .map(priorities::get)
        .reduce(0, Integer::sum);
      System.out.println(result);
    }

    // Part Two
    final List<String> lines = Files.readAllLines(input);
    final List<RucksackGroup> rucksackGroups = new ArrayList<>();
    RucksackGroup rucksackGroup = new RucksackGroup();
    int i = 0;
    for (final var line : lines.toArray(String[]::new)) {
      rucksackGroup.addRucksack(new Rucksack(line));
      if (i == 2) {
        rucksackGroups.add(rucksackGroup);
        rucksackGroup = new RucksackGroup();
        i = 0;
      }
      else {
        i++;
      }
    }
    final var result = rucksackGroups
      .stream()
      .map(RucksackGroup::findBadge)
      .map(String::valueOf)
      .map(priorities::get)
      .reduce(0, Integer::sum);
    System.out.println(result);
  }
}
