<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.time.format.DateTimeFormatter" %>


<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="${pageContext.servletContext.contextPath}/index.html">Home</a></h3>
<hr>
<table width="50%" border="5%">
    <thead>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>
    <c:forEach items="${mealsTo}" var="mlsto">
        <c:choose>
            <c:when test="${mlsto.excess}">
                <tr>
                    <td><font color="red">${mlsto.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</font>
                    </td>
                    <td><font color="red">${mlsto.description}</font></td>
                    <td><font color="red">${mlsto.calories}</font></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <td><font color="green">${mlsto.dateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}</font>
                    </td>
                    <td><font color="green">${mlsto.description}</font></td>
                    <td><font color="green">${mlsto.calories}</font></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
</table>
</body>
</html>
