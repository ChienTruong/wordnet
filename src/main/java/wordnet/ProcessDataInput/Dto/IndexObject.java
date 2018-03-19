package wordnet.ProcessDataInput.Dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chien on 15/03/2018.
 */
@Data
public class IndexObject {
    private String word;
    private boolean dependent = false;
    private Map<String, Synset> mapSynset = new HashMap<>();
}
