package wordnet.ProcessDataInput.Action.Read.Impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import wordnet.ProcessDataInput.Action.Read.ReadFileEV;
import wordnet.Util.PathFile;
import wordnet.Util.Regex;

import java.io.IOException;
import java.util.*;

/**
 * Created by chien on 15/03/2018.
 */
@Component
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
                        if (this.line.contains(" ")) {
                            this.line = this.line.replaceAll(" ", "_");
                        }
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
                                map.put(wordCurrent.getWord(), meanList);
                            }
                            if (!this.line.isEmpty()) {
                                map.get(wordCurrent.getWord()).add(this.line);
                            }
                        }
                        break;
                }
            }
        }
        // limited null pointer
        // bởi vì có những từ không có trong từ điển tiếng việt
        for (String s : setWord) {
            if (!map.containsKey(s)) {
                map.put(s, new ArrayList<>(0));
            }
        }
        // end limited
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
