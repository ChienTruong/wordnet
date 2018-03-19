package wordnet.App.Dto;

import lombok.Data;
import wordnet.ProcessDataInput.Dto.IndexObject;

import java.util.Map;

/**
 * Created by chien on 19/03/2018.
 */
@Data
public class MapObjectProcessed {

    private Map<String, IndexObject> mapObject;

}
