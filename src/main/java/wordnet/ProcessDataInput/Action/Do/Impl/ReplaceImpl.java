package wordnet.ProcessDataInput.Action.Do.Impl;

import org.springframework.stereotype.Component;
import wordnet.ProcessDataInput.Model.Synset;
import wordnet.ProcessDataInput.Model.WordForm;
import wordnet.ProcessDataInput.Action.Do.Replace;

import java.util.List;
import java.util.Map;

/**
 * Created by chien on 18/03/2018.
 */
@Component
public class ReplaceImpl implements Replace {

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
