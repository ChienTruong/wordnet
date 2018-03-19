package wordnet.ProcessDataInput.Action.Read;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 15/03/2018.
 */
public interface ReadFileEV {
    /**
     * @param setWord
     * @return
     * -- key of map type String
     * -- list string mean
     * @throws IOException
     * @example
     * map {
     * "regulation": []
     * "way": []
     * }
     */
    Map<String, List<String>> read(Set<String> setWord) throws IOException;
}
