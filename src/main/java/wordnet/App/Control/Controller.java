package wordnet.App.Control;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by chien on 27/07/2018.
 */
@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("")
    public String home() {
        return "index";
    }
}
