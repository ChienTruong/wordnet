package wordnet.App.Rest.Impl;

import lombok.Getter;
import org.springframework.stereotype.Component;
import wordnet.App.Model.BodyResult;
import wordnet.Util.PathFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by chien on 27/07/2018.
 */
@Getter
@Component
public class Result {

    private Map<String, List<String>> mapWordSynsetId = new HashMap<>();
    private Map<String, List<String>> mapSynsetIdWord = new HashMap<>();
    private Map<String, List<String>> mapSynsetIdMean = new HashMap<>();
    private Map<String, String> mapSynsetIdGloss = new HashMap<>();

    @PostConstruct
    public void init() throws IOException {
        Stream<String> stream = Files.lines(Paths.get(PathFile.result));
        stream.forEach(str -> {
            String[] strs = str.split(" \\| ");
            this.addMean(this.mapSynsetIdMean, strs[0], strs[2]);
            this.addWord(this.mapSynsetIdWord, strs[0], strs[1]);
            this.addSynId(this.mapWordSynsetId, strs[1], strs[0]);
            this.mapSynsetIdGloss.put(strs[0], strs[4]);
        });
    }

    public List<BodyResult> getMeanOfWord(String word) {
        List<String> listSynsetOfWord = this.mapWordSynsetId.get(word);
        List<BodyResult> bodyResults = new LinkedList<>();
        if (listSynsetOfWord != null) {
            for (String synset : listSynsetOfWord) {
                BodyResult bodyResult = new BodyResult();
                bodyResult.setSynsetId(synset);
                bodyResult.setGloss(this.mapSynsetIdGloss.get(synset));
                bodyResult.setWords(this.mapSynsetIdWord.get(synset).toString());
                List<String> listMeanOfWord = this.mapSynsetIdMean.get(synset);
                List<String> listMeanResult = new LinkedList<>();
                bodyResult.setMeans(listMeanResult);
                for (String mean : listMeanOfWord) {
                    listMeanResult.add(mean);
                }
                bodyResults.add(bodyResult);
            }
        }
        return bodyResults;
    }

    private void addMean(Map<String, List<String>> map, String key, String str) {
        str = str.substring(1, str.length() - 1);
        if (!map.containsKey(key)) {
            map.put(key, new LinkedList<>());
        }
        map.get(key).add(str);
    }

    private void addWord(Map<String, List<String>> map, String key, String str) {
        str = str.substring(1, str.length() - 1);
        if (!map.containsKey(key)) {
            map.put(key, new LinkedList<>());
        }
        map.get(key).add(str);
    }

    private void addSynId(Map<String, List<String>> map, String key, String value) {
        key = key.substring(1, key.length() - 1);
        String[] keys = key.split(", ");
        for (int i = 0; i < keys.length; i++) {
            if (!map.containsKey(keys[i])) {
                map.put(keys[i], new LinkedList<>());
            }
            map.get(keys[i]).add(value);
        }
    }
}
