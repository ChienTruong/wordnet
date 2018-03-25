package wordnet.App.Service.Impl;

import wordnet.App.Service.ChooseMeanOfSynset;
import wordnet.ProcessDataInput.Model.Synset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by chien on 20/03/2018.
 */
public class ChooseCaseTwo implements ChooseMeanOfSynset {

    /**
     * case two
     * with condition:
     * 1. synset have more than one wordform
     * 2. have less one synset dependent, độc lập là chỉ thuộc 1 synset và có 1 dòng nghĩa
     *
     * @param synset
     * @return
     */
    @Override
    public List<String> choice(Synset synset) {
        List<String> listMean = new ArrayList<>(0);
        if (synset.getMapWordForm().size() >= 1) {
            Set<String> wordFormKeyDependentSet = synset.getMapWordForm().keySet().stream().filter(s -> synset.getMapWordForm().get(s).verifyDependent()).collect(Collectors.toSet());
            if (wordFormKeyDependentSet.size() >= 1) {
                Map<String, Integer> mapCountWord = CountMap.countWordInList(synset, wordFormKeyDependentSet);
                int sizeOfWordFormDependent = wordFormKeyDependentSet.size();
                mapCountWord.forEach(
                        (s, integer) -> {
                            if (integer.equals(sizeOfWordFormDependent) && !s.isEmpty()) {
                                listMean.add(s);
                            }
                        }
                );
                if (!listMean.isEmpty()) {
                    for (String s : wordFormKeyDependentSet) {
                        listMean.addAll(synset.getMapWordForm().get(s).getListMean());
                    }
                }
            }
        }
        return listMean;
    }

    @Override
    public String getNameStrategy() {
        return "2";
    }

}
