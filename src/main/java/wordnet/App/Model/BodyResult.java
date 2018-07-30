package wordnet.App.Model;

import lombok.Data;

import java.util.List;

/**
 * Created by chien on 28/07/2018.
 */
@Data
public class BodyResult {

    private String synsetId;
    private String words;
    private String gloss;
    private List<String> means;
}
