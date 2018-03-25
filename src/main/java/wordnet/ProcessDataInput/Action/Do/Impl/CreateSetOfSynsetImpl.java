package wordnet.ProcessDataInput.Action.Do.Impl;

import org.springframework.stereotype.Component;
import wordnet.ProcessDataInput.Model.Synset;
import wordnet.ProcessDataInput.Action.Do.CreateSetOfSynset;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
@Component
public class CreateSetOfSynsetImpl implements CreateSetOfSynset {

    @Override
    public Set<String> createSetWordOfSynsetFromMapSynset(Map<String, Synset> synsetMap) {
        Set<String> wordSet = new HashSet<>(0);
        synsetMap.forEach(
                (s, synset) -> {
                    wordSet.addAll(synset.getMapWordForm().keySet());
                }
        );
        return wordSet;
    }
}
