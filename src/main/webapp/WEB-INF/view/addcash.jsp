<%-- 
    Document   : addcash
    Created on : Jan 19, 2018, 7:45:58 PM
    Author     : rafal
--%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ADD cash panel</title>
    </head>
    <body>
         <c:forEach var="curr" items="${currencies}">
    <td>       
        <c:out value="${curr.name()}" />
        </br>
    </td>
    </c:forEach>
    </body>
    
    <form action="${pageContext.request.contextPath}/view/addcash" method="post">
	<p>Name of currency:</p>
	<input type="text" name="setting" maxlength="30">
	<p>How many:</p>
	<input type="text" name="value"	maxlength="30">
        <p>Login:</p>
	<input type="text" name="login"	maxlength="30">
	<input type="submit" value="change"/>		
    </form>
</html>
