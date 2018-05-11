package wordnet.App.Business.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wordnet.App.Business.MainBusiness;
import wordnet.App.Model.BodyFind;
import wordnet.App.Model.MapObjectProcessed;
import wordnet.App.Model.Output;
import wordnet.App.Model.Result;
import wordnet.App.Service.ChooseMeanOfSynset;
import wordnet.App.Util.CaseInput;
import wordnet.App.Util.Export;
import wordnet.App.Util.NameStrategy;
import wordnet.App.Util.StrategyFactory;
import wordnet.ProcessDataInput.Business.MainBusinessProcessDataInput;
import wordnet.ProcessDataInput.Model.Synset;
import wordnet.ProcessDataInput.Model.WordForm;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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

    private Scanner scanner;

    @Autowired
    public MainBusinessImpl(MainBusinessProcessDataInput mainBusinessProcessDataInput) {
        this.mainBusinessProcessDataInput = mainBusinessProcessDataInput;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Output identifyMeanOfWord(List<BodyFind> bodyFindSet, CaseInput caseInput) {
        // convert to set of word input
        this.output = new Output();
        Set<String> input = bodyFindSet.stream().map(bodyFind -> bodyFind.getWord()).collect(Collectors.toSet());
        try {
            switch (caseInput) {
                case SYNSET:
                    this.mapObjectProcessed = this.mainBusinessProcessDataInput.doActionOneWithSynset(input);
                    break;
                case WORD:
                    this.mapObjectProcessed = this.mainBusinessProcessDataInput.doActionOneWithWord(input);
                    break;
            }
            long start = Calendar.getInstance().getTimeInMillis();
            processForFindMeanOfSynset();
//            doSomething();
            long end = Calendar.getInstance().getTimeInMillis();
            System.out.println((end - start) / 1000 / 60);
            System.out.println(this.mapObjectProcessed.getCountSynset());
//            this.output.getMap().forEach(
//                    (s, results) -> {
//                        for (Result result : results) {
//                            System.out.println("result = " + result);
//                        }
//                    }
//            );
            System.out.println(this.output.getCountOfResult());
            export();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void doSomething() throws IOException {
        BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("/home/chien/Documents/WordNet/HandleFile.txt"));
        this.mapObjectProcessed.getMapObject().forEach(
                (word, indexObject) -> {
                    indexObject.getMapSynset().forEach(
                            (synsetId, synset) -> {
                                try {
                                    // synset id
                                    bufferedWriter.write("*" + synsetId + "\n");
                                    // print word of synset
                                    bufferedWriter.write("**\n");
                                    process(synset.getMapWordForm(), bufferedWriter);
                                    // print word parent and children
                                    bufferedWriter.write("***\n");
                                    synset.getMapSynsetLayerOnes().forEach(
                                            (s, synset1) -> {
                                                process(synset1.getMapWordForm(), bufferedWriter);
                                            }
                                    );
                                    // print gloss
                                    bufferedWriter.write("****\n");
                                    process(synset.getMapWordFormFromGloss(), bufferedWriter);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                    );
                }
        );
    }

    private void process(Map<String, WordForm> mapWordForm, BufferedWriter bufferedWriter) {
        mapWordForm.forEach(
                (word, wordForm) -> {
                    try {
                        bufferedWriter.write(word + " | " + wordForm.getListMean().toString() + " | " + wordForm.getListSynonymMean().toString() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private void export() throws IOException {
        System.out.println("Do you want export:");
        System.out.println("1: Excel");
        System.out.println("2: Txt");
        System.out.println("Else: Out");
        System.out.println("Choose: ");
        String choice = this.scanner.nextLine();
        switch (choice) {
            case "1":
                Export.exportExcel(this.output);
                System.out.println("Done Export");
                break;
            case "2":
                Export.exportTxt(this.output);
                System.out.println("Done Export");
                break;
            default:
                System.exit(0);
        }
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
