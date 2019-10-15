package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import static ru.javawebinar.topjava.util.MealsUtil.*;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return service.getAll(userId);
    }

    public List<MealTo> getFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("getFiltered");
        startDate = (startDate == null) ? LocalDate.MIN : startDate;
        endDate = (endDate == null) ? LocalDate.MAX : endDate;
        startTime = (startTime == null) ? LocalTime.MIN : startTime;
        endTime = (endTime == null) ? LocalTime.MAX : endTime;
        return getFilteredTos(service.getAllFiltered(userId, startDate, endDate), DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

    public Meal get(int userId, int id) {
        log.info("get {}", id);
        return service.get(userId, id);
    }

    public Meal save(int userId, Meal meal) {
        log.info("create {}", meal);
        try {
            checkNew(meal);
        } catch (IllegalArgumentException e) {
            update(userId, meal, meal.getId());
        }

        return service.create(userId, meal);
    }

    public void delete(int userId, int id) {
        log.info("delete {}", id);
        service.delete(userId, id);
    }

    private void update(int userId, Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(userId, meal);
    }
}