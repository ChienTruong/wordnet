package wordnet.App.Service.Impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by chien on 19/03/2018.
 */
public class CalculateDice {

    /**
     * @param stringOne
     * @param stringTwo
     * @return
     * công thức: (2 * (giao chưa 2 chuỗi)) / (số từ của chuỗi 1) + (số từ của chuỗi 2)
     */
    public static float calculate(String stringOne, String stringTwo) {
        String[] stringsOne = stringOne.trim().split(" ");
        String[] stringsTwo = stringTwo.trim().split(" ");
        Set<String> setString = new HashSet<>(0);
        for (String sOne : stringsOne) {
            for (String sTwo : stringsTwo) {
                if (sOne.equals(sTwo)) {
                    setString.add(sOne);
                }
            }
        }
        float result = Float.valueOf(2 * (setString.size())) / (stringsOne.length + stringsTwo.length);
        return result;
    }

}
