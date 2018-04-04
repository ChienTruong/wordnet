package wordnet.App.Util;

import wordnet.ProcessDataInput.Model.Synset;
import wordnet.Util.PathFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by chien on 28/03/2018.
 */
public class DataFileSynonym extends DataFile {

    public void getSynonymForSynset(Synset synset) {
        Set<String> wordFormEnSet = synset.getMapWordForm().keySet();
        Map<String, String> reflectionBetweenWordVnAndWordForm = new HashMap<>(0);
        Set<String> wordFormVnSet = new HashSet<>(0);
        for (String s : wordFormEnSet) {
            List<String> meanList = synset.getMapWordForm().get(s).getListMean();
            for (String s1 : meanList) {
                String[] strings = s1.split(", ");
                for (String string : strings) {
                    wordFormVnSet.add(string);
                    reflectionBetweenWordVnAndWordForm.put(string, s);
                }
            }
        }
        for (String s : this.list) {
            String[] strings = s.split(",");
            for (String string : strings) {
                if (wordFormVnSet.contains(string)) {
                    String wordEn = reflectionBetweenWordVnAndWordForm.get(string);
                    synset.getMapWordForm().get(wordEn).getListSynonymMean().add(s.replace(",", ", "));
                    break;
                }
            }
        }
    }

    public DataFileSynonym() {
        super();
    }

    @Override
    protected String getNameFile() {
        return PathFile.fileSynonym;
    }

}
