package wordnet.App.Util;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by chien on 16/03/2018.
 */
public class SetOfWordAbsolutelyNotIsNoun {
    public static Set<String> setWordNotIsNoun = new HashSet<>();

    static {
        String[] strings = {
                "a", "an", "the",
                "be", "been", "am", "is", "are", "was", "were", "had", "has",
                "whom", "whose", "which", "where", "What",
                "in", "on", "at", "or", "of", "from", "as",
                "this", "that", "these", "our", "long", "into", "if",
                "however", "because", "with",
                "although", "though", "to", "before", "after",
                "since", "its",
                "said", "did", "didn't", "you're", "it's", "damed", "used", "done", "doing",
                "belong", "along", "below", "under",
                "his", "her", "he's", "she's",
                "aren't", "isn't"
        };
        for (String string : strings) {
            setWordNotIsNoun.add(string);
        }
    }
}
