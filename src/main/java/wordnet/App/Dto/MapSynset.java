package wordnet.App.Dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by chien on 12/03/2018.
 */
@Data
public class MapSynset {
    private Map<String, Map<String, List<String>>> mapWordOfSynset;
    private Map<String, List<String>> mapWordAndId;
}
