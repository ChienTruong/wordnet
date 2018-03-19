package wordnet.App.Business.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wordnet.App.Dto.BodyFind;
import wordnet.App.Business.MainBusiness;
import wordnet.App.Dto.Output;
import wordnet.ProcessDataInput.Business.MainBusinessProcessDataInput;

import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
@Component
public class MainBusinessImpl implements MainBusiness {

    private MainBusinessProcessDataInput mainBusinessProcessDataInput;

    @Autowired
    public MainBusinessImpl(MainBusinessProcessDataInput mainBusinessProcessDataInput) {
        this.mainBusinessProcessDataInput = mainBusinessProcessDataInput;
    }

    @Override
    public Output identifyMeanOfWord(Set<BodyFind> bodyFindSet) {
        return null;
    }
}
