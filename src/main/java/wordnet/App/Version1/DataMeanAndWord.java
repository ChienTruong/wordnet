package wordnet.App.Version1;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chien on 14/03/2018.
 */
@Data
public class DataMeanAndWord {
    private Map<String, List<String>> mapMean = new HashMap<>(0);
}
