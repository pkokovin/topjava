package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.dao.MealStorage;

import java.util.List;

public class MealServiceMapImpl implements MealService {
    MealStorage storage = MealStorage.getInstance();
    @Override
    public void add(Meal meal) {
        storage.createOrUpdate(meal);
    }

    @Override
    public void delete(long id) {
        storage.delete(id);
    }

    @Override
    public void update(Meal meal) {
        storage.createOrUpdate(meal);
    }

    @Override
    public List<Meal> getAll() {
        return storage.get();
    }

    @Override
    public Meal getById(long mealId) {
        return storage.getById(mealId);
    }
}
