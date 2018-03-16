package wordnet.App.Version2.Action.Impl;

import lombok.Data;
import wordnet.App.Util.PathFile;
import wordnet.App.Util.Regex;
import wordnet.App.Version2.Action.ReadFileEV;

import java.io.IOException;
import java.util.*;

/**
 * Created by chien on 15/03/2018.
 */
public class ReadFileEVImpl extends AttributeFile implements ReadFileEV {

    @Override
    public Map<String, List<String>> read(Set<String> setWord) throws IOException {
        this.connect();
        Map<String, List<String>> map = new HashMap<>(0);
        // Do
        int countWord = setWord.size();
        int n = 0;
        WordCurrent wordCurrent = new WordCurrent();
        while (true) {
            this.line = this.bufferedReader.readLine();
            if (this.line == null || n == countWord + 1) {
                break;
            }
            if (!this.line.isEmpty()) {
                String firstCharacter = this.line.substring(0, 1);
                switch (firstCharacter) {
                    case "@":
                        this.line = this.line.substring(1, line.length());
                        wordCurrent.setWord(this.line);
                        if (setWord.contains(this.line) || countWord == n) {
                            n++;
                        }
                        break;
                    case "-":
                        if (setWord.contains(wordCurrent.getWord())) {
                            String[] strings = this.line.substring(1, this.line.length()).replaceAll(Regex.regexBracket, "").split(",");
                            this.line = String.join(", ", strings);
                            if (map.get(wordCurrent.getWord()) == null) {
                                List<String> meanList = new ArrayList<>(0);
                                meanList.add(this.line);
                                map.put(wordCurrent.getWord(), meanList);
                            } else {
                                map.get(wordCurrent.getWord()).add(this.line);
                            }
                        }
                        break;
                }
            }
        }
        // end Do
        this.bufferedReader.close();
        return map;
    }

    @Override
    protected String getPathFile() {
        return PathFile.fileDataEV;
    }

    @Data
    private class WordCurrent {
        private String word;
    }
    // test
//    public static void main(String[] args) throws IOException {
//        ReadFileEV readFileEV = new ReadFileEVImpl();
//        Set<String> setWord = new HashSet<>(2);
//        setWord.add("regulation");
//        setWord.add("way");
//        System.out.println(readFileEV.read(setWord));
//    }
}
