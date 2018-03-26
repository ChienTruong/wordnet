package wordnet.App.Model;

import wordnet.ProcessDataInput.Model.Synset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chien on 15/03/2018.
 */

public class Output {
    private Map<String, List<Result>> map = new HashMap<>(0);

    public Map<String, List<Result>> getMap() {
        return map;
    }

    public void plus(Result result, String word) {
        if (this.map.get(word) == null) {
            List<Result> list = new ArrayList<>(0);
            this.map.put(word, list);
        }
        this.map.get(word).add(result);
    }

    public boolean verifySynsetProcessed(Synset synset) {
        return this.map.entrySet().stream()
                .anyMatch(stringListEntry ->
                        stringListEntry.getValue().stream()
                                .anyMatch(result -> result.getIdSynset().equals(synset.getSynsetId()))
                );
    }

    public int getCountOfResult() {
        int count = 0;
        for (Map.Entry<String, List<Result>> stringListEntry : this.map.entrySet()) {
            count += stringListEntry.getValue().size();
        }
        return count;
    }

    @Override
    public String toString() {
        return "Output{" +
                "map=" + map +
                '}';
    }
}
