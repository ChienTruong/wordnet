package wordnet.ProcessDataInput.Business;

import wordnet.App.Dto.MapObjectProcessed;

import java.io.IOException;
import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
public interface MainBusinessProcessDataInput {
    MapObjectProcessed doActionFour() throws IOException;

    MapObjectProcessed doActionThree() throws IOException;

    MapObjectProcessed doActionTwo() throws IOException;

    MapObjectProcessed doActionOne(Set<String> wordInputSet) throws IOException;
}
