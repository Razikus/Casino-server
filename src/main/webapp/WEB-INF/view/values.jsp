<%-- 
    Document   : login
    Created on : Dec 19, 2017, 11:44:39 PM
    Author     : rafal
--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Panel Casino</title>
</head>
<body>
    
    ${blad}
    
    <c:forEach var="setting" items="${settings}">
    <td>       
        <c:out value="${setting.getName()}" /> :
        <c:out value="${setting.getStringValue()}" />
        </br>
    </td>
    </c:forEach>
      <form action="${pageContext.request.contextPath}/change" method="post">
	<p>Name of setting:</p>
	<input type="text" name="setting" maxlength="30">
	<p>New Value:</p>
	<input type="text" name="value"	maxlength="30">
	<input type="submit" value="change"/>		
    </form>
    <%-- 
        <h1>Log In to Admin Panel</h1>
	<form method="post" action="j_security_check">
		<p>Login:</p>
		<input type="text" name="j_username"/>
		<p>Password:</p>
		<input type="text" name="j_password"> <br/>
		<input type="submit" value="LogIn"/>
		
	</form>
    --%>

</body>
</html>
