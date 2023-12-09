import AuxFunctions.StringRelated;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Day5 {

  public static Map<Long, Long> seedsToSoil = new HashMap<>();
  public static Map<Long, Long> soilsToFert = new HashMap<>();
  public static Map<Long, Long> fertsToWater = new HashMap<>();
  public static Map<Long, Long> watersToLight = new HashMap<>();
  public static Map<Long, Long> lightsToTemp = new HashMap<>();
  public static Map<Long, Long> tempsToHumi = new HashMap<>();
  public static Map<Long, Long> humisToLocation = new HashMap<>();

  public static void getDestination(
    Long resource,
    List<String> destination,
    Map<Long, Long> mapTo
  ) {
    int pointer = 1;
    while (destination.size() > pointer) {
      Long converting = Long.parseLong(destination.get(pointer - 1));
      Long conversive = Long.parseLong(destination.get(pointer));
      Long length = Long.parseLong(destination.get(pointer + 1));
      Long rangeConverting = converting + length - 1;
      Long rangeConverted = conversive + length - 1;

      if (resource >= conversive && resource <= rangeConverted) {
        Long conversion = resource + (converting - conversive);
        mapTo.put(resource, conversion);
        break;
      } else {
        mapTo.putIfAbsent(resource, resource);
      }

      pointer += 3;
    }
  }

  public static void main(String[] args) {
    String filePath = "Inputs/input5.txt";
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      StringBuilder stringBuilder = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        stringBuilder.append(line).append("\n");
      }

      String text = stringBuilder.toString();
      String[] lines = text.split("(?m)^\\n");
      List<List<String>> mapsList = new ArrayList<>();

      for (String l : lines) {
        mapsList.add(
          StringRelated.returnNonBlanks(l.split("([a-z]|:|\\s|\\n|-)"))
        );
      }

      for (int i = 0; i < mapsList.get(0).size(); i++) {
        int k = 1;
        String source = mapsList.get(0).get(i);
        Long sourceLong = Long.parseLong(source);
        Long seedSoil = seedsToSoil.getOrDefault(sourceLong, null);

        if (seedSoil == null) {
          getDestination(sourceLong, mapsList.get(k), seedsToSoil);
          sourceLong = seedsToSoil.get(sourceLong);
          getDestination(sourceLong, mapsList.get(k + 1), soilsToFert);
          sourceLong = soilsToFert.get(sourceLong);
          getDestination(sourceLong, mapsList.get(k + 2), fertsToWater);
          sourceLong = fertsToWater.get(sourceLong);
          getDestination(sourceLong, mapsList.get(k + 3), watersToLight);
          sourceLong = watersToLight.get(sourceLong);
          getDestination(sourceLong, mapsList.get(k + 4), lightsToTemp);
          sourceLong = lightsToTemp.get(sourceLong);
          getDestination(sourceLong, mapsList.get(k + 5), tempsToHumi);
          sourceLong = tempsToHumi.get(sourceLong);
          getDestination(sourceLong, mapsList.get(k + 6), humisToLocation);
        }
      }

      Optional<Map.Entry<Long, Long>> entryWithMinValue = humisToLocation
        .entrySet()
        .stream()
        .min(Map.Entry.comparingByValue());

      entryWithMinValue.ifPresent(entry ->
        System.out.println(
          "Key: " + entry.getKey() + ", Value: " + entry.getValue()
        )
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
