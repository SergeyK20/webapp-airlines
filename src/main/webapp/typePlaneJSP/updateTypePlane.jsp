<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kashk
  Date: 10.03.2020
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update type</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-grey">
<div class="w3-container w3-black w3-opacity w3-center-align">
    <h1 align="center">Update type</h1>
</div>
    <body>
    <div align="center">
    <style>
        .colortext {
            color: red;
        }
    </style>
    <div align="center">
    <form action="${pageContext.request.contextPath}/typePlane" method="post">
        <input type="hidden" name="command" value="update">
        <input type="hidden" name="id" value=${param.id}>
        New name type:<input type="text" name="type_name" autocomplete="off" value="${type.nameType}"class="w3-round-large w3-small" style="width: 100px;height: auto"/><br>
            <input type="submit" value="Update" class="w3-btn w3-light-grey w3-round-large w3">
    </form>
    <form action="${pageContext.request.contextPath}/typePlane" method="post">
        <input type="hidden" name="transitionPage" value="typePlaneJSP/updateTypePlane.jsp">
        <input type="hidden" name="id_copy" value="${id_copy_no_on_servlet}">
        <input type="submit" name="command" value="insert"  class="w3-btn w3-light-grey w3-round-large">
        <input type="hidden" name="id" value="${param.id}">
    </form>
    </div>
    <p><a href='<c:url value="/typePlane?command=getList" />'>Back</a></p>
    <span class="colortext"><c:out value="${errorMessage}"/>
    </div>
    </body>
</body>
</html>
