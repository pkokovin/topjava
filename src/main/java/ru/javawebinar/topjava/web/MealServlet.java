package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceMapImpl;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.helpers.RedirectHelper.*;
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
    private static final String JSP2 = "/WEB-INF/edit.jsp";
    private static MealService service = new MealServiceMapImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        request.setCharacterEncoding("UTF-8");
        String act = request.getParameter("action");
        if (act != null) {
            if (act.equals("delete")) {
                service.delete(Long.valueOf(request.getParameter("id")));
            }
            if (act.equals("update")) {
                String idS = request.getParameter("id");
                long id = Long.valueOf(idS);
                Meal meal = service.getById(id);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(JSP2).forward(request, response);
            }
        }
        List<MealTo> mealsToList = getFiltered(service.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsTo", mealsToList);
//        request.setAttribute("formatter", MealTo.FORMATTER);
        request.getRequestDispatcher(JSP).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("save");
        request.setCharacterEncoding("UTF-8");
        String idS = request.getParameter("id");
        String dateTime = request.getParameter("datetime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
        int cal = Integer.valueOf(calories);
        Meal meal = new Meal(localDateTime, description, cal);
        if (idS != null) {
            meal.setId(Long.valueOf(idS));
        }
        service.add(meal);
        localRedirect(request, response, "/meals");
    }
}
