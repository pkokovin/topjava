package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService {
    void add(Meal meal);
    void delete(long id);
    void update(Meal meal);
    List<Meal> getAll();
    Meal getById(long mealId);
}
