package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import static ru.javawebinar.topjava.util.TimeUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceededStreamOneFlow(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDaySum = new HashMap<>();
        for (UserMeal meal : mealList) {
            caloriesPerDaySum.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (a, b) -> a + b);
        }
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                UserMealWithExceed userMealWithExceed = new UserMealWithExceed(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        caloriesPerDaySum.get(meal.getDateTime().toLocalDate()) > caloriesPerDay);
                userMealWithExceedList.add(userMealWithExceed);
            }
        }
        return userMealWithExceedList;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDaySum = mealList
                .stream()
                .collect(Collectors.toMap(
                        x -> x.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        Integer::sum));
        return mealList.stream()
                .filter(x -> isBetween(x.getDateTime().toLocalTime(), startTime, endTime))
                .map(x -> new UserMealWithExceed(
                        x.getDateTime(),
                        x.getDescription(),
                        x.getCalories(),
                        caloriesPerDaySum.get(x.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededStreamOneFlow(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        return mealList.stream()
                .collect(Collectors.groupingBy(x -> x.getDateTime().toLocalDate()))
                .entrySet()
                .stream()
                .map(x -> x.getValue().stream()
                        .filter(p -> isBetween(p.getDateTime().toLocalTime(), startTime, endTime))
                        .map(n -> new UserMealWithExceed(
                                n.getDateTime(),
                                n.getDescription(),
                                n.getCalories(),
                                x.getValue().stream().collect(Collectors.summingInt(UserMeal::getCalories)) > caloriesPerDay
                        ))

                )
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }
}
