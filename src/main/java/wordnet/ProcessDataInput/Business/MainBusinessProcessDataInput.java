package wordnet.ProcessDataInput.Business;

import wordnet.App.Model.MapObjectProcessed;

import java.io.IOException;
import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
public interface MainBusinessProcessDataInput {
//    MapObjectProcessed doActionFour() throws IOException;
//
//    MapObjectProcessed doActionThree() throws IOException;
//
//    MapObjectProcessed doActionTwo() throws IOException;

    MapObjectProcessed doActionOneWithWord(Set<String> wordInputSet) throws IOException;

    MapObjectProcessed doActionOneWithSynset(Set<String> synsetIdSet) throws IOException;
}
