package net.dakotapride.pridemoths.client.entity.pride;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public interface IPrideMoths {
    LocalDate date = LocalDate.now();
    int getLocalDayFromUser = date.get(ChronoField.DAY_OF_MONTH);
    int getLocalMonthFromUser = date.get(ChronoField.MONTH_OF_YEAR);

     static boolean isWorldMothWeek() {
        return getLocalDayFromUser >= 25 && getLocalMonthFromUser == 7;
    }
}
