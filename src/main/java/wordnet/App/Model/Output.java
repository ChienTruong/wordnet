package wordnet.App.Model;

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
