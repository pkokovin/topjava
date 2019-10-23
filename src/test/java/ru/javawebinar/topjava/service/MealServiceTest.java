package ru.javawebinar.topjava.service;


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

import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-app-db.xml",
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

    List<Meal> meals = Arrays.asList(USER_MEAL_01, USER_MEAL_02, USER_MEAL_03, USER_MEAL_04, USER_MEAL_05, USER_MEAL_06);
    List<Meal> meals_removed = Arrays.asList(USER_MEAL_02, USER_MEAL_03, USER_MEAL_04, USER_MEAL_05, USER_MEAL_06);


    @Autowired
    private MealService service;


    @Test
    public void get() throws Exception {
        Meal mealExp = USER_MEAL_01;
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
        meals_removed.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(meals_removed, service.getAll(USER.getId()));
    }

    @Test(expected = NotFoundException.class)
    public void deleteAnotherUserMeal() throws Exception {
        service.delete(MEAL_TEST_ID, ADMIN.getId());
    }

    @Test
    public void getBetweenDates() throws Exception {
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        List<Meal> mealsAct = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 31), USER.getId());
        assertMatch(mealsAct, meals);
    }

    @Test
    public void getAll() throws Exception {
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        List<Meal> mealsAct = service.getAll(USER.getId());
        assertMatch(mealsAct, meals);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL_TEST_ID,
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
        Meal updated = new Meal(MEAL_TEST_ID,
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
        newMeal.setId(created.getId());
        assertMatch(service.get(created.getId(), USER.getId()), newMeal);
    }
}