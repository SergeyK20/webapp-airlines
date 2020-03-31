<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kashk
  Date: 09.03.2020
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add route</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-grey">
<div class="w3-container w3-black w3-opacity w3-center-align">
    <h1>Add route</h1>
</div>
    <style>
        .colortext {
            color: red;
        }
    </style>
    <div align="center">
    <form action="${pageContext.request.contextPath}/route" method="post">
        <input type="hidden" name="command" value="create">

            Name city from: <select name="city_from"class="w3-round-large w3-small" style="width: 100px;height: auto">
                <c:forEach var="City" items="${list}">
                   <option   value="${City.id}">
                           <c:out value=" ${City.nameCity}"/>
                   </option>
                </c:forEach>
        <option selected value="${route.from.id}">
            <c:out value="from ${route.from.nameCity} "/>
        </option>
            </select><br/>

            Name city to:  <select name="city_to"class="w3-round-large w3-small" style="width: 100px;height: auto">
                <c:forEach var="City" items="${list}">
                    <option  name="city_to" value="${City.id}">
                        <c:out value=" ${City.nameCity}"/>
                    </option>
                </c:forEach>
        <option selected value="${route.to.id}">
            <c:out value="to ${route.to.nameCity} "/>
        </option>
        </select><br/>

        Flight time in minutes: <input type="text" name="travel_minutes" autocomplete="off" value="${route.travelTimeMinutes}"class="w3-round-large w3-small" style="width: 100px;height: auto"/><br/>
        <input type="submit" value="Save"class="w3-btn w3-round-large w3-light-grey w3-small">
    </form>
    <form action="${pageContext.request.contextPath}/route" method="post">
        <input type="hidden" name="transitionPage" value="routeJSP/addRoute.jsp">
        <input type="hidden" name="id_copy" value="${id_copy_no_on_servlet}">
        <input type="submit" name="command" value="insert" class="w3-btn w3-round-large w3-light-grey w3-small">
    </form>
    <p><a href='<c:url value="/route?command=getList" />'>Back</a></p>
    <span class="colortext"><c:out value="${errorMessage}"/>
    </div>
</body>
</html>
