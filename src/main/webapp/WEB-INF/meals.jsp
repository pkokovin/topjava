<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.time.format.DateTimeFormatter" %>

<html>
<head>
    <title>Meals</title>
    <link href="${pageContext.request.contextPath}/resources/css/layout.css" rel="stylesheet">
</head>
<body>
<h3><a href="${pageContext.servletContext.contextPath}/index.html">Home</a></h3>
<hr>
<table>
    <thead>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Редактировать</th>
        <th>Удалить</th>
    </tr>
    </thead>
    <c:forEach items="${mealsTo}" var="mlsto">
        <tr class="${mlsto.excess ? 'red' : 'green'}">
            <td>${mlsto.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))}</td>
            <td>${mlsto.description}</td>
            <td>${mlsto.calories}</td>
            <td><a href="${pageContext.servletContext.contextPath}/meals?action=update&id=${mlsto.id}">edit</a></td>
            <td><a href="${pageContext.servletContext.contextPath}/meals?action=delete&id=${mlsto.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>

<form action="${pageContext.servletContext.contextPath}/meals" method="post">
    <table>
        <legend> Добавить прием пищи </legend>
        <tr>
            <td><b>Дата</b></td>
            <td><input type="datetime-local" name="datetime" value="Время приема пищи"></td>
        </tr>

        <tr>
            <td><b>Описание</b></td>
            <td><input type="text" name="description" value="Описание"></td>
        </tr>

        <tr>
            <td><b>Калории</b></td>
            <td><input type="number" name="calories" value="1000"></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Сохранить"></td>
        </tr>
    </table>
</form>

</body>
</html>
