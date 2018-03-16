package wordnet.App.Version1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chien on 13/03/2018.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordForm {
    private String word;
    private boolean dependentOneSynset = false;
    private List<String> meanList = new ArrayList<>(0);
    private Map<String, DataSynset>  dataSynsetMap = new HashMap<>(0);

}
