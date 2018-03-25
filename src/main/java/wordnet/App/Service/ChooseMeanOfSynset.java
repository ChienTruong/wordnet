package wordnet.App.Service;

import wordnet.ProcessDataInput.Model.Synset;

import java.util.List;
import java.util.Map;

/**
 * Created by chien on 19/03/2018.
 */
public interface ChooseMeanOfSynset {
    List<String> choice(Synset synset);

    String getNameStrategy();
}
