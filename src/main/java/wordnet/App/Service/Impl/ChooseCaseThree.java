package wordnet.App.Service.Impl;

import wordnet.App.Service.ChooseMeanOfSynset;
import wordnet.App.Util.DataFileSynonym;
import wordnet.ProcessDataInput.Model.Synset;
import wordnet.ProcessDataInput.Model.WordForm;
import wordnet.Util.Regex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chien on 20/03/2018.
 */
public class ChooseCaseThree implements ChooseMeanOfSynset {

    private String name = "";
    private DataFileSynonym dataFileSynonym = new DataFileSynonym();


    @Override
    public List<String> choice(Synset synset) {
        // case 3.1
        List<String> listMean = new ArrayList<>(0);
        if (synset.getMapWordForm().size() >= 2) {
            this.name = "3.1";
            makeListMean(synset, listMean, null);
            if (listMean.isEmpty()) {
                dataFileSynonym.getSynonymForSynset(synset);
                this.name = this.name.replace(".B", ".B1");
                processCaseThreeB1(synset, listMean, null);
            }
        }
        Queue<ObjectForCaseDotBOne> synsetQueue = new ArrayDeque<>(2);
        int n = 2;
        do {
            if (!listMean.isEmpty()) {
            } else {
                if (synset.getMapWordForm().size() >= 1) {
                    Synset synsetNew = new Synset();
                    synsetNew.setSynsetId(synset.getSynsetId());
                    synsetNew.getMapWordForm().putAll(synset.getMapWordForm());
                    List<String> listVerifyMeanWordOfWordForm = MakerListContainMeanOfSynset.getListAllMeanOfSynset(synset);
                    Set<String> keyOfWordFormSet = synset.getMapWordForm().keySet();
                    switch (n) {
                        case 2:
                            // create synset
                            this.name = "3.2";
                            makeSynsetForCaseThreeDotTwo(synset, synsetNew, keyOfWordFormSet);
                            break;
                        case 3:
                            // create synset
                            this.name = "3.3";
                            makeSynsetForCaseThreeDotThree(synset, synsetNew, keyOfWordFormSet);
                            break;
                    }
                    if (synsetNew.getMapWordForm().size() > keyOfWordFormSet.size()) {
                        synsetQueue.add(new ObjectForCaseDotBOne(synsetNew, listVerifyMeanWordOfWordForm, this.name));
                        makeListMean(synsetNew, listMean, listVerifyMeanWordOfWordForm);
                    }
                }
            }
            n++;
        } while (n < 4);
        while (!synsetQueue.isEmpty() && listMean.isEmpty()) {
            ObjectForCaseDotBOne objectForCaseDotBOne = synsetQueue.poll();
            this.name = objectForCaseDotBOne.getName();
            dataFileSynonym.getSynonymForSynset(objectForCaseDotBOne.getSynset());
            processCaseThreeB1(objectForCaseDotBOne.getSynset(), listMean, objectForCaseDotBOne.getListVerifyMeanWordOfWordForm());
        }
        return listMean;
    }

    private void makeSynsetForCaseThreeDotThree(Synset synset, Synset synsetNew, Set<String> keyOfWordFormSet) {
        synset.getMapWordFormFromGloss().forEach(
                (s, wordForm) -> {
                    if (!keyOfWordFormSet.contains(wordForm.getWord())) {
                        synsetNew.getMapWordForm().put(wordForm.getWord(), wordForm);
                    }
                }
        );
    }

    private void makeSynsetForCaseThreeDotTwo(Synset synset, Synset synsetNew, Set<String> keyOfWordFormSet) {
        synset.getMapSynsetLayerOnes().forEach(
                (s, synset1) ->
                        synset1.getMapWordForm().forEach(
                                (s1, wordForm) -> {
                                    if (!keyOfWordFormSet.contains(wordForm.getWord())) {
                                        synsetNew.getMapWordForm().put(wordForm.getWord(), wordForm);
                                    }
                                }
                        )
        );
    }

    private void processCaseThreeB1(Synset synset, List<String> listMean, List<String> listVerifyMeanWordOfWordForm) {
        List<List<String>> collectionOfListMean = makeCollectionOfListMeanAndListSynonymMean(synset);
        for (int i = 0; i < collectionOfListMean.size() - 1; i++) {
            for (int j = i + 1; j < collectionOfListMean.size(); j++) {
                makeMapSDiceBetweenTwoListMean(listMean, collectionOfListMean.get(i), collectionOfListMean.get(j));
            }
        }
        if (listVerifyMeanWordOfWordForm != null) {
            listMean.removeIf(s -> !listVerifyMeanWordOfWordForm.contains(s));
        }
    }

    private List<List<String>> makeCollectionOfListMeanAndListSynonymMean(Synset synset) {
        List<WordForm> wordFormList = synset.getMapWordForm().entrySet().stream()
                .map(stringWordFormEntry -> stringWordFormEntry.getValue())
                .collect(Collectors.toList());
        List<List<String>> collectionOfListMean = new ArrayList<>(0);
        for (WordForm wordForm : wordFormList) {
            collectionOfListMean.add(joinListSynonymMeanAndListMean(wordForm));
        }
        return collectionOfListMean;
    }

    private List<String> joinListSynonymMeanAndListMean(WordForm wordForm) {
        List<String> meanList = new ArrayList<>(0);
        meanList.addAll(wordForm.getListMean());
        meanList.addAll(wordForm.getListSynonymMean());
        return meanList;
    }

    @Override
    public String getNameStrategy() {
        return name;
    }

    /**
     * @param synset
     * @param listMean
     * @param listVerifyMeanWordOfWordForm
     * @error 25/03/18
     * map count lưu trữ số count trước đó
     */
    private void makeListMean(Synset synset, List<String> listMean, List<String> listVerifyMeanWordOfWordForm) {
        this.name += ".A";
        Map<String, Integer> mapCountWord = CountMap.countWordInList(synset);
        mapCountWord.forEach(
                (s, integer) -> {
                    if (integer >= 2 && !s.isEmpty()) {
                        listMean.add(s);
                    }
                }
        );
        if (listVerifyMeanWordOfWordForm != null) {
            listMean.removeIf(s -> !listVerifyMeanWordOfWordForm.contains(s));
        }
        if (listMean.isEmpty()) {
            this.name = this.name.replace(".A", ".B");
            if (listVerifyMeanWordOfWordForm == null) {
                processCaseThreeOneDotB(synset, listMean);
            } else {
                processCaseThreeTwoDotBAndThreeDotB(synset, listMean, listVerifyMeanWordOfWordForm);
            }
            if (listVerifyMeanWordOfWordForm != null) {
                listMean.removeIf(s -> !listVerifyMeanWordOfWordForm.contains(s));
            }
        }
    }

    /**
     * trường hợp duyệt tất cả wordform trong cùng 1 synset
     * sẽ lần lượt duyệt từng cặp wordform với nhau
     * sau dó duyệt từng dòng nghĩa
     * kế tiếp sẽ lấy max sdice của từng dòng nghĩa
     * tính sdice của từng cặp chữ trong từng cặp dòng nghĩa
     *
     * @param synset
     * @param listMean
     */
    private void processCaseThreeOneDotB(Synset synset, List<String> listMean) {
        List<WordForm> wordFormList = synset.getMapWordForm().entrySet().stream().map(stringWordFormEntry -> stringWordFormEntry.getValue()).collect(Collectors.toList());
        int sizeWordFormListOfSynset = wordFormList.size();
        for (int i = 0; i < sizeWordFormListOfSynset - 1; i++) {
            List<String> listMeanOfWordFormOne = wordFormList.get(i).getListMean();
            for (int j = i + 1; j < sizeWordFormListOfSynset; j++) {
                List<String> listMeanOfWordFormTwo = wordFormList.get(j).getListMean();
                makeMapSDiceBetweenTwoListMean(listMean, listMeanOfWordFormOne, listMeanOfWordFormTwo);
            }
        }
    }

    /**
     * trường hợp duyệt so sánh của wordform hiện tại của synset với wordform của các synset lân cận hoặc wordform của gloss
     * sẽ so sánh những wordform có trong synset hiện tại với những wordform của synset lân cận hoặc gloss
     *
     * @param synset
     * @param listMean
     * @param listVerifyMeanWordOfWordForm
     * @error 28/03
     * xem lại cách lấy list wordform
     * bởi vì listverify chứa 1 dòng nghĩa
     * nên chuyển thành set chứa từng word
     */
    private void processCaseThreeTwoDotBAndThreeDotB(Synset synset, List<String> listMean, List<String> listVerifyMeanWordOfWordForm) {
//        Set<String> setVerifyWordByWordOfWordForm = new HashSet<>(0);
//        for (String s : listVerifyMeanWordOfWordForm) {
//            String[] strings = s.split(", ");
//            for (String string : strings) {
//                if (!string.isEmpty()) {
//                    setVerifyWordByWordOfWordForm.add(string);
//                }
//            }
//        }
        List<WordForm> wordFormListParent = new ArrayList<>(0);
        for (Map.Entry<String, WordForm> stringWordFormEntry : synset.getMapWordForm().entrySet()) {
            WordForm wordForm = stringWordFormEntry.getValue();
            String[] stringMeans = wordForm.getListMean().toString().replaceAll(Regex.regexBracket, "").split(", ");
            Set<String> meanOfWordForm = new HashSet<>(0);
            for (String stringMean : stringMeans) {
                if (!stringMean.isEmpty()) {
                    meanOfWordForm.add(stringMean);
                }
            }
            if (!meanOfWordForm.isEmpty() && listVerifyMeanWordOfWordForm.containsAll(meanOfWordForm)) {
                wordFormListParent.add(wordForm);
            }
        }
        List<WordForm> wordFormListChildren = synset.getMapWordForm().entrySet().stream()
                .map(stringWordFormEntry -> stringWordFormEntry.getValue())
                .filter(wordForm -> !wordFormListParent.contains(wordForm))
                .collect(Collectors.toList());
        for (WordForm wordForm : wordFormListParent) {
            List<String> listMeanOfWordFormOne = wordForm.getListMean();
            for (WordForm wordFormListChild : wordFormListChildren) {
                List<String> listMeanOfWordFormTwo = wordFormListChild.getListMean();
                makeMapSDiceBetweenTwoListMean(listMean, listMeanOfWordFormOne, listMeanOfWordFormTwo);
            }
        }
    }

    /**
     * tính map sdice của các dòng nghĩa
     * tính từng cặp chữ trong mỗi cặp dòng nghĩa
     *
     * @param listMean
     * @param listMeanOfWordFormOne
     * @param listMeanOfWordFormTwo
     */
    private void makeMapSDiceBetweenTwoListMean(List<String> listMean, List<String> listMeanOfWordFormOne, List<String> listMeanOfWordFormTwo) {
        for (int k = 0; k < listMeanOfWordFormOne.size(); k++) {
            String[] stringsOne = listMeanOfWordFormOne.get(k).split(", ");
            for (int l = 0; l < listMeanOfWordFormTwo.size(); l++) {
                String[] stringsTwo = listMeanOfWordFormTwo.get(l).split(", ");
                Map<Key, Float> mapDiceOfTwoString = new HashMap<>(0);
                for (String sOne : stringsOne) {
                    for (String sTwo : stringsTwo) {
                        if (!sOne.isEmpty() && !sTwo.isEmpty()) {
                            mapDiceOfTwoString.put(new Key(sOne, sTwo), CalculateDice.calculate(sOne, sTwo));
                        }
                    }
                }
                identifyMeanOfMapSDice(mapDiceOfTwoString, listMean);
            }
        }
    }

    /**
     * lấy max của map sdice
     * sau đó ấy những cặp từ tiếng việt có số sdice bằng max
     *
     * @param mapDiceOfTwoString
     * @param listMean
     */
    private void identifyMeanOfMapSDice(Map<Key, Float> mapDiceOfTwoString, List<String> listMean) {
        float max = Float.MIN_VALUE;
        for (Map.Entry<Key, Float> keyFloatEntry : mapDiceOfTwoString.entrySet()) {
            if (keyFloatEntry.getValue() > max) {
                max = keyFloatEntry.getValue();
            }
        }
        if (max >= 0.65F) {
            for (Map.Entry<Key, Float> keyFloatEntry : mapDiceOfTwoString.entrySet()) {
                if (keyFloatEntry.getValue().equals(max)) {
                    for (String s : keyFloatEntry.getKey().getKey()) {
                        if (!listMean.contains(s)) {
                            listMean.add(s);
                        }
                    }
                }
            }
        }
    }

    class Key {
        String wordOne;
        String wordTwo;

        public Key(String wordOne, String wordTwo) {
            this.wordOne = wordOne;
            this.wordTwo = wordTwo;
        }

        public List<String> getKey() {
            return Arrays.asList(this.wordOne, this.wordTwo);
        }

        @Override
        public String toString() {
            return "Key{" +
                    "wordOne='" + wordOne + '\'' +
                    ", wordTwo='" + wordTwo + '\'' +
                    '}';
        }
    }

    static class MakerListContainMeanOfSynset {

        private static Map<Synset, List<String>> mapReflection = new HashMap<>(0);

        public static List<String> getListAllMeanOfSynset(Synset synset) {
            if (!mapReflection.containsKey(synset)) {
                doMake(synset);
            }
            return mapReflection.get(synset);
        }

        private static void doMake(Synset synset) {
            List<String> allMeanOfSynset = new ArrayList<>(0);
            synset.getMapWordForm().forEach(
                    (s, wordForm) -> {
                        allMeanOfSynset.addAll(Arrays.asList(wordForm.getListMean().toString().replaceAll(Regex.regexBracket, "").split(", ")));
                    }
            );
            allMeanOfSynset.removeIf(s -> s.isEmpty());
            mapReflection.put(synset, allMeanOfSynset);
        }
    }

    class ObjectForCaseDotBOne {
        private Synset synset;
        private List<String> listVerifyMeanWordOfWordForm;
        private String name;

        public ObjectForCaseDotBOne(Synset synset, List<String> listVerifyMeanWordOfWordForm, String name) {
            this.synset = synset;
            this.listVerifyMeanWordOfWordForm = listVerifyMeanWordOfWordForm;
            this.name = name + ".B1";
        }

        public Synset getSynset() {
            return synset;
        }

        public List<String> getListVerifyMeanWordOfWordForm() {
            return listVerifyMeanWordOfWordForm;
        }

        public String getName() {
            return name;
        }
    }
}
