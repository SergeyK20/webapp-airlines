<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<head>
    <title>Airlines</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body class="w3-grey">
<div class="w3-container w3-black w3-opacity w3-center-align">
    <h2 align="center">Airlines</h2>
</div>
<style>
    /*стиль для выводимых сообщений*/
    .colortext {
        color: red;
    }
</style>
<div class="w3-container w3-blue-grey w3opacity w3-center">
    <%--формы для работы с кнопками--%>
    <form action="${pageContext.request.contextPath}/flightAll" method="post" id="search"></form>
    <form action="${pageContext.request.contextPath}/flightAll" method="post" id="edit"></form>
    <form action="${pageContext.request.contextPath}/flightAll" method="post" id="create"></form>
    <form action="${pageContext.request.contextPath}/flightAll" method="post" id="copy"></form>

    <%--Поле поиска--%>
    <input type="text" name="text_search" autocomplete="off" value="" form="search" class="w3-round-large w3-small "
           style="width: auto;height: auto">
    <%--Раскрывающийся список полей для поиска--%>
    <select class="w3-round-large w3-select w3-small" name="name_field" form="search" style=" width: auto">
        <option selected value="ID_FLIGHTS">Id flights</option>
        <option value="FROM_NAME">From city</option>
        <option value="TO_NAME">To city</option>
        <option value="PLANE_NAME">Plane name</option>
        <option value="TYPE_NAME">Type name</option>
        <option value="DATE_VALUE">Date</option>
        <option value="TIME_VALUE">Time</option>
        <option value="TIME_TRAVEL">Time travel</option>
    </select>
    <%--Параметры передающиеся с поиском--%>
    <%--<input class="button" type="submit" value="search" form="search">
    <input class="button" type="submit" name="command" value="drop" form="search">--%>
    <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" type="submit" value="search" form="search">
        search
    </button>
    <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" name="command" type="submit" value="drop"
            form="search">drop
    </button>

    <input type="hidden" name="command" value="search" form="search">
    <%--Параметры передающиеся с изменением или удалением--%>
    <%--<input class="button" type="submit" name="command" value="delete" form="edit">--%>
    <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" name="command" type="submit" value="delete"
            form="edit">delete
    </button>
    <input type="hidden" name="transitionPage" value="airlinesJSP/updateAirlines.jsp" form="edit">
    <input type="hidden" name="command" value="getListRouteAndPlane" form="edit">
    <button class="w3-btn w3-round w3-light-grey w3-small w3-hover-grey" type="submit" value="update" form="edit">
        update
    </button>
    <%--<input class="button" type="submit" value="update" form="edit">--%>
    <c:set value="${param.id_copy_no_on_servlet}" var="id_copy_no_on_servlet" scope="application"/>
    <%--Параметры передающиеся с созданием--%>
    <input type="hidden" name="transitionPage" value="airlinesJSP/addAirlines.jsp" form="create">
    <input type="hidden" name="command" value="getListRouteAndPlane" form="create">
    <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" type="submit" value="create" form="create">
        create
    </button>
    <%--<input class="button" type="submit" value="create" form="create">%>
<%--Параметры передающиеся с копированием--%>
    <button class="w3-btn w3-round w3-light-grey w3-small  w3-hover-grey" type="submit" value="copy" form="copy">copy
    </button>
    <input type="hidden" name="command" value="getList" form="copy">
</div>

<% int i = 1;%>
<table class="w3-table w3-bordered w3-striped w3-hoverable">
    <tr class="w3-blue-grey">
        <td><c:out value="Sort"/></td>
        <td>
            <form action="${pageContext.request.contextPath}/flightAll" method="post">
                <input type="hidden" name="command" value="sort">
                <input type="hidden" name="field_name" value="ID_FLIGHTS">
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort ascending">▼
                </button>
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort descending">▲
                </button>
                <%--<input type="submit" name="view" value="sort ascending">
                <input type="submit" name="view" value="sort descending">--%>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/flightAll" method="post">
                <input type="hidden" name="command" value="sort">
                <input type="hidden" name="field_name" value="FROM_NAME">
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort ascending">▼
                </button>
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort descending">▲
                </button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/flightAll" method="post">
                <input type="hidden" name="command" value="sort">
                <input type="hidden" name="field_name" value="TO_NAME">
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort ascending">▼
                </button>
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort descending">▲
                </button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/flightAll" method="post">
                <input type="hidden" name="command" value="sort">
                <input type="hidden" name="field_name" value="TIME_TRAVEL">
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort ascending">▼
                </button>
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort descending">▲
                </button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/flightAll" method="post">
                <input type="hidden" name="command" value="sort">
                <input type="hidden" name="field_name" value="DATE_VALUE">
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort ascending">▼
                </button>
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort descending">▲
                </button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/flightAll" method="post">
                <input type="hidden" name="command" value="sort">
                <input type="hidden" name="field_name" value="TIME_VALUE">
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort ascending">▼
                </button>
                <button class="w3-round-large w3-btn-floating w3-teal w3-grey w3-border" type="submit" name="view"
                        value="sort descending">▲
                </button>
            </form>
        </td>
        <td>
            <form action="${pageContext.request.contextPath}/flightAll" method="post">
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
            <form action="${pageContext.request.contextPath}/flightAll" method="post">
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
        <td><c:out value="Num "/></td>
        <td><c:out value="Id airines"/></td>
        <td><c:out value="From city"/></td>
        <td><c:out value="To city"/></td>
        <td><c:out value="Time minutes"/></td>
        <td><c:out value="Date"/></td>
        <td><c:out value="Time"/></td>
        <td><c:out value="Plane"/></td>
        <td><c:out value="Type plane"/></td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach var="Airlines" items="${list}">
        <tr>
            <td><%= i++ %>
            </td>
            <td><c:out value="${Airlines.idAirlines}"/></td>
            <td><c:out value=" ${Airlines.route.from}"/></td>
            <td><c:out value=" ${Airlines.route.to}"/></td>
            <td><c:out value=" ${Airlines.route.travelTimeMinutes}"/></td>
            <td><c:out value=" ${Airlines.date}"/></td>
            <td><c:out value=" ${Airlines.time}"/></td>
            <td><c:out value=" ${Airlines.plane.namePlane}"/></td>
            <td><c:out value=" ${Airlines.plane.typePlane}"/></td>
            <td>
                <input form="edit" type="radio" name="id" value="${Airlines.id}">
            </td>
            <td>
                <input form="copy" type="radio" name="id_copy_no_on_servlet" value="${Airlines.id}">
            </td>
        </tr>
    </c:forEach>
</table>
<p align="center"><a href='<c:url value="/index.jsp" />' class="w3-grey">Navigation page</a></p>
<span class="colortext"><c:out value="${errorMessage}"/>
</body>
</html>