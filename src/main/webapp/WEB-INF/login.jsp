<%-- 
    Document   : login
    Created on : Dec 21, 2017, 11:59:04 AM
    Author     : rafal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pls Login</title>
    </head>
    <body>
        <h1>Enter the credentials</h1>
        <form  method="POST" action="j_security_check">
            <table>
                <tr>
                    <td>Username: </td>
                    <td> <input type="text" name="j_username"> </td>
                </tr>
                 <tr>
                    <td>Password:  </td>
                    <td> <input type="password" name="j_password"> </td>
                </tr>
                 <tr>
                    <td> <input type="submit" name="Login"> </td>
                </tr>      
            </table>
        </form>
    </body>
</html>
