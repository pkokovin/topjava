package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static com.sun.deploy.config.JREInfo.getAll;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            List<MealTo> mealsTo = mealRestController.getFiltered(1, LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 31), LocalTime.of(13, 00), LocalTime.of(20, 00));
            mealsTo.forEach(System.out::println);

            Meal meal = mealRestController.get(1, 6);
            System.out.println("meal = " + meal);
            LocalDateTime time = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            Meal ml = new Meal(time, "", 1000);
            ml.setUserId(2);
            mealRestController.save(2, ml);
            List<Meal> finList = mealRestController.getAll(1);
            finList.forEach(System.out::println);
            finList = mealRestController.getAll(2);
            finList.forEach(System.out::println);
            try {
                ml = mealRestController.get(1, 9);
                System.out.println(ml);
            } catch (NotFoundException e) {
                System.err.print(e.toString());
            }
            ml = mealRestController.get(2, 9);
            System.out.println(ml);


        }

    }
}
