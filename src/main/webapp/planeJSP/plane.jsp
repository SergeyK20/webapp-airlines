<%--
  Created by IntelliJ IDEA.
  User: kashk
  Date: 02.03.2020
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
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
    <h1 align="center">Plane</h1>
</div>
<div align="center"class="w3-container w3-blue-grey w3opacity w3-center">
    <%--формы для работы с кнопками--%>
    <form action="${pageContext.request.contextPath}/plane" method="post" id="search"></form>
    <form action="${pageContext.request.contextPath}/plane" method="post" id="edit"></form>
    <form action="${pageContext.request.contextPath}/plane" method="post" id="create"></form>
    <form action="${pageContext.request.contextPath}/plane" method="post" id="copy"></form>
    <%--Поле поиска--%>
    <input type="text" name="text_search" autocomplete="off" value="" form="search"class="w3-round-large w3-small "
           style="width: auto;height: auto">
    <%--Раскрывающийся список полей для поиска--%>
    <select class="w3-round-large w3-select w3-small" name="name_field" form="search" style=" width: auto">
        <option value="PLANE_NAME">Plane name</option>
        <option value="TYPE_NAME">Type name</option>
    </select>
    <%--Параметры передающиеся с поиском--%>
        <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" type="submit" value="search" form="search">
            search
        </button>
        <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" name="command" type="submit" value="drop"
                form="search">drop
        </button>
    <input type="hidden" name="command" value="search" form="search">
    <%--Параметры передающиеся с изменением или удалением--%>
        <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" name="command" type="submit" value="delete"
                form="edit">delete
        </button>
    <input type="hidden" name="transitionPage" value="planeJSP/updatePlane.jsp" form="edit">
    <input type="hidden" name="command" value="getListType" form="edit">
        <button class="w3-btn w3-round w3-light-grey w3-small w3-hover-grey" type="submit" value="update" form="edit">
            update
        </button>
    <c:set value="${param.id_copy_no_on_servlet}" var="id_copy_no_on_servlet" scope="application"/>
    <%--Параметры передающиеся с созданием--%>
    <input type="hidden" name="transitionPage" value="planeJSP/addPlane.jsp" form="create">
    <input type="hidden" name="command" value="getListType" form="create">
        <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" type="submit" value="create" form="create">
            create
        </button>
    <%--Параметры передающиеся с копированием--%>
        <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" type="submit" value="copy" form="copy">copy
        </button>
    <input type="hidden" name="command" value="getList" form="copy">
</div>
<% int i = 1;%>
<table class="w3-table w3-bordered w3-striped w3-hoverable">
    <tr>
        <td><c:out value="Sort"/></td>
        <td>
            <form action="${pageContext.request.contextPath}/plane" method="post">
                <input type="hidden" name="command" value="sort">
                <input type="hidden" name="field_name" value="PLANE_NAME">
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort ascending">▼
                </button>
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort descending">▲
                </button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/plane" method="post">
                <input type="hidden" name="command" value="sort">
                <input type="hidden" name="field_name" value="TYPE_NAME">
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort ascending">▼
                </button>
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort descending">▲
                </button>
            </form>
        </td>
        <td><c:out value="Update and Delete"/></td>
        <td><c:out value="Copy"/></td>
    </tr>
    <tr>
        <td><c:out value="Id"/></td>
        <td><c:out value="Name plane"/></td>
        <td><c:out value="Name type"/></td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach var="Plane" items="${list}">
        <tr>
            <td><%= i++%>
            </td>
            <td><c:out value=" ${Plane.namePlane}"/></td>
            <td><c:out value=" ${Plane.typePlane.nameType}"/></td>
            <td>
                <input form="edit" type="radio" name="id" value="${Plane.id}">
            </td>
            <td>
                <input form="copy" type="radio" name="id_copy_no_on_servlet" value="${Plane.id}">
            </td>
        </tr>
    </c:forEach>
</table>
<p align="center"><a href='<c:url value="/index.jsp" />' class="w3-grey">Navigation page</a></p>
<span class="colortext"><c:out value="${errorMessage}"/>
</body>
</html>
