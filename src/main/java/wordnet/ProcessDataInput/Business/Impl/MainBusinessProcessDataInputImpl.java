package wordnet.ProcessDataInput.Business.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wordnet.ProcessDataInput.Action.Do.CreateMapReflectionBetweenWordOfSynsetAndSynsetId;
import wordnet.ProcessDataInput.Action.Do.CreateSetOfSynset;
import wordnet.ProcessDataInput.Action.Do.Impl.ReplaceImpl;
import wordnet.ProcessDataInput.Action.Read.ReadFileData;
import wordnet.ProcessDataInput.Action.Read.ReadFileEV;
import wordnet.ProcessDataInput.Action.Read.ReadFileIndex;
import wordnet.ProcessDataInput.Business.MainBusinessProcessDataInput;
import wordnet.ProcessDataInput.Model.IndexObject;
import wordnet.ProcessDataInput.Model.MapObject;
import wordnet.App.Model.MapObjectProcessed;
import wordnet.ProcessDataInput.Model.Synset;
import wordnet.Util.PathFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by chien on 19/03/2018.
 */
@Component
public class MainBusinessProcessDataInputImpl implements MainBusinessProcessDataInput {

    private ReadFileEV readFileEV;
    private ReadFileData readFileData;
    private ReadFileIndex readFileIndex;
    private CreateSetOfSynset createSetOfSynset;
    private MapObject mapObject;

    public MainBusinessProcessDataInputImpl() {
    }

    @Autowired
    public MainBusinessProcessDataInputImpl(ReadFileEV readFileEV,
                                            ReadFileData readFileData,
                                            ReadFileIndex readFileIndex,
                                            CreateSetOfSynset createSetOfSynset,
                                            ReplaceImpl replace,
                                            CreateMapReflectionBetweenWordOfSynsetAndSynsetId createMapReflectionBetweenWordOfSynsetAndSynsetId) {
        this.readFileEV = readFileEV;
        this.readFileData = readFileData;
        this.readFileIndex = readFileIndex;
        this.createSetOfSynset = createSetOfSynset;
        this.mapObject = new MapObject(replace, createMapReflectionBetweenWordOfSynsetAndSynsetId);
    }

    private MapObjectProcessed doActionFour() throws IOException {
        Set<String> setWordOfGloss = this.mapObject.getAllWordOfGloss();
        Map<String, List<String>> listMeanGloss = this.readFileEV.read(setWordOfGloss);
        this.mapObject.replaceGloss(listMeanGloss);
        return this.retrieveCloneItSelf();
    }

    private MapObjectProcessed doActionThree() throws IOException {
        Set<String> setSynsetIdOfLayerNear = this.mapObject.getAllSynsetIdOfLayerNear();
        Map<String, Synset> mapSynset = this.readFileData.read(setSynsetIdOfLayerNear);
        Set<String> setOfWord = this.createSetOfSynset.createSetWordOfSynsetFromMapSynset(mapSynset);
        Map<String, List<String>> listMeanOfMapSet = this.readFileEV.read(setOfWord);
        setMeanforSynset(mapSynset, listMeanOfMapSet);
        this.mapObject.getMapObject().forEach(
                (s, indexObject) -> {
                    indexObject.getMapSynset().forEach(
                            (s1, synset) -> {
                                this.mapObject.replaceBetweenTwoMap(synset.getMapSynsetLayerOnes(), mapSynset);
                            }
                    );
                }
        );
        return doActionFour();
    }

    private MapObjectProcessed doActionTwo() throws IOException {
        Map<String, IndexObject> mapIndexObjectForDependent = this.readFileIndex.read(this.mapObject.getAllWordOfSynsetInIndexObject());
        this.mapObject.setDependentForIndexObjectOfWordInSynset(mapIndexObjectForDependent);
        return doActionThree();
    }

    public MapObjectProcessed doActionOneWithWord(Set<String> wordInputSet) throws IOException {
        // case 1
        // read file index with word input
        this.mapObject.setMapObject(this.readFileIndex.read(wordInputSet));
//        // read file data get synset object
//        Map<String, Synset> mapSynset = this.readFileData.read(this.mapObject.getAllSynsetIdOfIndexObject());
//        // read file EV get mean of word in container
//        Set<String> setOfWord = this.createSetOfSynset.createSetWordOfSynsetFromMapSynset(mapSynset);
//        Map<String, List<String>> mapMean = this.readFileEV.read(setOfWord);
//        setMeanforSynset(mapSynset, mapMean);
//        this.mapObject.replaceAllSynsetWithWordInThis(mapSynset);
        return doActionOne();
    }

    public MapObjectProcessed doActionOneWithSynset(Set<String> synsetIdSet) throws IOException {
        this.mapObject.setMapObject(makeMapIndexFromSetSynset(synsetIdSet));
        return doActionOne();
    }

    private MapObjectProcessed doActionOne() throws IOException {
        // read file data get synset object
        Map<String, Synset> mapSynset = this.readFileData.read(this.mapObject.getAllSynsetIdOfIndexObject());
        // read file EV get mean of word in container
        Set<String> setOfWord = this.createSetOfSynset.createSetWordOfSynsetFromMapSynset(mapSynset);
        Map<String, List<String>> mapMean = this.readFileEV.read(setOfWord);
        setMeanforSynset(mapSynset, mapMean);
        this.mapObject.replaceAllSynsetWithWordInThis(mapSynset);
        return doActionTwo();
    }

    private void setMeanforSynset(Map<String, Synset> mapSet, Map<String, List<String>> listMeanOfMapSet) {
        Set<Synset> setWord = mapSet.entrySet().stream().map(stringSynsetEntry -> stringSynsetEntry.getValue()).collect(Collectors.toSet());
        Map<String, List<String>> mapReflection = this.mapObject.createMapReflectionBetweenWordOfSynsetAndSynsetId(setWord);
        listMeanOfMapSet.forEach(
                (s, list) -> {
                    for (String synsetId : mapReflection.get(s)) {
                        mapSet.get(synsetId).getMapWordForm().get(s).setListMean(list);
                    }
                }
        );

    }

    private MapObjectProcessed retrieveCloneItSelf() {
        return this.mapObject.cloneThisProcessed();
    }

    private Map<String, IndexObject> makeMapIndexFromSetSynset(Set<String> synsetIdSet) {
        Map<String, IndexObject> map = new HashMap<>(1);
        IndexObject indexObject = new IndexObject();
        for (String s : synsetIdSet) {
            Synset synset = new Synset();
            synset.setSynsetId(s);
            indexObject.getMapSynset().put(s, synset);
        }
        indexObject.setWord("WORD");
        map.put(indexObject.getWord(), indexObject);
        return map;
    }
}
