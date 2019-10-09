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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealEditServlet extends HttpServlet {
    private static final Logger log = getLogger(MealEditServlet.class);
    private static MealService service = new MealServiceMapImpl();
    private static final String JSP = "/WEB-INF/edit.jsp";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("edit");
        request.setCharacterEncoding("UTF-8");
        String idS = request.getParameter("id");
        long id = Long.valueOf(idS);
        Meal meal = service.getById(id);
        request.setAttribute("meal", meal);
        request.getRequestDispatcher(JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("update");
        request.setCharacterEncoding("UTF-8");
        String dateTime = request.getParameter("datetime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        String idStr = request.getParameter("id");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
        int cal = Integer.valueOf(calories);
        Meal meal = new Meal(localDateTime, description, cal);
        meal.setId(Long.valueOf(idStr));
        service.update(meal);
        localRedirect(request, response, "/meals");
    }
}
