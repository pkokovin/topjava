package ru.javawebinar.topjava.web.meal.formatters;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return text != null && !text.isEmpty() ? LocalDate.parse(text) : null;
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return object.toString();
    }
}
