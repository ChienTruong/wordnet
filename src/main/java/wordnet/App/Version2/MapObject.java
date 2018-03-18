package wordnet.App.Version2;

import java.util.*;

/**
 * Created by chien on 15/03/2018.
 */
public class MapObject {

    private Map<String, IndexObject> mapObject = new HashMap<>(0);
    private Set<String> setSynsetId;
    private Map<String, List<String>> mapReflectionBetweenWordAndSynsetId;
    private Set<String> setWord;
    private Map<String, List<String>> mapReflectionBetweenSynsetIdAndWordOfSynset;
    public Service service = new Service();

    public Map<String, IndexObject> getMapObject() {
        return mapObject;
    }

    public void setMapObject(Map<String, IndexObject> mapObject) {
        this.mapObject = mapObject;
    }

    public Set<String> getAllWordOfSynsetInIndexObject() {
        if (this.setWord == null) {
            this.setWord = new HashSet<>(0);
            this.mapObject.forEach(
                    (word, indexObject) -> {
                        indexObject.getMapSynset().forEach(
                                (synsetId, synset) -> {
                                    this.setWord.addAll(synset.getMapWordForm().keySet());
                                }
                        );
                    }
            );
        }
        return this.setWord;
    }

    public Set<String> getAllSynsetIdOfIndexObject() {
        if (this.setSynsetId == null) {
            this.setSynsetId = new HashSet<>();
            Set<String> setKey = this.mapObject.keySet();
            for (String s : setKey) {
                this.setSynsetId.addAll(this.mapObject.get(s).getMapSynset().keySet());
            }
        }
        return this.setSynsetId;
    }

    private Map<String, List<String>> getReflectionBetweenWordAndSynsetId() {
        if (this.mapReflectionBetweenWordAndSynsetId == null) {
            this.mapReflectionBetweenWordAndSynsetId = new HashMap<>(0);
            Set<String> setSynsetId = this.getAllSynsetIdOfIndexObject();
            for (String s : setSynsetId) {
                this.mapObject.forEach((s1, indexObject) -> {
                    if (indexObject.getMapSynset().keySet().contains(s)) {
                        if (this.mapReflectionBetweenWordAndSynsetId.containsKey(s)) {
                            this.mapReflectionBetweenWordAndSynsetId.get(s).add(s1);
                        } else {
                            List<String> listWord = new ArrayList<>(0);
                            listWord.add(s1);
                            this.mapReflectionBetweenWordAndSynsetId.put(s, listWord);
                        }
                    }
                });
            }
        }
        return this.mapReflectionBetweenWordAndSynsetId;
    }

    private Map<String, List<String>> getReflectionBetweenWordOfSynsetAndSynsetId() {
        if (this.mapReflectionBetweenSynsetIdAndWordOfSynset == null) {
            Set<Synset> setSynset = new HashSet<>(0);
            this.mapObject.forEach(
                    (s, indexObject) -> {
                        indexObject.getMapSynset().forEach(
                                (s1, synset) -> {
                                    setSynset.add(synset);
                                }
                        );
                    }
            );
            this.mapReflectionBetweenSynsetIdAndWordOfSynset = this.service.createMapReflectionBetweenWordOfSynsetAndSynsetId(setSynset);
        }
        return this.mapReflectionBetweenSynsetIdAndWordOfSynset;
    }

    public void replaceAllSynsetWithWordInThis(Map<String, Synset> mapSynsetWithWordInThis) {
//        this.getReflectionBetweenWordAndSynsetId().forEach(
//                (synsetId, list) -> {
//                    for (String word : this.getReflectionBetweenWordAndSynsetId().get(synsetId)) {
//                        this.mapObject.get(word).getMapSynset().put(synsetId, mapSynsetWithWordInThis.get(synsetId));
//                    }
//                });
        this.mapObject.forEach(
                (s, indexObject) -> {
                    service.replaceBetweenTwoMap(indexObject.getMapSynset(), mapSynsetWithWordInThis);
                }
        );
    }

    public void replaceAllSynsetWithMeanOfWordInThis(Map<String, List<String>> mapSynsetWithMeanOfWordInThis) {
        mapSynsetWithMeanOfWordInThis.forEach(
                (key, listMean) -> {
                    List<String> listSynsetId = this.getReflectionBetweenWordOfSynsetAndSynsetId().get(key);
                    for (String synsetId : listSynsetId) {
                        List<String> listWord = this.getReflectionBetweenWordAndSynsetId().get(synsetId);
                        for (String word : listWord) {
                            this.mapObject.get(word).getMapSynset().get(synsetId).getMapWordForm().get(key).setListMean(listMean);
                        }
                    }
                }
        );
    }

    public Set<String> getAllWordOfGloss() {
        Set<String> setWordOfAllGlossInSynset = new HashSet<>(0);
        this.mapObject.forEach(
                (wordParent, indexObject) -> {
                    indexObject.getMapSynset().forEach(
                            (synsetId, synset) -> {
                                setWordOfAllGlossInSynset.addAll(synset.getAllWordInGloss());
                            }
                    );
                }
        );
        return setWordOfAllGlossInSynset;
    }

    public Set<String> getAllSynsetIdOfLayerNear() {
        Set<String> setSynsetIdOfLayerNear = new HashSet<>(0);
        this.mapObject.forEach(
                (word, indexObject) -> {
                    indexObject.getMapSynset().forEach(
                            (synsetId, synset) -> {
                                setSynsetIdOfLayerNear.addAll(synset.getMapSynsetLayerOnes().keySet());
                            }
                    );
                }
        );
        return setSynsetIdOfLayerNear;
    }

    public void setDependentForIndexObjectOfWordInSynset(Map<String, IndexObject> dependentForWordInSynset) {
        dependentForWordInSynset.forEach(
                (s, indexObject) -> {
                    List<String> listSynsetIdContainThisWord = this.getReflectionBetweenWordOfSynsetAndSynsetId().get(s);
                    for (String synsetId : listSynsetIdContainThisWord) {
                        List<String> listWordParentContainThisSynset = this.getReflectionBetweenWordAndSynsetId().get(synsetId);
                        for (String wordParent : listWordParentContainThisSynset) {
                            this.mapObject.get(wordParent).getMapSynset().get(synsetId).getMapWordForm().get(s).setIndexObject(indexObject);
                        }
                    }
                }
        );
    }

    public void replaceGloss(Map<String, List<String>> listMeanGloss) {
        this.mapObject.forEach(
                (s, indexObject) -> {
                    indexObject.getMapSynset().forEach(
                            (s1, synset) -> {
                                service.replaceMapGloss(synset, listMeanGloss);
                            }
                    );
                }
        );
    }
}
