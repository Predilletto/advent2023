import AuxFunctions.StringRelated;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {

  public static Map<Character, Integer> rankValues = new HashMap<>();

  static {
    rankValues.put('A', 14);
    rankValues.put('K', 13);
    rankValues.put('Q', 12);
    rankValues.put('J', 11);
    rankValues.put('T', 10);
    rankValues.put('9', 9);
    rankValues.put('8', 8);
    rankValues.put('7', 7);
    rankValues.put('6', 6);
    rankValues.put('5', 5);
    rankValues.put('4', 4);
    rankValues.put('3', 3);
    rankValues.put('2', 2);
  }

  static class Hand implements Comparable<Hand> {

    private String cards;
    private int value;
    private int strenght;

    public Hand(String cards, int value) {
      this.cards = cards;
      this.value = value;
      this.strenght = getStrenght(cards);
    }

    public String getCards() {
      return cards;
    }

    public void setCards(String cards) {
      this.cards = cards;
    }

    public int getValue() {
      return value;
    }

    public void setValue(int value) {
      this.value = value;
    }

    public int getStrenght() {
      return strenght;
    }

    public void setStrenght(int strenght) {
      this.strenght = strenght;
    }

    @Override
    public String toString() {
      return (
        "Hand [cards=" +
        cards +
        ", value=" +
        value +
        ", strenght=" +
        strenght +
        "]\n"
      );
    }

    private int getStrenght(String cards) {
      Map<Character, Integer> countMapper = new HashMap<>();
      for (int i = 0; i < cards.length(); i++) {
        countMapper.put(
          cards.charAt(i),
          countMapper.getOrDefault(cards.charAt(i), 0) + 1
        );
      }

      int maxCount = countMapper
        .values()
        .stream()
        .max(Integer::compareTo)
        .orElse(0);

      int secondCount = countMapper
        .values()
        .stream()
        .filter(v -> v < maxCount)
        .max(Integer::compareTo)
        .orElse(0);

      if (maxCount == 3 && secondCount == 2) {
        return 5;
      }
      if (maxCount == 2 && secondCount == 2) {
        return 3;
      }

      switch (maxCount) {
        case 5:
          return 7;
        case 4:
          return 6;
        case 3:
          return 4;
        case 2:
          return 2;
        default:
          return 1;
      }
    }

    @Override
    public int compareTo(Day7.Hand arg0) {
      int checkEqual = Integer.compare(this.strenght, arg0.strenght);

      if (checkEqual == 0) {
        for (int i = 0; i < this.cards.length(); i++) {
          char c1 = this.cards.charAt(i);
          char c2 = arg0.getCards().charAt(i);
          if (c1 != c2) {
            return Integer.compare(rankValues.get(c1), rankValues.get(c2));
          }
        }
      }
      return checkEqual;
    }
  }

  public static void main(String[] args) {
    String filePath = "Inputs/input7.txt";
    List<Hand> hands = new ArrayList<>();
    Long sum = 0L;

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;

      while ((line = reader.readLine()) != null) {
        List<String> split = StringRelated.returnNonBlanks(line.split("\\s"));
        hands.add(new Hand(split.get(0), Integer.parseInt(split.get(1))));
      }

      Collections.sort(hands);

      for (int i = 0; i < hands.size(); i++) {
        sum += (i + 1) * hands.get(i).getValue();
      }
      System.out.println(sum);
      System.out.println(hands);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
