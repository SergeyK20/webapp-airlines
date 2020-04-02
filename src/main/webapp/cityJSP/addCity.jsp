<%--
  Created by IntelliJ IDEA.
  User: kashk
  Date: 03.03.2020
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add city</title>
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
<h1 align="center">Add city</h1>
</div>
    <div align="center">
        <form action="${pageContext.request.contextPath}/city" method="post">
            <input type="hidden" name="command" value="create" class="w3-btn w3-round-large">

            Name City: <input type="text" name="name_city" autocomplete="off" value="${city.nameCity}" class="w3-round-large w3-small" style="width: 100px;height: auto"/><br/>
            <input type="submit" value="Save" class="w3-btn w3-round-large w3-light-grey w3-small">
        </form>
    <form action="${pageContext.request.contextPath}/city" method="post">
        <input type="hidden" name="transitionPage" value="cityJSP/addCity.jsp">
        <input type="hidden" name="id_copy" value="${id_copy_no_on_servlet}">
        <input type="submit" name="command" value="insert" class="w3-btn w3-round-large w3-light-grey w3-small">

        <input type="hidden" name="id" value="${param.id}">
    </form>
    <p><a href='<c:url value="/city?command=getList" />'>Back</a></p>
    <span class="colortext"><c:out value="${errorMessage}"/>
    </div>
</body>
</html>
