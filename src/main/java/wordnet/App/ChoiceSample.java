package wordnet.App;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by chien on 04/05/2018.
 */
public class ChoiceSample {

    /**
     * index of list
     */
    private static int[] index = {1, 2, 125, 250, 375, 500, 625, 750, 875, 1000};
    /**
     * Sắp xếp theo thứ tự tăng dần dựa theo synset id
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Stream<String> streamFile = Files.lines(Paths.get("/home/chien/Documents/WordNet/result80kOfChien.txt"));
            List<String> listId = streamFile.map(s -> s.split(" | ")[0]).collect(Collectors.toList());
            Collections.sort(listId);
            List<String> listNew = new ArrayList<>(500);
            for (int i = 0; i < 50; i++) {
                for (int j = 0; j < index.length; j++) {
                    listNew.add(listId.get(index[j]));
                    index[j] = index[j] + 1000;
                }
            }
            BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("/home/chien/Documents/WordNet/sample.txt"));
            for (String s : listNew) {
                bufferedWriter.write(s+"\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
