<%--
  Created by IntelliJ IDEA.
  User: kashk
  Date: 10.03.2020
  Time: 3:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Update route</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-grey">
<div class="w3-container w3-black w3-opacity w3-center-align">
<h1>Update route</h1>
</div>
<style>
    .colortext {
        color: red;
    }
</style>
<div align="center">
<form action="${pageContext.request.contextPath}/route" method="post">
    <input type="hidden" name="command" value="update">
    <input type="hidden" name="id" value="${param.id}">

    Name city from: <select name="city_from"class="w3-round-large w3-small" style="width: 100px;height: auto">
    <c:forEach var="City" items="${list}">
        <option name="city_from" value="${City.id}">
            <c:out value=" ${City.name_city}"/>
        </option>
    </c:forEach>
    <option selected name="city_from" value="${route.from.id}">
        <c:out value=" ${route.from.name_city}"/>
    </option>
</select><br/>

    Name city to:<select name="city_to"class="w3-round-large w3-small" style="width: 100px;height: auto">
    <c:forEach var="City" items="${list}">
        <option selected name="city_to" value="${City.id}">
            <c:out value=" ${City.name_city}"/>
        </option>
    </c:forEach>
    <option selected name="city_to" value="${route.to.id}">
        <c:out value=" ${route.to.name_city}"/>
    </option>
</select><br/>

    Flight time in minutes: <input type="text" name="travel_minutes" autocomplete="off"
                                   value="${route.travelTimeMinutes}"class="w3-round-large w3-small" style="width: 100px;height: auto"><br/>
    <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" type="submit" value="save">
        create
    </button>
</form>
<form action="${pageContext.request.contextPath}/route" method="post">
    <input type="hidden" name="transitionPage" value="routeJSP/updateRoute.jsp">
    <input type="hidden" name="id_copy" value="${id_copy_no_on_servlet}">
    <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" name="command" type="submit" value="insert">
        insert
    </button>
    <input type="hidden" name="id" value="${param.id}">
</form>
<p><a href='<c:url value="/route?command=getList" />'>Back</a></p>
<span class="colortext"><c:out value="${errorMessage}"/>
</div>
</body>
</html>
