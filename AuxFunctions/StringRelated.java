package AuxFunctions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringRelated {

  public static List<String> returnNonBlanks(String[] values) {
    return (
      Arrays
        .stream(values)
        .filter(s -> !s.isEmpty())
        .collect(Collectors.toList())
    );
  }
}
