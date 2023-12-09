import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day2 {

  public static final int redCubes = 12;
  public static final int blueCubes = 14;
  public static final int greenCubes = 13;

  public static List<String> returnNonBlanks(String[] values) {
    return (
      Arrays
        .stream(values)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toList())
    );
  }

  public static int returnIfLegal(String game) {
    List<String> dataSplited = returnNonBlanks(game.split("(Game)|[,:;]+"));
    int id = Integer.parseInt(dataSplited.get(0).trim());
    int quantity;

    for (String hands : dataSplited) {
      if (hands.contains("blue")) {
        quantity = Integer.parseInt(hands.replace("blue", "").trim());
        if (quantity > blueCubes) {
          return 0;
        }
      } else if (hands.contains("red")) {
        quantity = Integer.parseInt(hands.replace("red", "").trim());

        if (quantity > redCubes) {
          return 0;
        }
      } else if (hands.contains("green")) {
        quantity = Integer.parseInt(hands.replace("green", "").trim());
        if (quantity > greenCubes) {
          return 0;
        }
      }
    }

    return id;
  }

  public static int minimumSet(String game) {
    List<String> dataSplited = returnNonBlanks(game.split("(Game)|[,:;]+"));
    int powerSet = 0;
    int quantity;
    int redMin = 0;
    int blueMin = 0;
    int greenMin = 0;

    for (String hands : dataSplited) {
      if (hands.contains("blue")) {
        quantity = Integer.parseInt(hands.replace("blue", "").trim());
        blueMin = Math.max(blueMin, quantity);
      } else if (hands.contains("red")) {
        quantity = Integer.parseInt(hands.replace("red", "").trim());

        redMin = Math.max(redMin, quantity);
      } else if (hands.contains("green")) {
        quantity = Integer.parseInt(hands.replace("green", "").trim());
        greenMin = Math.max(greenMin, quantity);
      }
    }

    powerSet = redMin * blueMin * greenMin;

    return powerSet;
  }

  public static void main(String[] args) {
    String filePath = "Inputs/input2.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      int sum = 0;

      while ((line = reader.readLine()) != null) {
        sum += minimumSet(line);
      }
      System.out.println(sum);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
