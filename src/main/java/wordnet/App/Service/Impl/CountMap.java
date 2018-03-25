package wordnet.App.Service.Impl;

import wordnet.ProcessDataInput.Model.Synset;
import wordnet.Util.Regex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chien on 20/03/2018.
 */
public class CountMap {

    private static Map<String, Map<String, Integer>> collectionOfMapCount = new HashMap<>(0);

    /**
     * đếm số lần từ xuất hiện
     *
     * @param synset
     * @param wordFormKeySet
     * @return
     */
    public static Map<String, Integer> countWordInList(Synset synset, Set<String> wordFormKeySet) {
        List<String> listWordVn = findListMeanWordVn(synset, wordFormKeySet);
        Map<String, Integer> mapCountWordVn = new HashMap<>(0);
        for (String s : listWordVn) {
            if (mapCountWordVn.containsKey(s)) {
                int num = mapCountWordVn.get(s).intValue();
                num++;
                mapCountWordVn.put(s, num);
            } else {
                mapCountWordVn.put(s, new Integer(1));
            }
        }
        return mapCountWordVn;
    }

    private static List<String> findListMeanWordVn(Synset synset, Set<String> wordFormKeySet) {
        List<String> listWordVn = new ArrayList<>(0);
        for (String s : wordFormKeySet) {
            List<String> listMeanOfWordForm = synset.getMapWordForm().get(s).getListMean();
            Set<String> setMeanOfWordForm = listMeanOfWordForm.stream().collect(Collectors.toSet());
            String[] strings = setMeanOfWordForm.toString().replaceAll(Regex.regexBracket, "").split(", ");
            for (String string : strings) {
                if (!string.isEmpty()) {
                    listWordVn.add(string.trim());
                }
            }
        }
        return listWordVn;
    }

    /**
     * có 2 khả năng xảy ra
     * 1. chưa tồn tại synset trong map cache
     * 2. tồn tại, nhưng số lượng từ count khác nhau
     * @param synset
     * @return
     */
    public static Map<String, Integer> countWordInList(Synset synset) {
        // 1
        if (!collectionOfMapCount.containsKey(synset.getSynsetId())) {
            Map<String, Integer> mapCount = countWordInList(synset, synset.getMapWordForm().keySet());
            collectionOfMapCount.put(synset.getSynsetId(), mapCount);
        } else {
            // 2
            // verify same map
            Set<String> wordFormVnOfSynset = new HashSet<>(0);
            synset.getMapWordForm().entrySet().forEach(stringWordFormEntry -> wordFormVnOfSynset.addAll(stringWordFormEntry.getValue().getListMean()));
            if (!wordFormVnOfSynset.equals(collectionOfMapCount.get(synset.getSynsetId()).keySet())) {
                Map<String, Integer> mapCount = countWordInList(synset, synset.getMapWordForm().keySet());
                collectionOfMapCount.put(synset.getSynsetId(), mapCount);
            }
        }
        return collectionOfMapCount.get(synset.getSynsetId());
    }

}
