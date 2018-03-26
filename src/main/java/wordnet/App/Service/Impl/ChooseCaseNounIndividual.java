package wordnet.App.Service.Impl;

import wordnet.App.Service.ChooseMeanOfSynset;
import wordnet.App.Util.Month;
import wordnet.ProcessDataInput.Model.Synset;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by chien on 25/03/2018.
 */
public class ChooseCaseNounIndividual implements ChooseMeanOfSynset {
    @Override
    public List<String> choice(Synset synset) {
        List<String> listMean = new ArrayList<>(0);
        Set<String> wordFormSet = synset.getMapWordForm().keySet();
        boolean isUpperCase = wordFormSet.stream().anyMatch(s -> Character.isUpperCase(s.charAt(0)));
        if (isUpperCase) {
            // verify it is a number count
            for (String s : wordFormSet) {
                try {
                    Integer.parseInt(s);
                    return listMean;
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
            // verify it is a month
            for (String s : wordFormSet) {
                if (Month.verifyIsMonth(s)) {
                    return listMean;
                }
            }
            listMean.add("NounIndividual");
        }
        return listMean;
    }

    @Override
    public String getNameStrategy() {
        return "Noun-Individual";
    }

}
