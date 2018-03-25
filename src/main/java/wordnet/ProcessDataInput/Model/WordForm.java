package wordnet.ProcessDataInput.Model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chien on 15/03/2018.
 */
@Data
public class WordForm {
    private String word;
    private List<String> listMean = new ArrayList<>();
    private IndexObject indexObject;

    public boolean verifyDependent() {
        return indexObject != null && indexObject.isDependent() && listMean.size() == 1 ? true : false;
    }
}
