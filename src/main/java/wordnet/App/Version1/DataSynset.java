package wordnet.App.Version1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * Created by chien on 13/03/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataSynset {
    private String idSynset;
    private String gloss;
    private Map<String, WordForm> mapWord = new HashMap<>(0);
    private List<DataSynset> dataSynsetOneLayer = new ArrayList<>(0);

    public Set<String> getSynsetIdOfOnesLayer() {
        Set<String> synsetIdSet = new HashSet<>(0);
        for (DataSynset dataSynset : this.dataSynsetOneLayer) {
            synsetIdSet.add(dataSynset.getIdSynset());
        }
        return synsetIdSet;
    }

    public Set<String> getWordOfSynset() {
        return this.mapWord.keySet();
    }

    public Set<String> getWordOfOnesLayer() {
        Set<String> set = new HashSet<>(0);
        for (DataSynset dataSynset : this.dataSynsetOneLayer) {
            set.addAll(dataSynset.getWordOfSynset());
        }
        return set;
    }

}
