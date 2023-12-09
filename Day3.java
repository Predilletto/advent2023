import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day3 {

  public static String checkBothSides(int left, int right, String line) {
    StringBuilder digitValue = new StringBuilder();
    digitValue.insert(0, line.charAt(left));
    digitValue.append(line.charAt(right - 1));
    digitValue.append(line.charAt(right));

    return digitValue.toString();
  }

  public static String checkLeft(int left, String line) {
    StringBuilder digitValue = new StringBuilder();
    while (left >= 0 && (isIntendedChar(line.charAt(left), '1') || left < 0)) {
      digitValue.insert(0, line.charAt(left));
      left--;
      if (left < 0 || !isIntendedChar(line.charAt(left), '1')) {
        break;
      }
    }
    return digitValue.toString();
  }

  public static String checkRight(int right, String line) {
    StringBuilder digitValue = new StringBuilder();

    while (
      right <= line.length() - 1 && (isIntendedChar(line.charAt(right), '1'))
    ) {
      digitValue.append(line.charAt(right));
      right++;
      if (
        right == line.length() || (!isIntendedChar(line.charAt(right), '1'))
      ) {
        break;
      }
    }
    return digitValue.toString();
  }

  public static int checkAllSides(String[] lines, int lineIdx, int idx) {
    List<String> values = new ArrayList<>();
    String line = lines[lineIdx];
    int ratio = 1;
    int left = idx != 0 ? idx - 1 : idx;
    int right = idx != line.length() - 1 ? idx + 1 : idx;

    if (isIntendedChar(line.charAt(left), '1')) {
      values.add(checkLeft(left, line));
    }
    if (isIntendedChar(line.charAt(right), '1')) {
      values.add(checkRight(right, line));
    }
    if (lineIdx > 0) {
      String upperLine = lines[lineIdx - 1];
      char checkingUp = upperLine.charAt(idx);
      if (isIntendedChar(checkingUp, '1')) {
        if (
          isIntendedChar(upperLine.charAt(left), '1') &&
          isIntendedChar(upperLine.charAt(right), '1')
        ) {
          values.add(checkBothSides(left, right, upperLine));
        } else if (isIntendedChar(upperLine.charAt(left), '1')) {
          values.add(checkLeft(left, upperLine) + checkingUp);
        } else if (isIntendedChar(upperLine.charAt(right), '1')) {
          values.add(checkingUp + checkRight(right, upperLine));
        }
      }
      if (
        !isIntendedChar(checkingUp, '1') &&
        isIntendedChar(upperLine.charAt(left), '1')
      ) {
        values.add(checkLeft(left, upperLine));
      }
      if (
        !isIntendedChar(checkingUp, '1') &&
        isIntendedChar(upperLine.charAt(right), '1')
      ) {
        values.add(checkRight(right, upperLine));
      }
    }

    if (lineIdx < lines.length - 1) {
      String lowerLine = lines[lineIdx + 1];
      char checkingDown = lowerLine.charAt(idx);
      if (isIntendedChar(checkingDown, '1')) {
        if (
          isIntendedChar(lowerLine.charAt(left), '1') &&
          isIntendedChar(lowerLine.charAt(right), '1')
        ) {
          values.add(checkBothSides(left, right, lowerLine));
        } else if (isIntendedChar(lowerLine.charAt(left), '1')) {
          values.add(checkLeft(left, lowerLine) + checkingDown);
        } else if (isIntendedChar(lowerLine.charAt(right), '1')) {
          values.add(checkingDown + checkRight(right, lowerLine));
        }
      }
      if (
        !isIntendedChar(checkingDown, '1') &&
        isIntendedChar(lowerLine.charAt(left), '1')
      ) {
        values.add(checkLeft(left, lowerLine));
        System.out.println(
          "lowerLine.charAt(right): " + lowerLine.charAt(left)
        );
        System.out.println(
          "lowerLine.charAt(right): " + lowerLine.charAt(right)
        );
      }
      if (
        !isIntendedChar(checkingDown, '1') &&
        isIntendedChar(lowerLine.charAt(right), '1')
      ) {
        System.out.println("here");

        System.out.println(checkRight(right, lowerLine));
        values.add(checkRight(right, lowerLine));
      }
    }

    System.out.println(values);
    if (values.size() > 1) {
      for (String value : values) {
        ratio *= Integer.parseInt(value.trim());
      }
    }

    return ratio;
  }

  public static int returnNumberIfPass(
    String[] lines,
    String line,
    DigitCounter digit
  ) {
    int number = 0;
    int lineIdx = digit.getLine();
    int stidx = digit.getStidx();
    int endidx = digit.getEndidx();
    int left = stidx != 0 ? stidx - 1 : stidx;
    int right = endidx != line.length() - 1 ? endidx + 1 : endidx;

    if (
      checkHorizontally(lines[lineIdx], left, right, '*') ||
      (
        lineIdx > 0 &&
        (
          checkVertically(lines[lineIdx - 1], stidx, endidx, '*') ||
          checkDiagonals(lines[lineIdx - 1], left, right, '*')
        )
      ) ||
      (
        lineIdx < lines.length - 1 &&
        (
          checkVertically(lines[lineIdx + 1], stidx, endidx, '*') ||
          checkDiagonals(lines[lineIdx + 1], left, right, '*')
        )
      )
    ) {
      return digit.getValue();
    }

    return number;
  }

  public static boolean checkVertically(
    String line,
    int stidx,
    int endidx,
    char option
  ) {
    for (int i = stidx; i <= endidx; i++) {
      if (isIntendedChar(line.charAt(i), option)) {
        return true;
      }
    }

    return false;
  }

  public static boolean checkHorizontally(
    String line,
    int left,
    int right,
    char option
  ) {
    return (
      isIntendedChar(line.charAt(left), option) ||
      isIntendedChar(line.charAt(right), option)
    );
  }

  public static boolean checkDiagonals(
    String line,
    int left,
    int right,
    char option
  ) {
    return checkHorizontally(line, left, right, option);
  }

  public static boolean isIntendedChar(char c, char option) {
    if (option == '*') {
      if (!Character.isLetterOrDigit(c) && c != '.') {
        return true;
      }

      return false;
    } else {
      return Character.isDigit(c);
    }
  }

  public static String[] returnLines(String filePath) throws IOException {
    List<String> text = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = reader.readLine()) != null) {
        text.add(line);
      }
    }

    String[] linesArray = new String[text.size()];
    linesArray = text.toArray(linesArray);

    return linesArray;
  }

  public static void main(String[] args) {
    String filePath = "Inputs/input3.txt";
    try {
      String[] lines = returnLines(filePath);
      List<DigitCounter> digitCounters = new ArrayList<>();
      List<SymPointer> symPointers = new ArrayList<>();
      String valueString = "";
      int value = 0;
      int size = 0;
      int line = 0;
      int stidx = 0;
      int endidx = 0;
      Long ratios = 0L;

      for (int i = 0; i < lines.length; i++) {
        for (int j = 0; j < lines[i].length(); j++) {
          if (Character.isDigit(lines[i].charAt(j))) {
            if (size < 1) {
              stidx = j;
            }

            size++;
            valueString += lines[i].charAt(j);
            if (j == lines[i].length() - 1) {
              endidx = j;
              value = Integer.parseInt(valueString);
              digitCounters.add(
                new DigitCounter(value, i, size, stidx, endidx)
              );
              value = 0;
              valueString = "";
              size = 0;
            }
          } else {
            char sym = lines[i].charAt(j);
            if (sym == '*') {
              int ratio = checkAllSides(lines, i, j);

              if (ratio > 1) {
                ratios += ratio;
              }
            }
            if (size > 0) {
              endidx = j - 1;
              line = i;
              value = Integer.parseInt(valueString);

              digitCounters.add(
                new DigitCounter(value, line, size, stidx, endidx)
              );
            }
            value = 0;
            valueString = "";
            size = 0;
          }
        }
      }

      int sum = 0;

      for (DigitCounter digit : digitCounters) {
        String lineCheck = lines[digit.getLine()];

        sum += returnNumberIfPass(lines, lineCheck, digit);
      }
      System.out.println(ratios);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static class SymPointer {

    private char value;
    private int line;
    private int idx;

    public SymPointer(char value, int line, int idx) {
      this.value = value;
      this.line = line;
      this.idx = idx;
    }

    @Override
    public String toString() {
      return (
        "SymPointer [value=" + value + ", line=" + line + ", idx=" + idx + "]"
      );
    }

    public char getValue() {
      return value;
    }

    public void setValue(char value) {
      this.value = value;
    }

    public int getLine() {
      return line;
    }

    public void setLine(int line) {
      this.line = line;
    }

    public int getIdx() {
      return idx;
    }

    public void setIdx(int idx) {
      this.idx = idx;
    }
  }

  public static class DigitCounter {

    private int value;
    private int line;
    private int size;
    private int stidx;
    private int endidx;

    DigitCounter(int value, int line, int size, int stidx, int endidx) {
      this.value = value;
      this.line = line;
      this.size = size;
      this.stidx = stidx;
      this.endidx = endidx;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = line;
    }

    public int getLine() {
      return line;
    }

    public void setLine(int line) {
      this.line = line;
    }

    public int getSize() {
      return size;
    }

    public void setSize(int size) {
      this.size = size;
    }

    public int getStidx() {
      return stidx;
    }

    public void setStidx(int stidx) {
      this.stidx = stidx;
    }

    public int getEndidx() {
      return endidx;
    }

    public void setEndidx(int endidx) {
      this.endidx = endidx;
    }

    @Override
    public String toString() {
      return (
        "DigitCounter [value=" +
        value +
        ", line=" +
        line +
        ", size=" +
        size +
        ", stidx=" +
        stidx +
        ", endidx=" +
        endidx +
        "]\n"
      );
    }
  }
}
