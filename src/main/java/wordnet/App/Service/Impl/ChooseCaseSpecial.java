package wordnet.App.Service.Impl;

import wordnet.App.Service.ChooseMeanOfSynset;
import wordnet.App.Util.DataFileSyllable;
import wordnet.ProcessDataInput.Model.Synset;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chien on 03/04/2018.
 */
public class ChooseCaseSpecial implements ChooseMeanOfSynset {

    private DataFileSyllable dataFileSyllable = new DataFileSyllable();

    @Override
    public List<String> choice(Synset synset) {
        List<String> listMean = new ArrayList<>(0);
        if (synset.getGloss().contains("the syllable naming the")) {
            listMean.add(dataFileSyllable.getMeanOfSynset(synset.getSynsetId()));
        }
        return listMean;
    }

    @Override
    public String getNameStrategy() {
        return "Special (Translate at hand)";
    }
}
