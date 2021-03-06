package wordnet.App.Business;

import wordnet.App.Model.BodyFind;
import wordnet.App.Model.Output;
import wordnet.App.Util.CaseInput;

import java.io.IOException;
import java.util.List;

/**
 * Created by chien on 19/03/2018.
 */
public interface MainBusiness {
    Output identifyMeanOfWord(List<BodyFind> bodyFindSet, CaseInput caseInput);
}
