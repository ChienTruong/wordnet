package wordnet.App.Dto;

import lombok.Data;
import wordnet.App.Util.FinderStrategy;

import java.util.List;
import java.util.Map;

/**
 * Created by chien on 12/03/2018.
 */
@Data
public class SynsetMean {

    private FinderStrategy finderStrategy;
    private Map<String, List<String>> mapWord;

    public List<String> getMeanOfSynset() {
        return finderStrategy.selectMean(mapWord);
    }
}
