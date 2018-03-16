package wordnet.App.Util.Impl;

import wordnet.App.Dto.ListWord;
import wordnet.App.Util.FinderStrategy;
import wordnet.App.Util.Regex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 12/03/2018.
 */
public class FinderStrategyOneImpl implements FinderStrategy {
    @Override
    public List<String> selectMean(Map<String, List<String>> synset) {
        List<ListWord> listWords = new ArrayList<>(synset.size());
        Set keySet = synset.keySet();
        for (Object o : keySet) {
            ListWord listWord = new ListWord();
            String string = synset.get(o).toString();
            String[] strings = string.replaceAll(Regex.regexBracket, "").split(",");
            for (int i = 0; i < strings.length; i++) {
                listWord.getListWord().add(strings[i].trim());
            }
            listWords.add(listWord);
        }
        // get min
        ListWord listWord = listWords.get(0);
        for (int i = 1; i < listWords.size(); i++) {
            if (listWord.getListWord().size() < listWords.get(i).getListWord().size()) {
                listWord = listWords.get(i);
            }
        }
        List<String> stringList = listWord.getListWord();
        List<String> listMean = new ArrayList<>(0);
        for (String s : stringList) {
            int count = 1;
            for (ListWord word : listWords) {
                if (word != listWord && word.getListWord().contains(s)) {
                    count++;
                    if (count == synset.size()) {
                        listMean.add(s);
                    }
                }
            }
        }
        return listMean;
    }
}
