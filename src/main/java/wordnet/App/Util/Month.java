package wordnet.App.Util;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chien on 25/03/2018.
 */
public class Month {

    private static final List<String> listMonth = new ArrayList<>(24);
    private static List<String> listFullName = new ArrayList<>(12);
    private static List<String> listShortName = new ArrayList<>(12);

    static {
        listFullName = Arrays.asList(DateFormatSymbols.getInstance().getMonths());
        listShortName = Arrays.asList(DateFormatSymbols.getInstance().getShortMonths());
        for (String s : listFullName) {
            listMonth.add(s);
        }
        for (String s : listShortName) {
            listMonth.add(s);
        }
    }

    public static boolean verifyIsMonth(String input) {
        return listMonth.contains(input);
    }

    public static String getFullNameFromShortName(String input) {
        String result = null;
        for (String s : listFullName) {
            if (s.startsWith(input)) {
                result = s;
                break;
            }
        }
        return result;
    }

    public static boolean verifyIsMonthShortName(String input) {
        return listShortName.contains(input);
    }
}
