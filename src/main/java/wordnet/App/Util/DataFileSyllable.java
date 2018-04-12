package wordnet.App.Util;

import wordnet.Util.PathFile;

/**
 * Created by chien on 03/04/2018.
 */
public class DataFileSyllable extends DataFile {



    public DataFileSyllable() {
        super();
    }

    public String getMeanOfSynset(String synsetId) {
        String mean = "";
        for (String s : list) {
            if (s.substring(0, 8).equals(synsetId)) {
                mean = s.substring(9, s.length());
                break;
            }
        }
        return mean;
    }
    @Override
    protected String getNameFile() {
        return PathFile.fileSpecial;
    }
}
