package wordnet.App.Version2;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chien on 15/03/2018.
 */
@Data
public class Result {
    private String idSynset;
    private List<String> listWordEn = new ArrayList<>(0);
    private List<String> listWordVn = new ArrayList<>(0);
}
