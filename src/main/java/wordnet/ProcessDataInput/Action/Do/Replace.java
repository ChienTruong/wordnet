package wordnet.ProcessDataInput.Action.Do;

import wordnet.ProcessDataInput.Dto.Synset;

import java.util.List;
import java.util.Map;

/**
 * Created by chien on 19/03/2018.
 */
public interface Replace {


    void replaceBetweenTwoMap(Map<String, Synset> synsetMapOne, Map<String, Synset> synsetMapTwo);

    void replaceMapGloss(Synset synset, Map<String, List<String>> listMeanGloss);
}
