package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
//        MealsUtil.MEALS.forEach(this::save);
        for (Meal meal: MealsUtil.MEALS) {
//            meal.setUserId(1);
            save(1, meal);
        }
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        if (meal.getUserId() == userId) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        if (repository.get(id).getUserId() == userId) {
            return repository.remove(id) != null;
        } else return false;
    }

    @Override
    public Meal get(int userId, int id) {
        if (repository.get(id).getUserId() == userId) {
            return repository.get(id);
        } else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> meals = repository.values()
                .stream()
                .filter(x -> x.getUserId() == userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
        if (meals.size() > 0) {
            return meals;
        } else return Collections.emptyList();
    }
}

