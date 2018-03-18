package wordnet.App.Version2;

import java.util.*;

/**
 * Created by chien on 18/03/2018.
 */
public class Service {
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

    public void replaceBetweenTwoMap(Map<String, Synset> synsetMapOne, Map<String, Synset> synsetMapTwo) {
        synsetMapOne.forEach(
                (s, synset) -> {
                    if (synsetMapTwo.get(s) != null) {
                        synsetMapOne.put(s, synsetMapTwo.get(s));
                    }
                }
        );
    }

    public void replaceMapGloss(Synset synset, Map<String, List<String>> listMeanGloss) {
        for (String s : synset.getAllWordInGloss()) {
            WordForm wordForm = new WordForm();
            wordForm.setWord(s);
            wordForm.setListMean(listMeanGloss.get(s));
            synset.getMapWordFormFromGloss().put(s, wordForm);
        }
    }
}
