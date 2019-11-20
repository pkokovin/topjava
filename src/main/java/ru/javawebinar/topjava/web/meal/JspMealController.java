package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController{

    @GetMapping("")
    public String list(HttpServletRequest request, Model model) {
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(Integer.parseInt(id), userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        log.info("create view {} for user {}", meal, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/update/{id}")
    public String updateGet(@PathVariable String id, Model model) {
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(Integer.parseInt(id), userId);
        log.info("update view {} for user {}", meal, userId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("")
    public String createOrUpdate(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            log.info("create {} for user {}", meal, userId);
            service.create(meal, userId);
        } else {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            log.info("create {} for user {}", meal, userId);
            service.update(meal, userId);
        }
        return "redirect:/meals";
    }
}
