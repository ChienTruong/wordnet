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
            this.mapObject.replaceAllSynsetWithWordInThis(mapSynset);
            // read file EV get mean of word in container
            Map<String, List<String>> mapMean = this.readFileEV.read(this.mapObject.getAllWordOfSynsetInIndexObject());
            this.mapObject.replaceAllSynsetWithMeanOfWordInThis(mapMean);
            this.mapObject.getMapObject().forEach((s, indexObject) -> {
                System.out.println("indexObject = " + indexObject);
            });
//            // get all word
//            Set<String> setWordOfGloss = this.mapObject.getAllWordOfGloss();
//            Map<String, IndexObject> mapIndexObjectOfWordOfGloss = this.readFileIndex.read(setWordOfGloss);
//            // read file data get synset layer near one of synset
//            Set<String> setSynsetIdOfLayerNear = this.mapObject.getAllSynsetIdOfLayerNear();
//            Map<String, Synset> mapSet = this.readFileData.read(setSynsetIdOfLayerNear);
//            // read file idnex get dependent of word in word form of synset
//            Map<String, IndexObject> mapIndexObjectForDependent = this.readFileIndex.read(this.mapObject.getAllWordOfSynsetInIndexObject());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
