package wordnet.App.Util.Impl;

import org.springframework.stereotype.Component;
import wordnet.App.Version1.DataSynset;
import wordnet.App.Dto.BodyFind;
import wordnet.App.Dto.MapSynset;
import wordnet.App.Dto.Result;
import wordnet.App.Dto.WordEngVne;
import wordnet.App.Util.FinderInFIle;
import wordnet.App.Util.PathFile;
import wordnet.App.Version1.WordForm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chien on 28/02/2018.
 */
@Component
public class FinderInFileImpl implements FinderInFIle {

    private BufferedReader bufferedReader;

    private List<String> findInIndex(BodyFind bodyFind) throws IOException {
        this.connect(PathFile.indexFile);
        List<String> listSynsetId = new ArrayList<>(0);
        // app ver 2, make data
        WordForm wordForm = new WordForm();
        wordForm.setWord(bodyFind.getWord());
        // end
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            if (!line.startsWith(" ")) {
                String[] lines = line.split(" ");
                if (lines[0].equals(bodyFind.getWord())) {
                    int n = Integer.valueOf(lines[2]);
                    // set dependent
                    if (n == 1) {
                        wordForm.setDependentOneSynset(true);
                    }
                    // end
                    int i = 1;
                    while (i<=n) {
                        listSynsetId.add(lines[lines.length - i]);
                        // set synset
                        DataSynset dataSynset = new DataSynset();
                        dataSynset.setIdSynset(lines[lines.length - i]);
                        // end
                        i++;
                    }
                    break;
                }
            }
        }
        this.bufferedReader.close();
        return listSynsetId;
    }

    private List<Result> findInData(List<String> listSynsetId) throws IOException {
        this.connect(PathFile.dataFile);
        List<Result> resultList = new ArrayList<>(0);
        String line;
        while (true) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            if (!line.startsWith(" ")) {
                String[] lines = line.split(" ");
                if (listSynsetId.contains(lines[0])) {
                    listSynsetId.remove(lines[0]);
                    Result result = new Result();
                    result.setResultId(lines[0]);
                    int i = Integer.valueOf(lines[3]);
                    int k = 4;
                    for (int j = 0; j < i; j++) {
                        result.getWords().add(lines[k]);
                        k+=2;
                    }
                    resultList.add(result);
                }
            }
        }
        this.bufferedReader.close();
        return resultList;
    }

    private void connect(String pathFile) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(pathFile));
    }

    @Override
    public List<Result> find(BodyFind bodyFind) throws IOException {
        List<String> stringList = this.findInIndex(bodyFind);
        List<Result> resultList = this.findInData(stringList);
        return resultList;
    }

    /**
     * @input mapSynset
     * @output all of mean vietnamese of word english in synset
     * @param mapSynset
     * @throws IOException
     */
    @Override
    public void findVietnameseForMapSynset(MapSynset mapSynset) throws IOException {
        connect(PathFile.fileDataEV);
        String line;
        int n = 0;
        int sizeOfMapIdAndWord = mapSynset.getMapWordAndId().size();
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
                        if (mapSynset.getMapWordAndId().keySet().contains(line) || sizeOfMapIdAndWord == n) {
                            n++;
                        }
                        break;
                    case "-":
                        if (mapSynset.getMapWordAndId().keySet().contains(wordEngVne.getWordForm())) {
                            List<String> listSynsetIdRelatedWithWord = mapSynset.getMapWordAndId().get(wordEngVne.getWordForm());
                            for (String s : listSynsetIdRelatedWithWord) {
                                mapSynset.getMapWordOfSynset().get(s).get(wordEngVne.getWordForm()).add(line.substring(1, line.length()));
                            }
                        }
                        break;
                }
            }
        }
        this.bufferedReader.close();
    }
}
