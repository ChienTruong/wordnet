package wordnet.ProcessDataInput.Action.Do;

import wordnet.ProcessDataInput.Dto.Synset;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
public interface CreateMapReflectionBetweenWordOfSynsetAndSynsetId {
    Map<String, List<String>> createMapReflectionBetweenWordOfSynsetAndSynsetId(Set<Synset> synsetSet);
}
