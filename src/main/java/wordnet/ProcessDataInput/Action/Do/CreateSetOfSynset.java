package wordnet.ProcessDataInput.Action.Do;

import wordnet.ProcessDataInput.Dto.Synset;

import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
public interface CreateSetOfSynset {
    Set<String> createSetWordOfSynsetFromMapSynset(Map<String, Synset> synsetMap);
}
