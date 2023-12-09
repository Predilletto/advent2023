import AuxFunctions.StringRelated;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day6 {

  public static void main(String[] args) {
    String filePath = "Inputs/input6.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String line;
      List<List<String>> data = new ArrayList<>();

      while ((line = reader.readLine()) != null) {
        data.add(
          StringRelated.returnNonBlanks(line.split("(Time:|Distance:|\\s)"))
        );
      }
      Long sum = 1L;

      for (int i = 0; i < data.get(0).size(); i++) {
        Long speed = 1L;
        Long count = 0L;
        Long time = Long.parseLong(data.get(0).get(i));
        Long distance = Long.parseLong(data.get(1).get(i));

        System.out.println(distance);

        while (speed < time) {
          if (speed * (time - speed) > distance) {
            // System.out.println(speed * (time - speed) + " " + speed);

            count++;
          }
          speed++;
        }
        System.out.println(count);
        sum *= count;
        count = 0L;
        speed = 1L;
      }

      System.out.println(sum);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
