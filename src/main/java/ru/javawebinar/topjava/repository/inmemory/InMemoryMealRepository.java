package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.*;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {

        for (Meal meal : MealsUtil.MEALS) {
            save(1, meal);
        }

        for (Meal meal : MealsUtil.MEALS2) {
            save(2, meal);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            Map<Integer, Meal> innerMap = repository.get(userId);
            if (innerMap == null) {
                innerMap = new ConcurrentHashMap<>();
                innerMap.put(meal.getId(), meal);
            } else {
                innerMap.put(meal.getId(), meal);
            }
            repository.put(userId, innerMap);
            return meal;
        }
        // treat case: update, but not present in storage
        Meal userMealToUpdate = repository.get(userId).get(meal.getId());

        if (userMealToUpdate != null && userMealToUpdate.getUserId() == userId) {
            return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        Meal toDel = repository.get(userId).get(id);
        if (toDel != null && toDel.getUserId() == userId) {
            return repository.get(userId).remove(id) != null;
        } else return false;
    }

    @Override
    public Meal get(int userId, int id) {
        Meal toGet = repository.get(userId).get(id);

        return toGet = toGet != null && toGet.getUserId() == userId ? toGet : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId).values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    private List<Meal> getAllCommon(int userId, Predicate<Meal> filter) {
        return repository.get(userId)
                .values()
                .stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllCommon(userId, x -> DateTimeUtil.isBetween(x.getDate(), startDate, endDate));
    }

}

