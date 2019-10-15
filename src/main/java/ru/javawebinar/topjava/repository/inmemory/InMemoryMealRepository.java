package ru.javawebinar.topjava.repository.inmemory;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.*;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Table<LocalDate, Integer, Meal> repository = HashBasedTable.create();
    private AtomicInteger counter = new AtomicInteger(0);

    {

        for (Meal meal : MealsUtil.MEALS) {
            save(1, meal);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getDate(), meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        Meal userTableMeal = repository.column(meal.getId())
                .values()
                .stream()
                .filter(x -> x.getId() == meal.getId())
                .findFirst()
                .get();

        if (userTableMeal != null && userTableMeal.getUserId() == userId) {
            return repository.put(userTableMeal.getDate(), userTableMeal.getId(), meal);
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        Meal toDelTable = repository
                .column(id)
                .values()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .get();
        if (toDelTable != null && toDelTable.getUserId() == userId) {
            return repository.remove(toDelTable.getDate(), id) != null;
        } else return false;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal toGetTable = repository
                .column(id)
                .values()
                .stream()
                .filter(x -> x.getId() == id)
                .findFirst()
                .get();
        return toGetTable = toGetTable != null && toGetTable.getUserId() == userId ? toGetTable : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values()
                .stream()
                .filter(x -> x.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        List<Meal> mealList = repository.rowMap().entrySet()
                .stream()
                .filter(x -> DateTimeUtil.isBetween(x.getKey(), startDate, endDate))
                .flatMap(x -> x.getValue().values().stream())
                .filter(x -> x.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        return mealList;
    }


}

