package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.moc.MealStorage;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealServiceMapImpl implements MealService {
    MealStorage storage = MealStorage.getInstance();
    @Override
    public void add(Meal meal) {
        storage.createOrUpdate(meal);
    }

    @Override
    public void delete(Meal meal) {
        storage.delete(meal);
    }

    @Override
    public void update(Meal meal) {
        storage.createOrUpdate(meal);
    }

    @Override
    public List<Meal> getAll() {
        return storage.get()
                .entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }

    @Override
    public Meal getById(long mealId) {
        return storage.get()
                .entrySet()
                .stream()
                .filter(x -> x.getKey() == mealId)
                .map(Map.Entry::getValue)
                .findFirst()
                .get();
    }
}
