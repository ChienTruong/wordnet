package wordnet.App.Util;

import wordnet.Util.PathFile;

/**
 * Created by chien on 03/04/2018.
 */
public class DataFileSyllable extends DataFile {



    public DataFileSyllable() {
        super();
    }

    @Override
    protected String getNameFile() {
        return PathFile.fileSpecial;
    }
}
