package wordnet.App.Version1;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Created by chien on 14/03/2018.
 */
@Data
public class MapReflection {

    private List<WordForm> wordFormList = new ArrayList<>();
    private Map<String, Map<String, List<String>>> reflectionMap = new HashMap<>();
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Set<String> wordSet;

    public Set<String> getWordOfSet() {
        if (wordSet == null) {
            wordSet = new HashSet<>(0);
            Set<String> setParentId = this.reflectionMap.keySet();
            for (String s : setParentId) {
                Set<String> setChildrenId = this.reflectionMap.get(s).keySet();
                for (String sC : setChildrenId) {
                    wordSet.add(sC);
                }
            }
        }
        return wordSet;
    }

}
