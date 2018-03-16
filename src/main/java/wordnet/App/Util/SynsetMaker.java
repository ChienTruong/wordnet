package wordnet.App.Util;

import wordnet.App.Dto.MapSynset;
import wordnet.App.Dto.Result;

import java.util.List;

/**
 * Created by chien on 12/03/2018.
 */
public interface SynsetMaker {
    MapSynset makeMapSynset(List<Result> resultList);
}
