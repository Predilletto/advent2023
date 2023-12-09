import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {

  private static final Map<String, Character> stringToNumbers = Map.ofEntries(
    new AbstractMap.SimpleEntry<String, Character>("one", '1'),
    new AbstractMap.SimpleEntry<String, Character>("two", '2'),
    new AbstractMap.SimpleEntry<String, Character>("three", '3'),
    new AbstractMap.SimpleEntry<String, Character>("four", '4'),
    new AbstractMap.SimpleEntry<String, Character>("five", '5'),
    new AbstractMap.SimpleEntry<String, Character>("six", '6'),
    new AbstractMap.SimpleEntry<String, Character>("seven", '7'),
    new AbstractMap.SimpleEntry<String, Character>("eight", '8'),
    new AbstractMap.SimpleEntry<String, Character>("nine", '9')
  );

  public static int getDigits(String line) {
    String newLine = line
      .replace("oneight", "oneeight")
      .replace("twone", "twoone")
      .replace("eightwo", "eighttwo");

    List<Character> digits = new ArrayList<>();
    int twoDigit = 0;

    Pattern pattern = Pattern.compile(
      "(one|two|three|four|five|six|seven|eight|nine|[1-9])"
    );
    Matcher matcher = pattern.matcher(newLine);
    while (matcher.find()) {
      char digit;
      if (stringToNumbers.containsKey(matcher.group())) {
        digit = stringToNumbers.get(matcher.group());
      } else {
        digit = matcher.group().charAt(0);
      }
      digits.add(digit);
    }

    String twoDigits = new StringBuilder()
      .append(digits.get(0))
      .append(digits.get(Math.max(digits.size() - 1, 0)))
      .toString();
    twoDigit = Integer.valueOf(twoDigits);

    return twoDigit;
  }

  public static void main(String[] args) {
    String filePath = "Inputs/input1.txt";
    Long sum = 0L;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;

      while ((line = reader.readLine()) != null) {
        int digits = getDigits(line);

      

        sum += digits;
      }
      System.out.println(sum);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
