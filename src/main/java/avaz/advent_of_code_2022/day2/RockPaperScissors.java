package avaz.advent_of_code_2022.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class RockPaperScissors {
  public static void main(String[] args) throws IOException {
    final Path input = Path.of("src/main/resources/day2/input.txt");
    enum Result {
      WIN("Z", 6), LOSE("X", 0), DRAW("Y", 3);

      private final int score;
      private final String code;

      Result(final String code, final int score) {
        this.code = code;
        this.score = score;
      }

      public static Result findByCode(String code) {
        return Arrays.stream(Result.values()).filter(r -> r.getCode().equals(code)).findFirst().orElseThrow();
      }

      public int getScore() {
        return score;
      }

      public String getCode() {
        return code;
      }

    }
    enum Hand {
      ROCK("A", "X", 1), PAPER("B", "Y", 2), SCISSORS("C", "Z", 3);

      private final int score;
      private final String leftCode;
      private final String rightCode;

      Hand(final String leftCode, final String rightCode, final int score) {
        this.leftCode = leftCode;
        this.rightCode = rightCode;
        this.score = score;
      }

      public static Hand findByCode(String code) {
        return Arrays.stream(Hand.values()).filter(h -> h.getLeftCode().equals(code) || h.getRightCode().equals(code)).findFirst().orElseThrow();
      }

      public static Hand calculateResultStrategically(final Hand other, final Result expectedResult) {
        return switch (other) {
          case ROCK -> switch (expectedResult) {
            case WIN -> PAPER;
            case LOSE -> SCISSORS;
            case DRAW -> ROCK;
          };
          case PAPER -> switch (expectedResult) {
            case WIN -> SCISSORS;
            case LOSE -> ROCK;
            case DRAW -> PAPER;
          };
          case SCISSORS -> switch (expectedResult) {
            case WIN -> ROCK;
            case LOSE -> PAPER;
            case DRAW -> SCISSORS;
          };
        };
      }

      public int getScore() {
        return score;
      }

      public String getLeftCode() {
        return leftCode;
      }

      public Result calculateResultBasic(Hand other) {
        return switch (other) {
          case ROCK -> this == PAPER ? Result.WIN : this == SCISSORS ? Result.LOSE : Result.DRAW;
          case PAPER -> this == SCISSORS ? Result.WIN : this == ROCK ? Result.LOSE : Result.DRAW;
          case SCISSORS -> this == ROCK ? Result.WIN : this == PAPER ? Result.LOSE : Result.DRAW;
        };
      }

      public String getRightCode() {
        return rightCode;
      }
    }
    class Match {
      private final Hand left;
      private final Hand right;

      public Match(Hand left, Hand right) {
        this.left = left;
        this.right = right;
      }

      public Hand getLeft() {
        return left;
      }

      public Hand getRight() {
        return right;
      }

      public Result calculateResult() {
        return right.calculateResultBasic(left);
      }

      public int calculateScore() {
        return calculateResult().getScore() + right.getScore();
      }
    }
    class Strategy {
      private final Hand hand;
      private final Result result;

      public Strategy(Hand hand, Result result) {
        this.hand = hand;
        this.result = result;
      }

      public Hand getHand() {
        return hand;
      }

      public Result getResult() {
        return result;
      }
    }
    // Part One
    try (final Stream<String> lines = Files.lines(input)) {
      lines.map(line -> line.split(" "))
        .map(parts -> new Match(Hand.findByCode(parts[0]), Hand.findByCode(parts[1])))
        .peek(match -> System.out.printf("%s (%s) vs %s (%s): %s (%s)%n", match.getLeft(), match.getLeft().leftCode, match.getRight(), match.getRight().rightCode, match.calculateResult(), match.calculateScore()))
        .map(Match::calculateScore)
        .reduce(Integer::sum)
        .ifPresent(System.out::println);
    }

    // Part Two
    try (final Stream<String> lines = Files.lines(input)) {
      lines.map(line -> line.split(" "))
        .map(parts -> new Strategy(Hand.findByCode(parts[0]), Result.findByCode(parts[1])))
        .map(strategy -> new Match(strategy.getHand(), Hand.calculateResultStrategically(strategy.getHand(), strategy.getResult())))
        .peek(match -> System.out.printf("%s (%s) vs %s (%s): %s (%s)%n", match.getLeft(), match.getLeft().leftCode, match.getRight(), match.getRight().rightCode, match.calculateResult(), match.calculateScore()))
        .map(Match::calculateScore)
        .reduce(Integer::sum)
        .ifPresent(System.out::println);
    }
  }
}
