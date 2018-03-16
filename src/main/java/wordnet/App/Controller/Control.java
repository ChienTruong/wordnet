package wordnet.App.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by chien on 27/02/2018.
 */
@Controller
public class Control {
    @GetMapping("")
    public String home() {
        return "index";
    }
}
