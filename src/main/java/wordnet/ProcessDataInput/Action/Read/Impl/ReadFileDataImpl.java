package wordnet.ProcessDataInput.Action.Read.Impl;

import org.springframework.stereotype.Component;
import wordnet.Util.PathFile;
import wordnet.ProcessDataInput.Action.Read.ReadFileData;
import wordnet.ProcessDataInput.Dto.Synset;
import wordnet.ProcessDataInput.Dto.WordForm;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 15/03/2018.
 */
@Component
public class ReadFileDataImpl extends AttributeFile implements ReadFileData {

    private static final Set<String> characterSet;

    static {
        characterSet = new HashSet<>();
        characterSet.add("~");
        characterSet.add("@");
    }

    @Override
    protected String getPathFile() {
        return PathFile.dataFile;
    }

    @Override
    public Map<String, Synset> read(Set<String> setIdSynset) throws IOException {
        this.connect();
        Map<String, Synset> mapSynset = new HashMap<>(0);
        int count = setIdSynset.size();
        while (true) {
            this.line = this.bufferedReader.readLine();
            if (this.line == null || count == 0) {
                break;
            }
            if (!line.startsWith(" ")) {
                String[] lines = this.line.split(" ");
                if (setIdSynset.contains(lines[0])) {
                    count--;
                    Synset synset = new Synset();
                    synset.setSynsetId(lines[0]);
                    synset.setGloss(line.split(" \\| ")[1].trim());
                    // get word
                    int i = Integer.valueOf(lines[3]);
                    int k = 4;
                    for (int j = 0; j < i; j++) {
                        String word = lines[k];
                        // create word form
                        WordForm wordForm = new WordForm();
                        wordForm.setWord(word);
                        // end word form
                        synset.getMapWordForm().put(word, wordForm);
                        k += 2;
                    }
                    // get word hypernym and hyponym
                    int m = k + 1;
                    k = Integer.valueOf(lines[k]);
                    for (int j = 0; j < k; j++) {
                        if (characterSet.contains(lines[m]) && lines[m + 2].equals("n")) {
                            String idSynset = lines[m + 1];
                            // create object synset layer
                            Synset synsetLayerOnes = new Synset();
                            synsetLayerOnes.setSynsetId(idSynset);
                            // end creat object
                            synset.getMapSynsetLayerOnes().put(idSynset, synsetLayerOnes);
                        }
                        m += 4;
                    }
                    mapSynset.put(lines[0], synset);
                }
            }
        }
        this.bufferedReader.close();
        return mapSynset;
    }

    // test
//    public static void main(String[] args) throws IOException {
//        Set<String> setSynset = new HashSet<>(0);
//        setSynset.add("00808563");
//        setSynset.add("14466900");
//        ReadFileData readFileData = new ReadFileDataImpl();
//        System.out.println(readFileData.read(setSynset));
//    }
}
