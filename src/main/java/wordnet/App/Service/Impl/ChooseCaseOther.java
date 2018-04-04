package wordnet.App.Service.Impl;

import wordnet.App.Service.ChooseMeanOfSynset;
import wordnet.App.Util.Month;
import wordnet.ProcessDataInput.Model.Synset;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by chien on 25/03/2018.
 */
public class ChooseCaseOther implements ChooseMeanOfSynset {

    @Override
    public List<String> choice(Synset synset) {
        List<String> listMean = new ArrayList<>(0);
        Set<String> wordFormSet = synset.getMapWordForm().keySet();
        boolean isUpperCase = wordFormSet.stream().anyMatch(s -> Character.isUpperCase(s.charAt(0)));
        if (isUpperCase) {
            // case numeric
            for (String s : wordFormSet) {
                try {
                    Integer.parseInt(s);
                    listMean.add(s);
                    break;
                } catch (Exception e) {
//                    e.printStackTrace();
                }
            }
            // case blood
            if (listMean.isEmpty()) {
                if (synset.getGloss().contains("the blood group whose red cells carry")) {
                    for (String s : synset.getMapWordForm().keySet()) {
                        if (s.length() == 1) {
                            listMean.add("Nhóm máu " + s);
                            break;
                        }
                    }
                }
            }
            // case month
            if (listMean.isEmpty()) {
                for (String s : wordFormSet) {
                    if (Month.verifyIsMonth(s)) {
                        if (Month.verifyIsMonthShortName(s)) {
                            s = Month.getFullNameFromShortName(s);
                        }
                        listMean.addAll(synset.getMapWordForm().get(s).getListMean());
                        break;
                    }
                }
            }
        }
        return listMean;
    }

    @Override
    public String getNameStrategy() {
        return "Other";
    }
}
