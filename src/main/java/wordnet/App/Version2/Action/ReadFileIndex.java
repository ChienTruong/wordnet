package wordnet.App.Version2.Action;

import wordnet.App.Version2.IndexObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 15/03/2018.
 */
public interface ReadFileIndex {
    Map<String, IndexObject> read(Set<String> setWord) throws IOException;
}
