package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcMealRepository implements MealRepository {


    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("meal_id");
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("meal_id", meal.getId())
                .addValue("user_id", userId)
                .addValue("date_time", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number newkey = insertMeal.executeAndReturnKey(map);
            meal.setId(newkey.intValue());
        } else if (jdbcTemplate.update("UPDATE meals set date_time =?, description =?, calories =? where meal_id =? and user_id =?",
                meal.getDateTime(),
                meal.getDescription(),
                meal.getCalories(),
                meal.getId(),
                userId) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("delete from meals where meal_id = ? and user_id = ?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * from meals where meal_id=? and user_id=?", (rs, rowNum) -> new Meal(
                rs.getInt("meal_id"),
                rs.getTimestamp("date_time").toLocalDateTime(),
                rs.getString("description"),
                rs.getInt("calories")
        ), id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("select * from meals where user_id = ? order by date_time desc", (rs, rowNum) -> new Meal(
                rs.getInt("meal_id"),
                rs.getTimestamp("date_time").toLocalDateTime(),
                rs.getString("description"),
                rs.getInt("calories")
        ), userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> meals = getAll(userId);
        return meals == null ? Collections.emptyList() :
                meals.stream()
                        .filter(meal -> Util.isBetweenInclusive(meal.getDateTime(), startDate, endDate))
                        .collect(Collectors.toList());
    }
}
