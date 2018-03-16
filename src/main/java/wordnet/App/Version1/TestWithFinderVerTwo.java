package wordnet.App.Version1;

import wordnet.App.Dto.BodyFind;
import wordnet.App.Dto.WordEngVne;
import wordnet.App.Util.PathFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chien on 13/03/2018.
 */
public class TestWithFinderVerTwo {

    private MapReflection mapReflection;
    private static final Set<String> characterSet;

    static {
        characterSet = new HashSet<>();
        characterSet.add("~");
        characterSet.add("@");
    }


    private void doSomething() throws IOException {
        this.mapReflection = new MapReflection();
        findIndex();
        findData();
        makeData();
        DataMeanAndWord dataMeanAndWord = find(this.mapReflection.getWordOfSet());
        List<WordForm> wordFormList = this.mapReflection.getWordFormList();
        for (WordForm wordForm : wordFormList) {
            Map<String, List<String>> mapReflection = this.mapReflection.getReflectionMap().get(wordForm.getWord());
            setMean(dataMeanAndWord, wordForm, mapReflection);
        }
        System.out.println("mapReflection = " + mapReflection);
        for (WordForm wordForm : this.mapReflection.getWordFormList()) {
            for (String s : wordForm.getDataSynsetMap().keySet()) {
                System.out.println("wordForm = " + wordForm.getDataSynsetMap().get(s));
            }
        }
        findOnesLayer(this.mapReflection.getWordFormList().get(0).getDataSynsetMap().get("05854581"));
    }

    private void findOnesLayer(DataSynset dataSynset) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(PathFile.dataFile));
        Set<String> idSynsetSet = dataSynset.getSynsetIdOfOnesLayer();
        int size = idSynsetSet.size();
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null || size == 0) {
                break;
            }
            String[] lines = line.split(" ");
            if (idSynsetSet.contains(lines[0])) {
                size--;
            }
        }
        bufferedReader.close();
    }

    private void setMean(DataMeanAndWord dataMeanAndWord, WordForm wordForm, Map<String, List<String>> mapReflection) {
        Set<String> keySetOfDataMeanAndWord = dataMeanAndWord.getMapMean().keySet();
        for (String s : keySetOfDataMeanAndWord) {
            List<String> idSynsetList = mapReflection.get(s);
            for (String s1 : idSynsetList) {
                wordForm.getDataSynsetMap().get(s1).getMapWord().get(s).setMeanList(dataMeanAndWord.getMapMean().get(s));
            }
        }
    }

    /**
     * @throws IOException
     * @input set word
     * @output dataMeanAndWord include word and it's means
     */
    private DataMeanAndWord find(Set<String> wordSet) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(PathFile.fileDataEV));
        DataMeanAndWord dataMeanAndWord = new DataMeanAndWord();
        String line;
        int n = 0;
        int sizeOfMapIdAndWord = wordSet.size();
        WordEngVne wordEngVne = new WordEngVne();
        while (true) {
            line = bufferedReader.readLine();
            if (line == null || n == sizeOfMapIdAndWord + 1) {
                break;
            }
            if (!line.isEmpty()) {
                String firstCharacter = line.substring(0, 1);
                switch (firstCharacter) {
                    case "@":
                        line = line.substring(1, line.length());
                        wordEngVne.setWordForm(line);
                        if (wordSet.contains(line) || sizeOfMapIdAndWord == n) {
                            n++;
                        }
                        break;
                    case "-":
                        if (wordSet.contains(wordEngVne.getWordForm())) {
                            if (dataMeanAndWord.getMapMean().get(wordEngVne.getWordForm()) == null) {
                                List<String> meanList = new ArrayList<>(0);
                                meanList.add(line.substring(1, line.length()));
                                dataMeanAndWord.getMapMean().put(wordEngVne.getWordForm(), meanList);
                            } else {
                                dataMeanAndWord.getMapMean().get(wordEngVne.getWordForm()).add(line.substring(1, line.length()));
                            }
                        }
                        break;
                }
            }
        }
        bufferedReader.close();
        return dataMeanAndWord;
    }

    private void makeData() {
        int size = this.mapReflection.getWordFormList().size();
        for (int i = 0; i < size; i++) {
            Map<String, List<String>> mapFlat = new HashMap<>();
            Map<String, DataSynset> dataSynsetMap = this.mapReflection.getWordFormList().get(i).getDataSynsetMap();
            for (String s : dataSynsetMap.keySet()) {
                DataSynset dataSynset = dataSynsetMap.get(s);
                Map<String, WordForm> wordFormMap = dataSynset.getMapWord();
                for (String sC : wordFormMap.keySet()) {
                    WordForm form = wordFormMap.get(sC);
                    if (mapFlat.containsKey(form.getWord())) {
                        mapFlat.get(form.getWord()).add(dataSynset.getIdSynset());
                    } else {
                        List<String> wordList = new ArrayList<>(0);
                        wordList.add(dataSynset.getIdSynset());
                        mapFlat.put(form.getWord(), wordList);
                    }
                }
            }
            String key = this.mapReflection.getWordFormList().get(i).getWord();
            mapReflection.getReflectionMap().put(key, mapFlat);
        }
    }

    /**
     * @throws IOException
     * @input one word
     */
    private void findIndex() throws IOException {
        // prepare
        BufferedReader bufferedReader = new BufferedReader(new FileReader(PathFile.indexFile));
        BodyFind bodyFind = new BodyFind();
        bodyFind.setWord("regulation");
        // do
//         app ver 2, make data
        WordForm wordForm = new WordForm();
        wordForm.setWord(bodyFind.getWord());
        this.mapReflection.getWordFormList().add(wordForm);
//        WordForm wordForm = new WordForm();
//        wordForm.setWord("incredulity");
//        this.mapReflection.getWordFormList().add(wordForm);
//        wordForm = new WordForm();
//        wordForm.setWord("disbelief");
//        this.mapReflection.getWordFormList().add(wordForm);
//        wordForm = new WordForm();
//        wordForm.setWord("skepticism");
//        this.mapReflection.getWordFormList().add(wordForm);
        // end
        // start
        List<String> listWord = this.mapReflection.getWordFormList().stream().map(wordForm1 -> wordForm1.getWord()).collect(Collectors.toList());
        int count = this.mapReflection.getWordFormList().size();
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null || count == 0) {
                break;
            }
            if (!line.startsWith(" ")) {
                String[] lines = line.split(" ");
                if (listWord.contains(lines[0])) {
                    count--;
                    int index = listWord.indexOf(lines[0]);
                    int n = Integer.valueOf(lines[2]);
                    // set dependent
                    if (n == 1) {
                        this.mapReflection.getWordFormList().get(index).setDependentOneSynset(true);
                    }
                    // end
                    int i = 1;
                    while (i <= n) {
                        DataSynset dataSynset = new DataSynset();
                        dataSynset.setIdSynset(lines[lines.length - i]);
                        this.mapReflection.getWordFormList().get(index).getDataSynsetMap().put(dataSynset.getIdSynset(), dataSynset);
                        i++;
                    }
                }
            }
        }
        bufferedReader.close();
    }

    private void findData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(PathFile.dataFile));
        List<String> listSynsetId = new ArrayList<>(0);
        List<WordForm> wordFormInputList = this.mapReflection.getWordFormList();
        Map<String, List<WordForm>> mapIndex = new HashMap<>(0);
        for (WordForm wordForm : wordFormInputList) {
            Map<String, DataSynset> dataSynsetMap = wordForm.getDataSynsetMap();
            Set<String> setKey = dataSynsetMap.keySet();
            for (String s : setKey) {
                String idSynset = dataSynsetMap.get(s).getIdSynset();
                listSynsetId.add(idSynset);
                if (mapIndex.containsKey(idSynset)) {
                    mapIndex.get(idSynset).add(wordForm);
                } else {
                    List<WordForm> wordFormList = new ArrayList<>(0);
                    wordFormList.add(wordForm);
                    mapIndex.put(idSynset, wordFormList);
                }
            }
        }
        int count = listSynsetId.size();
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null || count == 0) {
                break;
            }
            if (!line.startsWith(" ")) {
                String[] lines = line.split(" ");
                if (listSynsetId.contains(lines[0])) {
                    count--;
                    // get map
                    List<WordForm> wordFormList = mapIndex.get(lines[0]);
                    for (WordForm wordForm : wordFormList) {
                        Set<String> setKey = wordForm.getDataSynsetMap().keySet();
                        for (String s : setKey) {
                            DataSynset dataSynset = wordForm.getDataSynsetMap().get(s);
                            if (dataSynset.getIdSynset().equals(lines[0])) {
                                // add gloss
                                dataSynset.setGloss(line.split(" \\| ")[1].trim());
                                // get word
                                int i = Integer.valueOf(lines[3]);
                                int k = 4;
                                for (int j = 0; j < i; j++) {
                                    dataSynset.getMapWord().put(lines[k], WordForm.builder().word(lines[k]).build());
                                    k += 2;
                                }
                                // get word hypernym and hyponym
                                int m = k + 1;
                                k = Integer.valueOf(lines[k]);
                                for (int j = 0; j < k; j++) {
                                    if (characterSet.contains(lines[m]) && lines[m + 2].equals("n")) {
                                        dataSynset.getDataSynsetOneLayer().add(DataSynset.builder().idSynset(lines[m + 1]).build());
                                    }
                                    m += 4;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        bufferedReader.close();
    }

    public static void main(String[] args) {
        try {
            TestWithFinderVerTwo testWithFinderVerTwo = new TestWithFinderVerTwo();
            testWithFinderVerTwo.doSomething();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String str = "15269955 28 n 01 moment_of_truth 0 001 @ 15269461 n 0000 | a crucial moment on which much depends ";
//        System.out.println(str.split(" \\| ")[1].trim());
    }
}
