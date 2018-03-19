package wordnet.ProcessDataInput.Action.Read.Impl;

import org.springframework.stereotype.Component;
import wordnet.Util.PathFile;
import wordnet.ProcessDataInput.Action.Read.ReadFileIndex;
import wordnet.ProcessDataInput.Dto.IndexObject;
import wordnet.ProcessDataInput.Dto.Synset;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 15/03/2018.
 */
@Component
public class ReadFileIndexImpl extends AttributeFile implements ReadFileIndex {

    @Override
    protected String getPathFile() {
        return PathFile.indexFile;
    }

    @Override
    public Map<String, IndexObject> read(Set<String> setWord) throws IOException {
        this.connect();
        Map<String, IndexObject> mapIndexObject = new HashMap<>();
        int count = setWord.size();
        while (true) {
            this.line = this.bufferedReader.readLine();
            if (this.line == null || count == 0) {
                break;
            }
            if (!line.startsWith(" ")) {
                String[] lines = this.line.split(" ");
                if (setWord.contains(lines[0])) {
                    // create object index
                    IndexObject indexObject = new IndexObject();
                    indexObject.setWord(lines[0]);
                    // end create object index
                    count--;
                    int n = Integer.valueOf(lines[2]);
                    // set dependent
                    if (n == 1) {
                        indexObject.setDependent(true);
                    }
                    // end
                    int i = 1;
                    while (i <= n) {
                        String synsetId = lines[lines.length - i];
                        // create object synset
                        Synset synset = new Synset();
                        synset.setSynsetId(synsetId);
                        // end create object synset
                        indexObject.getMapSynset().put(synsetId, synset);
                        i++;
                    }
                    mapIndexObject.put(lines[0], indexObject);
                }
            }
        }
        this.bufferedReader.close();
        return mapIndexObject;
    }
    // test
//    public static void main(String[] args) throws IOException {
//        Set<String> setWord = new HashSet<>(2);
//        setWord.add("regulation");
//        setWord.add("way");
//        ReadFileIndex readFileIndex = new ReadFileIndexImpl();
//        System.out.println(readFileIndex.read(setWord));
//    }
}
