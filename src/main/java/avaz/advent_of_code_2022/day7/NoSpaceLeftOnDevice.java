package avaz.advent_of_code_2022.day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class NoSpaceLeftOnDevice {

  public static class FileSystem {

    enum Command {
      CD("cd"), LS("ls");

      private final String value;

      Command(final String value) {
        this.value = value;
      }

      public static Command findByValue(final String value) {
        return Arrays.stream(Command.values())
          .filter(command -> command.getValue().equals(value))
          .findFirst()
          .orElseThrow(() -> new IllegalArgumentException("No command found for value: " + value));
      }

      public String getValue() {
        return value;
      }
    }

    private final FileSystemItem root;
    private FileSystemItem current;
    private final int maxSize;

    private FileSystem(final int maxSize) {
      this.maxSize = maxSize;
      root = new FileSystemItem("/");
      current = root;
    }

    public void addChild(final FileSystemItem child) {
      current.addChild(child);
    }

    public void changeDir(final String dir) {
      if ("/".equals(dir)) {
        current = root;
      }
      else if ("..".equals(dir)) {
        if (current != root) {
          current = current.parent;
        }
      }
      else {
        current.children.stream().filter(child -> child.name.equals(dir)).findFirst().ifPresent(child -> current = child);
      }
    }

    public FileSystemItem getRoot() {
      return root;
    }

    public int freeSpace() {
      return maxSize - root.getSize();
    }

    public List<FileSystemItem> dirsWouldFreeUpTo(final FileSystemItem dir, final int size) {
      final List<FileSystemItem> result = new ArrayList<>();
      for (final FileSystemItem child : dir.children) {
        if (child.isDirectory()) {
          if (child.getSize() + freeSpace() >= size) {
            result.add(child);
          }
          if (!child.children.isEmpty()) {
            result.addAll(dirsWouldFreeUpTo(child, size));
          }
        }
      }
      return result;
    }

    public List<FileSystemItem> itemsSizeLessThan(final FileSystemItem dir, final int limit) {
      final List<FileSystemItem> result = new ArrayList<>();
      for (final FileSystemItem child : dir.children) {
        if (child.isDirectory()) {
          if(child.getSize() <= limit) {
            result.add(child);
          }
          if (!child.children.isEmpty()) {
            result.addAll(itemsSizeLessThan(child, limit));
          }
        }
      }
      return result;
    }

    public static FileSystem create(final List<String> input, final int maxSize) {
      FileSystem fileSystem = new FileSystem(maxSize);
      for (String line : input) {
        final String[] data = line.split(" ");
        switch (data[0]) {
          case "$" -> {
            final Command command = Command.findByValue(data[1]);
            switch (command) {
              case CD:
                fileSystem.changeDir(data[2]);
                break;
              case LS:
                break;
              default:
                throw new IllegalArgumentException("Unknown command: " + command);
            }
          }
          case "dir" -> fileSystem.addChild(new FileSystemItem(data[1]));
          default -> fileSystem.addChild(new FileSystemItem(data[1], Integer.parseInt(data[0]), false));
        }
      }
      return fileSystem;
    }

  }

  public static class FileSystemItem {
    private String name;
    private int size;
    private boolean isDirectory;
    private FileSystemItem parent;
    private final List<FileSystemItem> children;

    public FileSystemItem() {
      this.children = new ArrayList<>();
    }

    public FileSystemItem(final String name) {
      this();
      this.name = name;
      this.size = 0;
      this.isDirectory = true;
    }

    public FileSystemItem(final String name, final int size, final boolean isDirectory) {
      this();
      this.name = name;
      this.size = size;
      this.isDirectory = isDirectory;
    }

    public int getSize() {
      return isDirectory ? this.children.stream().mapToInt(FileSystemItem::getSize).sum() : this.size;
    }

    public boolean isDirectory() {
      return isDirectory;
    }

    public void addChild(final FileSystemItem child) {
      this.children.add(child);
      child.parent = this;
    }

    @Override
    public boolean equals(final Object o) {
      if (this == o) return true;
      if (!(o instanceof final FileSystemItem that)) return false;
      return getSize() == that.getSize() && isDirectory == that.isDirectory && name.equals(that.name);
    }

    @Override
    public int hashCode() {
      return Objects.hash(name, getSize(), isDirectory);
    }

    @Override
    public String toString() {
      return "FileSystemItem{" +
        "name='" + name + '\'' +
        ", size=" + getSize() +
        ", isDirectory=" + isDirectory +
        '}';
    }
  }

}
