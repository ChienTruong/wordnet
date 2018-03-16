package wordnet.App.Version2.Action.Impl;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by chien on 15/03/2018.
 */
@Data
public abstract class AttributeFile {

    protected BufferedReader bufferedReader;
    protected String line;

    protected void connect() throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(getPathFile()));
    }

    protected abstract String getPathFile();

}
