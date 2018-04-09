package wordnet.App.Business.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wordnet.App.Business.MainBusiness;
import wordnet.App.Model.BodyFind;
import wordnet.App.Model.MapObjectProcessed;
import wordnet.App.Model.Output;
import wordnet.App.Model.Result;
import wordnet.App.Service.ChooseMeanOfSynset;
import wordnet.App.Util.ExportExcel;
import wordnet.App.Util.NameStrategy;
import wordnet.App.Util.StrategyFactory;
import wordnet.ProcessDataInput.Business.MainBusinessProcessDataInput;
import wordnet.ProcessDataInput.Model.Synset;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by chien on 19/03/2018.
 */
@Component
public class MainBusinessImpl implements MainBusiness {

    private MainBusinessProcessDataInput mainBusinessProcessDataInput;
    private MapObjectProcessed mapObjectProcessed;
    private Output output;
    private ChooseMeanOfSynset chooseMeanOfSynset;

    @Autowired
    public MainBusinessImpl(MainBusinessProcessDataInput mainBusinessProcessDataInput) {
        this.mainBusinessProcessDataInput = mainBusinessProcessDataInput;
    }

    @Override
    public Output identifyMeanOfWord(List<BodyFind> bodyFindSet) {
        // convert to set of word input
        this.output = new Output();
        Set<String> wordSet = bodyFindSet.stream().map(bodyFind -> bodyFind.getWord()).collect(Collectors.toSet());
        try {
            this.mapObjectProcessed = this.mainBusinessProcessDataInput.doActionOne(wordSet);
            processForFindMeanOfSynset();
            System.out.println(this.mapObjectProcessed.getCountSynset());
            this.output.getMap().forEach(
                    (s, results) -> {
                        for (Result result : results) {
                            System.out.println("result = " + result);
                        }
                    }
            );
            System.out.println(this.output.getCountOfResult());
//            ExportExcel.exportExcel(this.output);
//            System.out.println("Done Export");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     */
    private void processForFindMeanOfSynset() {
        this.mapObjectProcessed.getMapObject().forEach(
                (word, indexObject) ->
                        indexObject.getMapSynset().forEach(
                                (synsetId, synset) -> {
                                    if (!this.output.verifySynsetProcessed(synset)) {
                                        for (Class aClass : NameStrategy.classList) {
                                            try {
                                                this.chooseMeanOfSynset = StrategyFactory.getStrategy(aClass);
                                                List<String> listMean = this.chooseMeanOfSynset.choice(synset);
                                                if (/*listMean != null && */!listMean.isEmpty()) {
                                                    addMeanOfSynsetForOutput(listMean, synset, word, synset.getGloss(), this.chooseMeanOfSynset);
                                                    break;
                                                }
                                            } catch (IllegalAccessException e) {
                                                e.printStackTrace();
                                            } catch (InstantiationException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                        )
        );
    }

    /**
     * set mean for synset
     *
     * @param listMean
     * @param synset
     * @param word
     * @param gloss
     * @param chooseMeanOfSynset
     */
    private void addMeanOfSynsetForOutput(List<String> listMean, Synset synset, String word, String gloss, ChooseMeanOfSynset chooseMeanOfSynset) {
        Result result = new Result();
        result.setIdSynset(synset.getSynsetId());
        result.setListWordEn(synset.getMapWordForm().keySet().stream().collect(Collectors.toList()));
        result.setListWordVn(listMean);
        result.setGloss(gloss);
        result.setCaseName(chooseMeanOfSynset.getNameStrategy());
        this.output.plus(result, word);
    }

}
