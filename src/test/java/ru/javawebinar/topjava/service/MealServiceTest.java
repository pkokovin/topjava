package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

import ru.javawebinar.topjava.*;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;


    @Test
    public void get() throws Exception {
        Meal mealExp = new Meal(MEAL_TEST_ID, USER.getId(),
                LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                "User Breakfast",
                500);
        Meal meal = service.get(MEAL_TEST_ID, USER.getId());
        assertMatch(meal, mealExp);
    }

    @Test(expected = NotFoundException.class)
    public void getAnotherUserMeal() throws Exception {
        service.get(MEAL_TEST_ID, ADMIN.getId());
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_TEST_ID, USER.getId());
        assertMatch(USER_MEALS_DELETED, service.getAll(USER.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() throws Exception {
        service.delete(MEAL_TEST_ID, ADMIN.getId());
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> mealsExp = USER_MEALS_BTW;
        List<Meal> mealsAct = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 31), USER.getId());
        assertMatch(mealsAct, mealsExp);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> mealsExp = USER_MEALS;
        List<Meal> mealsAct = service.getAll(USER.getId());
        assertMatch(mealsAct, mealsExp);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL_TEST_ID, USER.getId(),
                LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                "User Breakfast",
                500);
        updated.setDescription("Updated");
        updated.setCalories(800);
        service.update(updated, USER.getId());
        assertMatch(service.get(MEAL_TEST_ID, USER.getId()), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateAnotherUserMeal() throws Exception {
        Meal updated = new Meal(MEAL_TEST_ID, USER.getId(),
                LocalDateTime.of(2015, Month.MAY, 30, 10, 0),
                "User Breakfast",
                500);
        updated.setDescription("Updated");
        updated.setCalories(800);
        service.update(updated, ADMIN.getId());
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.of(2019, Month.MAY, 30, 10, 0),
                "Test Meal",
                500);
        Meal created = service.create(newMeal, USER.getId());
        newMeal.setUserId(USER.getId());
        newMeal.setId(created.getId());
        assertMatch(service.get(created.getId(), USER.getId()), newMeal);
    }
}