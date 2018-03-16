package wordnet.App.Version1;

import wordnet.App.Dto.MapSynset;
import wordnet.App.Dto.Result;
import wordnet.App.Util.FinderInFIle;
import wordnet.App.Util.Impl.FinderInFileImpl;
import wordnet.App.Util.Impl.SynsetMakerImpl;
import wordnet.App.Util.SynsetMaker;

import java.io.IOException;
import java.util.*;

/**
 * Created by chien on 12/03/2018.
 */
public class Test {

    private static List<Result> resultList = new ArrayList<>(0);
    private static MapSynset mapSynset;
    private static SynsetMaker synsetMaker = new SynsetMakerImpl();
    private static FinderInFIle finderInFIle = new FinderInFileImpl();

    static {
        resultList.add(new Result("05628636", Arrays.asList("incredulity", "disbelief", "skepticism", "mental rejection")));
        resultList.add(new Result("04728769", Arrays.asList("implausibility", "implausibleness")));
        mapSynset = synsetMaker.makeMapSynset(resultList);
        try {
            finderInFIle.findVietnameseForMapSynset(mapSynset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        doSomeThing();
    }

    private static void doSomeThing() {
        String idSynset  = "05628636";
        Map<String, List<String>> synset = mapSynset.getMapWordOfSynset().get(idSynset);

    }
}
