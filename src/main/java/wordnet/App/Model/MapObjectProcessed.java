package wordnet.App.Model;

import lombok.Data;
import wordnet.ProcessDataInput.Model.IndexObject;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
@Data
public class MapObjectProcessed {

    private Map<String, IndexObject> mapObject;

    public int getCountSynset() {
        Set<String> set = new HashSet<>(0);
        for (Map.Entry<String, IndexObject> stringIndexObjectEntry : this.mapObject.entrySet()) {
            set.addAll(stringIndexObjectEntry.getValue().getMapSynset().keySet());
        }
        return set.size();
    }
}
