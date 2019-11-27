package ru.javawebinar.topjava.web.meal.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalTime;
import java.util.Locale;

public class LocalTimeFormatter implements Formatter<LocalTime> {
    @Override
    public LocalTime parse(String text, Locale locale) throws ParseException {
        return text != null && !text.isEmpty() ? LocalTime.parse(text) : null;
    }

    @Override
    public String print(LocalTime object, Locale locale) {
        return object.toString();
    }
}
