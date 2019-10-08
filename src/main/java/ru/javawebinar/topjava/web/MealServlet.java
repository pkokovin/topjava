package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTestData;
import ru.javawebinar.topjava.model.MealTo;

import static ru.javawebinar.topjava.util.MealsUtil.*;

import static org.slf4j.LoggerFactory.getLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String JSP = "/WEB-INF/meals.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        List<MealTo> mealsToList = listAllWithExcessFlag(MealTestData.getInstance().getMeals(), 2000);
        request.setAttribute("mealsTo", mealsToList);
        request.getRequestDispatcher(JSP).forward(request, response);
    }

}
