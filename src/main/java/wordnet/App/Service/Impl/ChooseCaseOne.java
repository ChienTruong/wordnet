package wordnet.App.Service.Impl;

import org.springframework.stereotype.Component;
import wordnet.App.Service.ChooseMeanOfSynset;
import wordnet.ProcessDataInput.Model.Synset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
public class ChooseCaseOne implements ChooseMeanOfSynset {

    /**
     * case one
     * with condition:
     * 1. synset have more than two word form
     * 2. each mean of word in synset have more than one line mean vietnamese
     * 3. tất cả synset đều có chung một dòng nghĩa
     * @param synset
     * @return list mean of word in synset, choose with all o word in synset have less one mean
     */
    @Override
    public List<String> choice(Synset synset) {
        List<String> listMean = new ArrayList<>(0);
        if (synset.getMapWordForm().size() >= 2) {
            Set<String> wordFormKeySet = synset.getMapWordForm().keySet();
            for (String s : wordFormKeySet) {
                if (synset.getMapWordForm().get(s).getListMean().size() < 1) {
                    return listMean;
                }
            }
            // do action
            Map<String, Integer> mapCountWord = CountMap.countWordInList(synset);
            int size = wordFormKeySet.size();
            mapCountWord.forEach(
                    (s, integer) -> {
                        if (integer.equals(size) && !s.isEmpty()) {
                            listMean.add(s);
                        }
                    }
            );
        }
        return listMean;
    }

    @Override
    public String getNameStrategy() {
        return "1";
    }

}
