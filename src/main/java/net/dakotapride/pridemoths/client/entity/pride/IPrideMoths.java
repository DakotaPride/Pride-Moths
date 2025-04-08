package net.dakotapride.pridemoths.client.entity.pride;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.List;

public interface IPrideMoths {
    LocalDate localDate = LocalDate.now();
    Calendar calendar = Calendar.getInstance();
    int day_of_month = localDate.get(ChronoField.DAY_OF_MONTH);
    int week_of_month = calendar.get(Calendar.WEEK_OF_MONTH);
    int month_of_year = localDate.get(ChronoField.MONTH_OF_YEAR);

    boolean isJan = month_of_year == 1;
    boolean isFeb = month_of_year == 2;
    boolean isMarch = month_of_year == 3;
    boolean isApril = month_of_year == 4;
    boolean isMay = month_of_year == 5;
    boolean isJune = month_of_year == 6;
    boolean isJuly = month_of_year == 7;
    boolean isAug = month_of_year == 8;
    boolean isSep = month_of_year == 9;
    boolean isOct = month_of_year == 10;
    boolean isNov = month_of_year == 11;
    boolean isDec = month_of_year == 12;

    static boolean isWorldMothWeek() {
        return week_of_month == 4 && isJuly;
    }

    static boolean isTransgenderDayOfVisibility() {
        return day_of_month == 31 && isMarch;
    }

    static boolean isAgenderDayOfVisibility() {
        return day_of_month == 19 && isMay;
    }

    static boolean isAromanticDayOfVisibility() {
        return day_of_month == 5 && isJune;
    }

    static boolean isAsexualDayOfVisibility() {
        return day_of_month == 6 && isApril;
    }

    static boolean isBisexualDayOfVisibility() {
        return day_of_month == 23 && isSep;
    }

    static boolean isLesbianDayOfVisibility() {
        return day_of_month == 26 && isApril;
    }

    static boolean isGayDayOfVisibility() {
        return day_of_month == 25 && isMarch;
    }

    static boolean isPansexualDayOfVisibility() {
        return day_of_month == 24 && isMay;
    }

    static boolean isPolyamorousDayOfVisibility() {
        return day_of_month == 23 && isNov;
    }

    static boolean isPolysexualDayOfVisibility() {
        return day_of_month == 26 && isJuly;
    }

    static boolean isOmnisexualDayOfVisibility() {
        return day_of_month == 6 && isJuly;
    }

    static boolean isDemigenderDayOfVisibility() {
        return day_of_month == 15 && isDec;
    }

    static boolean isIntersexDayOfVisibility() {
        return day_of_month == 26 && isOct;
    }

    static boolean isXenogenderDayOfVisibility() {
        return day_of_month == 15 && isMay;
    }

    static boolean isGenderQueerDayOfVisibility() {
        return day_of_month == 25 && isApril;
    }


    List<Integer> k = List.of(17, 18, 19, 20, 21, 22, 23, 24);

    // To my knowledge, no individual day exists for genderfluid visibility
    static boolean isGenderfluidWeekOfVisibility() {
        for (Integer i : k) {
            if (day_of_month == i && isOct) {
                return true;
            }
        }
        return false;
    }

}
