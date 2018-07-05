package wordnet.App;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by chien on 24/05/2018.
 */
public class Count {

    private static Map<String, Integer> stringIntegerMap = new HashMap<>(14);

    static {
        stringIntegerMap.put("Other", 0); // contain Special
        stringIntegerMap.put("Noun-Individual", 0);
        stringIntegerMap.put("1", 0);
        stringIntegerMap.put("2", 0);
        stringIntegerMap.put("3.1.A", 0);
        stringIntegerMap.put("3.1.B", 0);
        stringIntegerMap.put("3.1.B1", 0);
        stringIntegerMap.put("3.2.A", 0);
        stringIntegerMap.put("3.2.B", 0);
        stringIntegerMap.put("3.2.B1", 0);
        stringIntegerMap.put("3.3.A", 0);
        stringIntegerMap.put("3.3.B", 0);
        stringIntegerMap.put("3.3.B1", 0);
    }

    public static void main(String[] args) {
        try {
            Stream str = Files.lines(Paths.get("/home/chien/Documents/WordNet/result80kOfChien.txt"));
            str.forEach(
                    o -> {
                        String[] strings = o.toString().split(" \\| ");
                        if (strings[3].contains("Special")) {
                            stringIntegerMap.put("Other", stringIntegerMap.get("Other") + 1);
                        } else {
                            stringIntegerMap.put(strings[3], stringIntegerMap.get(strings[3]) + 1);
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        int num = 0;
        for (String s : stringIntegerMap.keySet()) {
            System.out.println(s + " = " + stringIntegerMap.get(s));
            num += stringIntegerMap.get(s);
        }
        System.out.println(num);
    }
}