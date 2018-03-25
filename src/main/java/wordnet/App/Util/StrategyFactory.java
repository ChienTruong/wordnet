package wordnet.App.Util;

import wordnet.App.Service.ChooseMeanOfSynset;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chien on 20/03/2018.
 */
public class StrategyFactory {

    private static Map<String, ChooseMeanOfSynset> mapStrategy = new HashMap<>(0);

    public static <C extends ChooseMeanOfSynset> ChooseMeanOfSynset getStrategy(Class<C> cClass) throws IllegalAccessException, InstantiationException {
        if (!mapStrategy.containsKey(cClass.getSimpleName())) {
            mapStrategy.put(cClass.getSimpleName(), cClass.newInstance());
        }
        return mapStrategy.get(cClass.getSimpleName());
    }
}
