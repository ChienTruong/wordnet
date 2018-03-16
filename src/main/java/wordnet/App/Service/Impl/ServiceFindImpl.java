package wordnet.App.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wordnet.App.Dto.BodyFind;
import wordnet.App.Dto.MapSynset;
import wordnet.App.Dto.Result;
import wordnet.App.Dto.SynsetMean;
import wordnet.App.Service.ServiceFind;
import wordnet.App.Util.FinderInFIle;
import wordnet.App.Util.FinderStrategy;
import wordnet.App.Util.Impl.FinderStrategyOneImpl;
import wordnet.App.Util.SynsetMaker;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by chien on 27/02/2018.
 */
@Service
public class ServiceFindImpl implements ServiceFind {

    private FinderInFIle finderInFIle;
    private SynsetMaker synsetMaker;

    @Autowired
    public ServiceFindImpl(FinderInFIle finderInFIle, SynsetMaker synsetMaker) {
        this.finderInFIle = finderInFIle;
        this.synsetMaker = synsetMaker;
    }

    @Override
    public List<Result> findSynset(BodyFind bodyFind) throws IOException {
        List<Result> listResult = finderInFIle.find(bodyFind);
        MapSynset mapSynset = synsetMaker.makeMapSynset(listResult);
        finderInFIle.findVietnameseForMapSynset(mapSynset);
        Set<String> setKey = mapSynset.getMapWordOfSynset().keySet();
        for (String s : setKey) {
            SynsetMean synsetMean = new SynsetMean();
            synsetMean.setFinderStrategy(new FinderStrategyOneImpl());
            synsetMean.setMapWord(mapSynset.getMapWordOfSynset().get(s));
            System.out.println(synsetMean.getMeanOfSynset());
        }
        return listResult;
    }

}
