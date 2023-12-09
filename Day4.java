import AuxFunctions.StringRelated;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 {

  public static Map<Integer, Integer> cards = new HashMap<>();

  public static void populateMap(int lines) {
    for (int i = 1; i <= lines; i++) {
      cards.put(i, 1);
    }
  }

  public static void addCards(int number, String line) {
    int matches = checkMatches(line);
    int card = number;
    int limit = card + matches;
    int value = cards.get(number);

    while (card + 1 <= limit) {
      card++;

      cards.put(card, cards.get(card) + 1 * value);
    }
  }

  public static int checkMatches(String line) {
    int result = 0;
    String[] card = line.split("(\\|)+");
    List<String> winnerSide = StringRelated.returnNonBlanks(
      card[0].split("(Card\\s([0-9]+):|\\s)")
    );
    List<String> checkingNumbers = StringRelated.returnNonBlanks(
      card[1].split("(\\s)")
    );
    Map<String, Integer> winners = new HashMap<>();

    for (String winner : winnerSide) {
      winners.put(winner, 1);
    }

    for (String checking : checkingNumbers) {
      if (winners.containsKey(checking)) {
        result++;
      }
    }

    return result;
  }

  public static int checkWinner(String line) {
    int result = 0;
    String[] card = line.split("(\\|)+");
    List<String> winnerSide = StringRelated.returnNonBlanks(
      card[0].split("(Card\\s([0-9]+):|\\s)")
    );
    List<String> checkingNumbers = StringRelated.returnNonBlanks(
      card[1].split("(\\s)")
    );
    Map<String, Integer> winners = new HashMap<>();

    for (String winner : winnerSide) {
      winners.put(winner, 1);
    }

    for (String checking : checkingNumbers) {
      if (winners.containsKey(checking)) {
        if (result == 0) {
          result = 1;
        } else {
          result *= 2;
        }
      }
    }

    return result;
  }

  public static int returnFileLines(Path filePath) {
    long lineCount = 0L;
    try {
      lineCount = Files.lines(filePath).count();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return (int) lineCount;
  }

  public static void main(String[] args) {
    Long startTime = System.nanoTime();
    String filePath = "Inputs/input4.txt";
    Path fileP = Path.of(filePath);
    int fileLines = returnFileLines(fileP);
    Long sum = 0L;
    int cardNumber = 0;
    populateMap(fileLines);

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;

      while ((line = reader.readLine()) != null) {
        cardNumber++;
        addCards(cardNumber, line);
      }
      int mapSum = cards.values().stream().mapToInt(Integer::intValue).sum();
      System.out.println(mapSum);
      long endTime = System.nanoTime();
      long elapsedTimeInNanos = endTime - startTime;
      long elapsedTimeInMillis = elapsedTimeInNanos / 1_000_000;
      System.out.println(
        "Elapsed Time: " + elapsedTimeInMillis + " milliseconds"
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
