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

    private Map<String, List<String>> getReflectionBetweenSynsetIdAndWordOfSynset() {
        if (this.mapReflectionBetweenSynsetIdAndWordOfSynset == null) {
            this.mapReflectionBetweenSynsetIdAndWordOfSynset = new HashMap<>(0);
            this.mapObject.forEach(
                    (word, indexObject) -> {
                        indexObject.getMapSynset().forEach(
                                (synsetId, synset) -> {
                                    for (String wordOfSynset : synset.getMapWordForm().keySet()) {
                                        if (!this.mapReflectionBetweenSynsetIdAndWordOfSynset.containsKey(wordOfSynset)) {
                                            List<String> listSynsetId = new ArrayList<>();
                                            listSynsetId.add(synsetId);
                                            this.mapReflectionBetweenSynsetIdAndWordOfSynset.put(wordOfSynset, listSynsetId);
                                        } else {
                                            this.mapReflectionBetweenSynsetIdAndWordOfSynset.get(wordOfSynset).add(synsetId);
                                        }
                                    }
                                }
                        );
                    }
            );
        }
        return this.mapReflectionBetweenSynsetIdAndWordOfSynset;
    }

    public void replaceAllSynsetWithWordInThis(Map<String, Synset> mapSynsetWithWordInThis) {
        this.getReflectionBetweenWordAndSynsetId().forEach(
                (synsetId, list) -> {
                    for (String word : this.getReflectionBetweenWordAndSynsetId().get(synsetId)) {
                        this.mapObject.get(word).getMapSynset().put(synsetId, mapSynsetWithWordInThis.get(synsetId));
                    }
                });
    }

    public void replaceAllSynsetWithMeanOfWordInThis(Map<String, List<String>> mapSynsetWithMeanOfWordInThis) {
        mapSynsetWithMeanOfWordInThis.forEach(
                (key, listMean) -> {
                    List<String> listSynsetId = this.getReflectionBetweenSynsetIdAndWordOfSynset().get(key);
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
}
