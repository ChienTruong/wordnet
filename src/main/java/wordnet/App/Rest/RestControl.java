package wordnet.App.Rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wordnet.App.Model.BodyFind;

import java.io.IOException;
import java.util.List;

/**
 * Created by chien on 19/03/2018.
 */
public interface RestControl {

    @PostMapping("/findWord")
    ResponseEntity findWithWord(@RequestBody List<BodyFind> bodyFindList);

    @PostMapping("/findSynset")
    ResponseEntity findWithSynset(@RequestBody List<BodyFind> bodyFindList);

    @GetMapping("/getAllMeanOfSynset")
    ResponseEntity get() throws IOException;

    @PostMapping("/find")
    ResponseEntity find(@RequestBody BodyFind bodyFind);
}
