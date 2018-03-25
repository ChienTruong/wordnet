package wordnet.App.Rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wordnet.App.Model.BodyFind;

import java.util.List;

/**
 * Created by chien on 19/03/2018.
 */
public interface RestControl {
    @PostMapping("/find")
    public ResponseEntity find(@RequestBody List<BodyFind> bodyFindList);
}
