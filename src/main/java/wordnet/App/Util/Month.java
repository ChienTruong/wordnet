package wordnet.App.Util;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chien on 25/03/2018.
 */
public class Month {
    private static final List<String> listMonth = new ArrayList<>(24);

    static {
        String[] stringsMonth = DateFormatSymbols.getInstance().getMonths();
        String[] stringsMonthShort = DateFormatSymbols.getInstance().getShortMonths();
        for (String s : stringsMonth) {
            listMonth.add(s);
        }
        for (String s : stringsMonthShort) {
            listMonth.add(s);
        }
    }

    public static boolean verifyIsMonth(String input) {
        return listMonth.contains(input);
    }
}
