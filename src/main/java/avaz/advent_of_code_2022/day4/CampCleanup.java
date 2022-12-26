package avaz.advent_of_code_2022.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CampCleanup {
  public static void main(String[] args) throws IOException {
    class Section {
      final int start;
      final int end;

      Section(final String range) {
        final String[] parts = range.split("-");
        this.start = Integer.parseInt(parts[0]);
        this.end = Integer.parseInt(parts[1]);
      }

      public boolean fullOverlap(final Section section) {
        return this.start <= section.start && this.end >= section.end || section.start <= this.start && section.end >= this.end;
      }

      public boolean partialOverlap(final Section section) {
        return this.start <= section.start && this.end >= section.start || section.start <= this.start && section.end >= this.start;
      }

    }
    class SectionPair {
      final Section first;
      final Section second;

      SectionPair(final String sectionPair) {
        final String[] data = sectionPair.split(",");
        this.first = new Section(data[0]);
        this.second = new Section(data[1]);
      }

      public boolean fullOverlap() {
        return this.first.fullOverlap(second);
      }

      public boolean partialOverlap() {
        return this.first.partialOverlap(second);
      }

    }

    final Path input = Path.of("src/main/resources/day4/input_elena.txt");
    try (final Stream<String> lines = Files.lines(input)) {
      final long count = lines.map(SectionPair::new).filter(SectionPair::fullOverlap).count();
      System.out.println(count);
    }
    try (final Stream<String> lines = Files.lines(input)) {
      final long count = lines.map(SectionPair::new).filter(SectionPair::partialOverlap).count();
      System.out.println(count);
    }
  }


}
