package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

    public final static int MEAL_TEST_ID = 100002;
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(100002, 100000, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "User Breakfast", 500),
            new Meal(100003, 100000, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "User Dinner", 1000),
            new Meal(100004, 100000, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "User Supper", 500),
            new Meal(100005, 100000, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "User Breakfast", 500),
            new Meal(100006, 100000, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "User Dinner", 1000),
            new Meal(100007, 100000, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "User Supper", 510),
            new Meal(100008, 100001, LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Admin Breakfast", 500),
            new Meal(100009, 100001, LocalDateTime.of(2016, Month.MAY, 30, 13, 0), "Admin Dinner", 1000),
            new Meal(100010, 100001, LocalDateTime.of(2016, Month.MAY, 30, 20, 0), "Admin Supper", 500),
            new Meal(100011, 100001, LocalDateTime.of(2016, Month.MAY, 31, 10, 0), "Admin Breakfast", 500),
            new Meal(100012, 100001, LocalDateTime.of(2016, Month.MAY, 31, 13, 0), "Admin Dinner", 1000),
            new Meal(100013, 100001, LocalDateTime.of(2016, Month.MAY, 31, 20, 0), "Admin Supper", 510)
    );

    public static final List<Meal> USER_MEALS = Arrays.asList(
            new Meal(100007, 100000, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "User Supper", 510),
            new Meal(100006, 100000, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "User Dinner", 1000),
            new Meal(100005, 100000, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "User Breakfast", 500),
            new Meal(100004, 100000, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "User Supper", 500),
            new Meal(100003, 100000, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "User Dinner", 1000),
            new Meal(100002, 100000, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "User Breakfast", 500)
    );

    public static final List<Meal> USER_MEALS_BTW = Arrays.asList(
            new Meal(100007, 100000, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "User Supper", 510),
            new Meal(100006, 100000, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "User Dinner", 1000),
            new Meal(100005, 100000, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "User Breakfast", 500),
            new Meal(100004, 100000, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "User Supper", 500),
            new Meal(100003, 100000, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "User Dinner", 1000),
            new Meal(100002, 100000, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "User Breakfast", 500)
    );

    public static final List<Meal> USER_MEALS_DELETED = Arrays.asList(
            new Meal(100007, 100000, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "User Supper", 510),
            new Meal(100006, 100000, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "User Dinner", 1000),
            new Meal(100005, 100000, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "User Breakfast", 500),
            new Meal(100004, 100000, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "User Supper", 500),
            new Meal(100003, 100000, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "User Dinner", 1000)
    );

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
