package wordnet.App.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wordnet.App.Dto.BodyFind;
import wordnet.App.Dto.Result;
import wordnet.App.Service.ServiceFind;

import java.io.IOException;
import java.util.List;

/**
 * Created by chien on 27/02/2018.
 */
@RestController
public class RestControl {

    private ServiceFind serviceFind;

    @Autowired
    public RestControl(ServiceFind serviceFind) {
        this.serviceFind = serviceFind;
    }

    @PostMapping("/find")
    public ResponseEntity<List<Result>> find(@RequestBody BodyFind bodyFind) {
        List<Result> listSynsetId = null;
        try {
            listSynsetId = serviceFind.findSynset(bodyFind);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(listSynsetId);
    }
}
