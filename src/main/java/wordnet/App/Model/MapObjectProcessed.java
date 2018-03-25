package wordnet.App.Model;

import lombok.Data;
import wordnet.ProcessDataInput.Model.IndexObject;

import java.util.Map;

/**
 * Created by chien on 19/03/2018.
 */
@Data
public class MapObjectProcessed {

    private Map<String, IndexObject> mapObject;

    public int getCountSynset() {
        int count = 0;
        for (Map.Entry<String, IndexObject> stringIndexObjectEntry : this.mapObject.entrySet()) {
            count += stringIndexObjectEntry.getValue().getMapSynset().size();
        }
        return count;
    }
}
