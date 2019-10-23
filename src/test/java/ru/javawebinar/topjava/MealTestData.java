package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealTestData {

    public final static int MEAL_TEST_ID = 100002;
    public final static Meal USER_MEAL_01 = new Meal(100002, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "User Breakfast", 500);
    public final static Meal USER_MEAL_02 = new Meal(100003, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "User Dinner", 1000);
    public final static Meal USER_MEAL_03 = new Meal(100004, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "User Supper", 500);
    public final static Meal USER_MEAL_04 = new Meal(100005, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "User Breakfast", 500);
    public final static Meal USER_MEAL_05 = new Meal(100006, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "User Dinner", 1000);
    public final static Meal USER_MEAL_06 = new Meal(100007, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "User Supper", 510);
    public final static Meal ADMIN_MEAL_01 = new Meal(100008, LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Admin Breakfast", 500);
    public final static Meal ADMIN_MEAL_02 = new Meal(100009, LocalDateTime.of(2016, Month.MAY, 30, 13, 0), "Admin Dinner", 1000);
    public final static Meal ADMIN_MEAL_03 = new Meal(100010, LocalDateTime.of(2016, Month.MAY, 30, 20, 0), "Admin Supper", 500);
    public final static Meal ADMIN_MEAL_04 = new Meal(100011, LocalDateTime.of(2016, Month.MAY, 31, 10, 0), "Admin Breakfast", 500);
    public final static Meal ADMIN_MEAL_05 = new Meal(100012, LocalDateTime.of(2016, Month.MAY, 31, 13, 0), "Admin Dinner", 1000);
    public final static Meal ADMIN_MEAL_06 = new Meal(100013, LocalDateTime.of(2016, Month.MAY, 31, 20, 0), "Admin Supper", 510);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
