package wordnet.App.Util;

import wordnet.App.Dto.BodyFind;
import wordnet.App.Dto.MapSynset;
import wordnet.App.Dto.Result;

import java.io.IOException;
import java.util.List;

/**
 * Created by chien on 28/02/2018.
 */
public interface FinderInFIle {

    List<Result> find(BodyFind bodyFind) throws IOException;
    void findVietnameseForMapSynset(MapSynset mapSynset) throws IOException;

}
