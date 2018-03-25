package wordnet.App.Util;

import wordnet.App.Service.Impl.ChooseCaseOne;
import wordnet.App.Service.Impl.ChooseCaseThree;
import wordnet.App.Service.Impl.ChooseCaseTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chien on 20/03/2018.
 */
public class NameStrategy {
    public static final List<Class> classList = new ArrayList<>(0);

    static {
        classList.add(ChooseCaseOne.class);
        classList.add(ChooseCaseTwo.class);
        classList.add(ChooseCaseThree.class);
    }

}
