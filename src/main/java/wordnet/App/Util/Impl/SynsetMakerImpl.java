package wordnet.App.Util.Impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import wordnet.App.Dto.MapSynset;
import wordnet.App.Dto.Result;
import wordnet.App.Util.SynsetMaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chien on 12/03/2018.
 */
@Component
@Data
public class SynsetMakerImpl implements SynsetMaker {

    /**
     * @input list synset and word english serch in file data and index
     * @output mapSynset contain two map
     * @map1 contain id of synset -> word of synset
     * @map2 contain word of synset -> all of synset it contain this word
     * @param resultList
     * @return
     */
    @Override
    public MapSynset makeMapSynset(List<Result> resultList) {
        Map<String, Map<String, List<String>>> map = new HashMap<>(0);
        Map<String, List<String>> mapWordAndId = new HashMap<>(0);
        for (Result result : resultList) {
            Map<String, List<String>> mapWordform = new HashMap<>();
            for (String s : result.getWords()) {
                mapWordform.put(s, new ArrayList<>(0));
                if (mapWordAndId.containsKey(s)) {
                    mapWordAndId.get(s).add(result.getResultId());
                } else {
                    List<String> list = new ArrayList<>(0);
                    list.add(result.getResultId());
                    mapWordAndId.put(s, list);
                }
            }
            map.put(result.getResultId(), mapWordform);
        }
        MapSynset mapSynset = new MapSynset();
        mapSynset.setMapWordAndId(mapWordAndId);
        mapSynset.setMapWordOfSynset(map);
        return mapSynset;
    }
}
