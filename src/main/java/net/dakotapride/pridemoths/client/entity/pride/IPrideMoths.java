package net.dakotapride.pridemoths.client.entity.pride;

import java.util.Calendar;

public interface IPrideMoths {

    static boolean isWorldMothWeek() {
        return week() == 4;
    }

    static int week() {
        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

}
