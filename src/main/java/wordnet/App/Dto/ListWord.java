package wordnet.App.Dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chien on 12/03/2018.
 */
@Data
public class ListWord {
    private List<String> listWord = new ArrayList<>(0);
}
