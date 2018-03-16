package wordnet.App.Service;

import wordnet.App.Dto.BodyFind;
import wordnet.App.Dto.Result;

import java.io.IOException;
import java.util.List;

/**
 * Created by chien on 27/02/2018.
 */
public interface ServiceFind {

    List<Result> findSynset(BodyFind bodyFind) throws IOException;

}
