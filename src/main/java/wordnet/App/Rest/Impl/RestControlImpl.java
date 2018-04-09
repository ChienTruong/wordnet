package wordnet.App.Rest.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wordnet.App.Business.MainBusiness;
import wordnet.App.Model.BodyFind;
import wordnet.App.Rest.RestControl;
import wordnet.App.Util.CaseInput;
import wordnet.Util.PathFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by chien on 19/03/2018.
 */
@RestController
public class RestControlImpl implements RestControl {

    @Autowired
    private MainBusiness mainBusiness;

    @Override
    public ResponseEntity findWithWord(@RequestBody List<BodyFind> bodyFindList) {
        mainBusiness.identifyMeanOfWord(bodyFindList, CaseInput.WORD);
        return null;
    }

    @Override
    public ResponseEntity findWithSynset(@RequestBody List<BodyFind> bodyFindList) {
        mainBusiness.identifyMeanOfWord(bodyFindList, CaseInput.SYNSET);
        return null;
    }

    @Override
    public ResponseEntity get() throws IOException {
        Stream stream = Files.lines(Paths.get(PathFile.fileInput));
        List<BodyFind> bodyFindList = new ArrayList<>();
        stream.forEach(
                o -> {
                    BodyFind bodyFind = new BodyFind();
                    bodyFind.setWord(String.valueOf(o));
                    bodyFindList.add(bodyFind);
                }
        );
        this.findWithSynset(bodyFindList);
        return null;
    }

}
