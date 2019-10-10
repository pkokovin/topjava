<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="${pageContext.servletContext.contextPath}/index.html">Home</a></h3>
<hr>
<form action="${pageContext.servletContext.contextPath}/meals" method="post">
    <table>
        <legend> Редактирование приемов пищи </legend>
        <input type="hidden"
               name="id"
               value="${meal.id}">
        <tr>
            <td><b>Дата</b></td>
            <td><input type="datetime-local" name="datetime" value="${meal.dateTime}"></td>
        </tr>

        <tr>
            <td><b>Описание</b></td>
            <td><input type="text" name="description" value="${meal.description}"></td>
        </tr>

        <tr>
            <td><b>Калории</b></td>
            <td><input type="number" name="calories" value="${meal.calories}"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Сохранить"></td>
        </tr>
    </table>
</form>

</body>
</html>
