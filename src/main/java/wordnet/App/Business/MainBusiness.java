package wordnet.App.Business;

import wordnet.App.Dto.BodyFind;
import wordnet.App.Dto.Output;

import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
public interface MainBusiness {
    Output identifyMeanOfWord(Set<BodyFind> bodyFindSet);
}
