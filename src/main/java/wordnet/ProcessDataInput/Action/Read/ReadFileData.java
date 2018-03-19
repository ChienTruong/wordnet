package wordnet.ProcessDataInput.Action.Read;

import wordnet.ProcessDataInput.Dto.Synset;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 15/03/2018.
 */
public interface ReadFileData {

    Map<String, Synset> read(Set<String> setIdSynset) throws IOException;
}
