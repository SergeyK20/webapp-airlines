<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kashk
  Date: 10.03.2020
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add plane</title>
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
    <h1 align="center">Add plane</h1>
</div>
    <div align="center">
    <form action="${pageContext.request.contextPath}/plane" method="post">
        <input type="hidden" name="command" value="create">
        Type plane: <select name="type_id" class="w3-round-large w3-small" style="width: 97px;height: auto">
        <c:forEach var="TypePlane" items="${list}">
            <option selected name="type_id" value="${TypePlane.id}">
                <c:out value=" ${TypePlane.nameType}"/>
            </option>
            <option selected name="type_id" value="${plane.id}">
                <c:out value=" ${plane.typePlane.nameType}"/>
            </option>
        </c:forEach>
    </select>
        <br/>
        Name plane: <input type="text" name="name_plane" autocomplete="off" value="${plane.namePlane}"class="w3-round-large w3-small" style="width: 100px;height: auto"/><br/>
        <input type="submit" value="Save" class="w3-btn w3-round-large w3-light-grey">
    </form>
    <form action="${pageContext.request.contextPath}/plane" method="post">
        <input type="hidden" name="transitionPage" value="planeJSP/addPlane.jsp">
        <input type="hidden" name="id_copy" value="${id_copy_no_on_servlet}">
        <input type="submit" name="command" value="insert" class="w3-btn w3-round-large w3-light-grey">

    </form>
<p><a href='<c:url value="/plane?command=getList" />'>Back</a></p>
<span class="colortext"><c:out value="${errorMessage}"/>
</div>
</body>
</html>
