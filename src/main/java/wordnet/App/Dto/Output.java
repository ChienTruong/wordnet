package wordnet.App.Dto;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chien on 15/03/2018.
 */
@Data
public class Output {
    private Map<String, List<Result>> map = new HashMap<>(0);
}
