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
    <title>Update plane</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-grey">
<div class="w3-container w3-black w3-opacity w3-center-align">
<h1 align="center">Update plane</h1>
</div>
<style>
    .colortext {
        color: red;
    }
</style>
<div align="center">
<form action="${pageContext.request.contextPath}/plane" method="post">
    <input type="hidden" name="command" value="update">
    <input type="hidden" name="id" value="${param.id}">

    Type plane: <select name="type_id"class="w3-round-large w3-small" style="width: 100px;height: auto">
    <c:forEach var="TypePlane" items="${list}">
        <option selected name="type_id" value="${TypePlane.id}">
            <c:out value=" ${TypePlane.nameType}"/>
        </option>
    </c:forEach>
    <option selected name="type_id" value="${plane.id}">
        <c:out value=" ${plane.typePlane.nameType}"/>
    </option>
</select><br/>

    Name plane:  <input type="text" name="name_plane" autocomplete="off" value="${plane.namePlane}"class="w3-round-large w3-small" style="width: 100px;height: auto"/><br/>

    <input type="submit" value="Save" class="w3-btn w3-round-large w3-light-grey w3-small">

</form>
<form action="${pageContext.request.contextPath}/plane" method="post">
    <input type="hidden" name="transitionPage" value="planeJSP/updatePlane.jsp">
    <input type="hidden" name="id_copy" value="${id_copy_no_on_servlet}">
    <input type="hidden" name="id" value="${param.id}">
    <input type="submit" name="command" value="insert"class="w3-btn w3-round-large w3-light-grey w3-small">
</form>
<p><a href='<c:url value="/plane?command=getList" />'>Back</a></p>
<span class="colortext"><c:out value="${errorMessage}"/>
</div>
</body>
</html>
