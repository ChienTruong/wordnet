package wordnet.App.Rest.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wordnet.App.Business.MainBusiness;
import wordnet.App.Model.BodyFind;
import wordnet.App.Rest.RestControl;

import java.util.List;

/**
 * Created by chien on 19/03/2018.
 */
@RestController
public class RestControlImpl implements RestControl {

    @Autowired
    private MainBusiness mainBusiness;

    @Override
    public ResponseEntity find(@RequestBody List<BodyFind> bodyFindList) {
        mainBusiness.identifyMeanOfWord(bodyFindList);
        return null;
    }
}
