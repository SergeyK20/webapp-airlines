<%@ page import="dao.FlightsDAO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kashk
  Date: 13.03.2020
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add airlines</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-grey">
<style>
    /*стиль для выводимых сообщений*/
    .colortext {
        color: red;
    }
</style>
<div class="w3-container w3-black w3-opacity w3-center-align">
<h1 align="center">Add route</h1>
</div>
<div align="center">
<form action="${pageContext.request.contextPath}/flightAll" method="post" id="create">
    <input type="hidden" name="command" value="create" class="w3-round-large w3-small" style="width: 200px;height: auto">

    Idfly:<input  align="center" type="text" name="id_flight" value="${flight.idAirlines}" class="w3-round-large w3-small" style="width: 100px;height: auto"><br>

    Route:<select name="route" class="w3-round-large w3-small" style="width: 100px;height: auto">
    <c:forEach var="Route" items="${list}">
        <option value="${Route.id}">
            <c:out value="from ${Route.from} "/>
            <c:out value="to ${Route.to} "/>
        </option>
    </c:forEach>
    <option selected value="${flight.route.id}">
        <c:out value="from ${flight.route.from}"/>
        <c:out value="to ${flight.route.to} "/>
    </option>
    </select><br>

    Date: <input type="date" name="date" value="${flight.date}" class="w3-round-large w3-small " style="width: 100px;height: auto"><br>

    time: <input type="time" name="time" value="${flight.time}" class="w3-round-large w3-small " style="width: 100px;height: auto"><br>

    Plane:<select name="plane" class="w3-round-large w3-small " style="width: 100px;height: auto">
    <c:forEach var="Plane" items="${list1}">
        <option value="${Plane.id}">
            <c:out value=" ${Plane.namePlane}"/>
            <c:out value=" ${Plane.typePlane.nameType}"/>
        </option>
    </c:forEach>
    <option selected value="${flight.plane.id}">
        <c:out value=" ${flight.plane.namePlane}"/>
        <c:out value=" ${flight.plane.typePlane}"/>
    </option>
    </select><br>
</form>
<form action="${pageContext.request.contextPath}/flightAll" method="post">
    <input align="center" type="hidden" name="transitionPage" value="airlinesJSP/addAirlines.jsp">
    <input align="center" type="hidden" name="id_copy" value="${id_copy_no_on_servlet}">
    <input align="center" type="submit" name="command" value="insert" class="w3-btn w3-round-large w3-light-grey">
    <input align="center" type="submit" value="save" form="create" class="w3-btn w3-round-large w3-light-grey">
</form>
<p align="center"><a href='<c:url value="/flightAll?command=getList" />'>Back</a></p>
<span class="colortext"><c:out value="${errorMessage}"/>
</div>
</body>
</html>
