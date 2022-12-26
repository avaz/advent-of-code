package avaz.advent_of_code_2022.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SupplyStacks {

  private final Path input;

  public SupplyStacks(final Path input) {
    this.input = input;
  }

  public String reorganize(final GiantCargoCrane crane) throws IOException {
    final List<String> strings = Files.readAllLines(input);
    int j = 0;
    String s = strings.get(j);
    while (!s.contains("1") && j++ < strings.size()) {
      String[] label = s.split(" ");
      int a = 0;
      int stackIndex;
      for (int i = 0; i < label.length; i++) {
        stackIndex = (int) Math.max(0, Math.ceil((double) (i + a) / 4));
        if (!label[i].equals("")) {
          a += 3;
          crane.addCrate(new Crate(label[i]), stackIndex);
        }
      }
      s = strings.get(j);
    }
    for (int i = j + 2; i < strings.size(); i++) {
      String[] move = strings.get(i).split(" ");
      crane.move(Integer.parseInt(move[1]), Integer.parseInt(move[3]) - 1, Integer.parseInt(move[5]) - 1);
    }
    return crane.getTopLabels();
  }

  record Crate(String label) {

    @Override
    public String label() {
      return label.charAt(1) + "";
    }

    @Override
    public String toString() {
      return label();
    }
  }

  static abstract class GiantCargoCrane {
    protected final List<Deque<Crate>> stacks;

    GiantCargoCrane() {
      stacks = new ArrayList<>();
    }

    public void addCrate(final Crate crate, int stackIndex) {
      while (stackIndex + 1 > stacks.size()) {
        stacks.add(new LinkedList<>());
      }
      stacks.get(stackIndex).offer(crate);
    }

    public abstract void move(int quantity, int from, int to);

    public String getTopLabels() {
      final StringBuilder sb = new StringBuilder();
      for (Deque<Crate> stack : stacks) {
        if (stack.isEmpty()) {
          sb.append("_");
        }
        else {
          sb.append(stack.peek().label());
        }
      }
      return sb.toString();
    }
  }

  static class GiantCargoCrane9000 extends GiantCargoCrane {

    public void move(int quantity, int from, int to) {
      final Deque<Crate> fromStack = stacks.get(from);
      final Deque<Crate> toStack = stacks.get(to);
      for (int i = 0; i < quantity; i++) {
        toStack.push(fromStack.pop());
      }
    }

  }

  static class GiantCargoCrane9001 extends GiantCargoCrane {

    public void move(int quantity, int from, int to) {
      final Deque<Crate> fromStack = stacks.get(from);
      final Deque<Crate> toStack = stacks.get(to);
      Stack<Crate> temp = new Stack<>();
      for (int i = 0; i < quantity; i++) {
        temp.push(fromStack.pop());
      }
      while (!temp.isEmpty()) {
        toStack.push(temp.pop());
      }
    }

  }

}
