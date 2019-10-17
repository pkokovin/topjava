package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.AbstractChronology;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
//        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
//    }
//    public static boolean isBetweenDates(LocalDate lt, LocalDate startDate, LocalDate endDate) {
//        return lt.compareTo(startDate) >= 0 && lt.compareTo(endDate) <= 0;
//    }

    public static <T extends Temporal> boolean isBetween(T ldt, T start, T end) {
        Class type = ldt.getClass();
        if (type == LocalTime.class) {
            LocalTime ltCheck = LocalTime.from(ldt);
            LocalTime startCheck = LocalTime.from(start);
            LocalTime endCheck = LocalTime.from(end);
            return ltCheck.compareTo(startCheck) >= 0 && ltCheck.compareTo(endCheck) <= 0;
        }
        if (type == LocalDate.class) {
            LocalDate ldCheck = LocalDate.from(ldt);
            LocalDate startCheck = LocalDate.from(start);
            LocalDate endCheck = LocalDate.from(end);
            return ldCheck.compareTo(startCheck) >= 0 && ldCheck.compareTo(endCheck) <= 0;
        }
        return false;
    }


    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

