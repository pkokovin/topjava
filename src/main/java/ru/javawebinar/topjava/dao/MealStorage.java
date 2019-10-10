package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MealStorage {
    private static MealStorage ourInstance = new MealStorage();

    public static synchronized MealStorage getInstance() {
        if (ourInstance == null) {
            ourInstance = new MealStorage();
        }
        return ourInstance;
    }

    private MealStorage() {
    }
    private Meal m1 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    private Meal m2 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    private Meal m3 = new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    private Meal m4 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000);
    private Meal m5 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500);
    private Meal m6 = new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    private Map<Long, Meal> meals = new ConcurrentHashMap<Long, Meal>() {{
        m1.setId(UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE);
        m2.setId(UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE);
        m3.setId(UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE);
        m4.setId(UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE);
        m5.setId(UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE);
        m6.setId(UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE);
        put(m1.getId(), m1);
        put(m2.getId(), m2);
        put(m3.getId(), m3);
        put(m4.getId(), m4);
        put(m5.getId(), m5);
        put(m6.getId(), m6);
    }};

    public List<Meal> get() {
        return new ArrayList<>(meals.values());
    }

    public void createOrUpdate(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE);
            meals.put(meal.getId(), meal);
        } else meals.put(meal.getId(), meal);
    }

    public void delete(long id) {
        meals.remove(id);
    }

    public Meal getById(long id) {
        return meals.getOrDefault(id, null);
    }

}
