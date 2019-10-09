package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceMapImpl;
import static ru.javawebinar.topjava.helpers.RedirectHelper.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class MealDeleteServlet extends HttpServlet {
    private static final Logger log = getLogger(MealDeleteServlet.class);
    private static MealService service = new MealServiceMapImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Meal meal = service.getById(Long.valueOf(id));
        service.delete(meal);
        redirectBack(request, response);
    }
}
