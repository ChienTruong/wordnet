package wordnet.ProcessDataInput.Action.Do.Impl;

import org.springframework.stereotype.Component;
import wordnet.ProcessDataInput.Dto.Synset;
import wordnet.ProcessDataInput.Action.Do.CreateMapReflectionBetweenWordOfSynsetAndSynsetId;

import java.util.*;

/**
 * Created by chien on 19/03/2018.
 */
@Component
public class CreateMapReflectionBetweenWordOfSynsetAndSynsetIdImpl implements CreateMapReflectionBetweenWordOfSynsetAndSynsetId {
    @Override
    public Map<String, List<String>> createMapReflectionBetweenWordOfSynsetAndSynsetId(Set<Synset> synsetSet) {
        Map<String, List<String>> mapReflectionBetweenWordOfSynsetAndSynsetId = new HashMap<>(0);
        synsetSet.forEach(
                synset -> {
                    Map<String, String> mapReflectionBetweenWordFormOfSynsetAndSynset = synset.getMapReflectonBetweenWordAndThisId();
                    mapReflectionBetweenWordFormOfSynsetAndSynset.forEach(
                            (s, s2) -> {
                                if (!mapReflectionBetweenWordOfSynsetAndSynsetId.containsKey(s)) {
                                    List<String> listSynsetId = new ArrayList<>(0);
                                    listSynsetId.add(s2);
                                    mapReflectionBetweenWordOfSynsetAndSynsetId.put(s, listSynsetId);
                                } else {
                                    mapReflectionBetweenWordOfSynsetAndSynsetId.get(s).add(s2);
                                }
                            }
                    );
                }
        );
        return mapReflectionBetweenWordOfSynsetAndSynsetId;
    }
}