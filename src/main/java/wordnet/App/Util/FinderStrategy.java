package wordnet.App.Util;

import java.util.List;
import java.util.Map;

/**
 * Created by chien on 12/03/2018.
 */
public interface FinderStrategy {
    List<String> selectMean(Map<String, List<String>> synset);
}
