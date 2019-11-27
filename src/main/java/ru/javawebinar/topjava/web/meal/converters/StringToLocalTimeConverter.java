package ru.javawebinar.topjava.web.meal.converters;


import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String source) {
        return source != null && !source.isEmpty() ? LocalTime.parse(source) : null;
    }
}

