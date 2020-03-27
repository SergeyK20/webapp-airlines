<%--
  Created by IntelliJ IDEA.
  User: kashk
  Date: 07.02.2020
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Navigation</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  </head>
  <body class="w3-grey">
  <div class="w3-container w3-black w3-opacity w3-center-align">
    <h1 align="center">Navigation page</h1>
  </div>
  <div class="w3-container w3-grey w3-center-align w3-border">
    <ul  class=" w3-navbar w3-card-8 w3-hoverable ">
      <li><p align="center"><a href='<c:url value="/flightAll?command=getList" />'>Display flight table</a></p></li>
      <li><p align="center"><a href='<c:url value="/route?command=getList"/>'>Display route table</a></p></li>
      <li><p align="center"><a href='<c:url value="/city?command=getList" />'>Display city table</a></p></li>
      <li><p align="center"><a href='<c:url value="/plane?command=getList" />'>Display plane table</a></p></li>
      <li><p align="center"><a href='<c:url value="/typePlane?command=getList" />'>Display type plane table</a></p></li>
    </ul>
  </div>
  </body>
</html>
