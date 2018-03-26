package wordnet.App.Model;

import lombok.Data;

import java.util.List;

/**
 * Created by chien on 15/03/2018.
 */
@Data
public class Result {
    private String idSynset;
    private List<String> listWordEn;
    private List<String> listWordVn;
    private String gloss;
    private String caseName;

    @Override
    public String toString() {
        return "Result{" +
                "idSynset='" + idSynset + '\'' +
                ", listWordEn=" + listWordEn +
                ", listWordVn=" + listWordVn +
                ", gloss='" + gloss + '\'' +
                ", caseName='" + caseName + '\'' +
                '}';
    }
}
