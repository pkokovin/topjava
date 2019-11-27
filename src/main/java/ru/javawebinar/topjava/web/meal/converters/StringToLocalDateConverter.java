package ru.javawebinar.topjava.web.meal.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {
    @Override
    public LocalDate convert(String source) {
        return source != null && !source.isEmpty() ? LocalDate.parse(source) : null;
    }
}
