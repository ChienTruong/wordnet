package wordnet.App.Util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by chien on 03/04/2018.
 */
public abstract class DataFile {

    protected List<String> list;

    protected DataFile() {
        try {
            this.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read() throws IOException {
        Stream stream = Files.lines(Paths.get(getNameFile()));
        list = new ArrayList<>(0);
        stream.forEach(o -> list.add(String.valueOf(o)));
    }

    protected abstract String getNameFile();


}
