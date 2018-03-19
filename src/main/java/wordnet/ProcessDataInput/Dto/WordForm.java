package wordnet.ProcessDataInput.Dto;

import lombok.Data;

import java.util.List;

/**
 * Created by chien on 15/03/2018.
 */
@Data
public class WordForm {
    private String word;
    private List<String> listMean;
    private IndexObject indexObject;
}
