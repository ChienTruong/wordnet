package wordnet.ProcessDataInput.Model;

import lombok.Data;
import wordnet.Util.Regex;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by chien on 15/03/2018.
 */
@Data
public class WordForm {
    private String word;
    private List<String> listMean = new ArrayList<>(0);
    private List<String> listSynonymMean = new ArrayList<>(0);
    private IndexObject indexObject;

    public boolean verifyDependent() {
        return indexObject != null && indexObject.isDependent() && listMean.size() == 1 ? true : false;
    }

    public Set<String> getAllMean() {
        return new HashSet<>(Arrays.asList(
                this.listMean.toString().replaceAll(Regex.regexBracket, "").split(", "))
                .stream().filter(s -> !s.isEmpty()).collect(Collectors.toSet())
        );
    }
}
