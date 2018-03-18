package wordnet.App.Version2;

import wordnet.App.Version2.Action.Impl.ReadFileDataImpl;
import wordnet.App.Version2.Action.Impl.ReadFileEVImpl;
import wordnet.App.Version2.Action.Impl.ReadFileIndexImpl;
import wordnet.App.Version2.Action.ReadFileData;
import wordnet.App.Version2.Action.ReadFileEV;
import wordnet.App.Version2.Action.ReadFileIndex;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by chien on 15/03/2018.
 */
public class Main {

    private ReadFileData readFileData;
    private ReadFileIndex readFileIndex;
    private ReadFileEV readFileEV;
    private MapObject mapObject;

    public Main() {
        this.readFileData = new ReadFileDataImpl();
        this.readFileIndex = new ReadFileIndexImpl();
        this.readFileEV = new ReadFileEVImpl();
        this.mapObject = new MapObject();
    }

    public static void main(String[] args) {
        Set<String> setWordInput = new HashSet<>(0);
        setWordInput.add("regulation");
        setWordInput.add("way");
        Main main = new Main();
        try {
            main.doSomething(setWordInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Output doSomething(Set<String> setWord) {
        try {
            // read file index with word input
            this.mapObject.setMapObject(this.readFileIndex.read(setWord));
            // read file data get synset object
            Map<String, Synset> mapSynset = this.readFileData.read(this.mapObject.getAllSynsetIdOfIndexObject());
            // replace all of synset for container (mapObject)
//            this.mapObject.replaceAllSynsetWithWordInThis(mapSynset);
            // read file EV get mean of word in container
            Set<String> setOfWord = new HashSet<>(0);
            mapSynset.forEach(
                    (s, synset) -> {
                        setOfWord.addAll(synset.getMapWordForm().keySet());
                    }
            );
//            Map<String, List<String>> mapMean = this.readFileEV.read(this.mapObject.getAllWordOfSynsetInIndexObject());
            Map<String, List<String>> mapMean = this.readFileEV.read(setOfWord);
//            this.mapObject.replaceAllSynsetWithMeanOfWordInThis(mapMean);
            setMeanforSynset(mapSynset, mapMean);
            this.mapObject.replaceAllSynsetWithWordInThis(mapSynset);
            this.mapObject.getMapObject().forEach(
                    (word, indexObject) -> {
                        indexObject.getMapSynset().forEach(
                                (s1, synset) -> {
                                    System.out.println("synset = " + synset);
                                }
                        );
                    }
            );
            // read file idnex get dependent of word in word form of synset
//            Map<String, IndexObject> mapIndexObjectForDependent = this.readFileIndex.read(this.mapObject.getAllWordOfSynsetInIndexObject());
//            this.mapObject.setDependentForIndexObjectOfWordInSynset(mapIndexObjectForDependent);
            // read file data get synset layer near one of synset
//            Set<String> setSynsetIdOfLayerNear = this.mapObject.getAllSynsetIdOfLayerNear();
//            Map<String, Synset> mapSet = this.readFileData.read(setSynsetIdOfLayerNear);
//            Set<String> setOfWordTw = new HashSet<>(0);
//            mapSet.forEach(
//                    (s, synset) -> {
//                        setOfWordTw.addAll(synset.getMapWordForm().keySet());
//                    }
//            );
//            Map<String, List<String>> listMeanOfMapSet = this.readFileEV.read(setOfWordTw);
//            setMeanforSynset(mapSet, listMeanOfMapSet);
//            this.mapObject.getMapObject().forEach(
//                    (s, indexObject) -> {
//                        indexObject.getMapSynset().forEach(
//                                (s1, synset) -> {
//                                    synset.getMapSynsetLayerOnes().forEach(
//                                            (s2, synset1) -> {
//                                                System.out.println("synset1 = " + synset1);
//                                            }
//                                    );
//                                }
//                        );
//                    }
//            );
//            this.mapObject.getMapObject().forEach(
//                    (s, indexObject) -> {
//                        indexObject.getMapSynset().forEach(
//                                (s1, synset) -> {
//                                    this.mapObject.service.replaceBetweenTwoMap(synset.getMapSynsetLayerOnes(), mapSet);
//                                }
//                        );
//                    }
//            );
//            this.mapObject.getMapObject().forEach(
//                    (s, indexObject) -> {
//                        indexObject.getMapSynset().forEach(
//                                (s1, synset) -> {
//                                    synset.getMapSynsetLayerOnes().forEach(
//                                            (s2, synset1) -> {
//                                                System.out.println("synset11 = " + synset1);
//                                            }
//                                    );
//                                }
//                        );
//                    }
//            );
            // get all word
            Set<String> setWordOfGloss = this.mapObject.getAllWordOfGloss();
//            Map<String, IndexObject> mapIndexObjectOfWordOfGloss = this.readFileIndex.read(setWordOfGloss);
            Map<String, List<String>> listMeanGloss = this.readFileEV.read(setWordOfGloss);
            this.mapObject.replaceGloss(listMeanGloss);
            this.mapObject.getMapObject().forEach(
                    (s, indexObject) -> {
                        indexObject.getMapSynset().forEach(
                                (s1, synset) -> {
                                    System.out.println(synset.getAllWordInGloss());
                                    synset.getMapWordFormFromGloss().forEach(
                                            (s2, wordForm) -> {
                                                System.out.println("wordForm = " + wordForm);
                                            }
                                    );
                                }
                        );
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setMeanforSynset(Map<String, Synset> mapSet, Map<String, List<String>> listMeanOfMapSet) {
        Service service = new Service();
        Set<Synset> setWord = mapSet.entrySet().stream().map(stringSynsetEntry -> stringSynsetEntry.getValue()).collect(Collectors.toSet());
        Map<String, List<String>> mapReflection = service.createMapReflectionBetweenWordOfSynsetAndSynsetId(setWord);
        listMeanOfMapSet.forEach(
                (s, list) -> {
                    for (String synsetId : mapReflection.get(s)) {
                        mapSet.get(synsetId).getMapWordForm().get(s).setListMean(list);
                    }
                }
        );

    }
}
