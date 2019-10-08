<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Meals</title>
    <style type="text/css">
        th, td {
            padding: 5px;
            border: 1px solid;
        }

        tr.red {
            padding: 5px;
            color: red;
        }

        tr.green {
            padding: 5px;
            color: green;
        }
    </style>
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
    </tr>
    </thead>
    <c:forEach items="${mealsTo}" var="mlsto">
        <tr class="${mlsto.excess ? 'red' : 'green'}">
            <td>${mlsto.parsedDateTime}</td>
            <td>${mlsto.description}</td>
            <td>${mlsto.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
