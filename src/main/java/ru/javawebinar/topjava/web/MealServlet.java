package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceMapImpl;
import static ru.javawebinar.topjava.util.MealsUtil.*;

import static org.slf4j.LoggerFactory.getLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String JSP = "/WEB-INF/meals.jsp";
    private static MealService service = new MealServiceMapImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        List<MealTo> mealsToList = getFiltered(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsTo", mealsToList);
//        request.setAttribute("formatter", MealTo.FORMATTER);
        request.getRequestDispatcher(JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("save");
        request.setCharacterEncoding("UTF-8");
        String dateTime = request.getParameter("datetime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
        int cal = Integer.valueOf(calories);
        Meal meal = new Meal(localDateTime, description, cal);
        service.add(meal);
        doGet(request, response);
    }
}
