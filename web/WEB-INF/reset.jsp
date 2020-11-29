<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password</title>
    </head>
    <body>
        <c:if test="${emailSent eq 'false' || uuid eq null}" >
            <h1>Reset Password</h1>
            <h2>Please enter your email address to reset your password</h2>
            <form action="reset" method="post">
                <p><input type="text" name="sendEmail"</p>
                <input type="hidden" name="action" value="sendConfirm">
                <input type="submit" value="Submit">
            </form>
        </c:if>
            
        <c:if test="${emailSent eq 'true' || uuid ne null}" >
            <h1>Enter a new Password</h1>
            <form action="reset" method="post">
                <p><input type="text" name="newPassword"</p>
                <input type="hidden" name="uuid" value="${uuid}">
                <input type="hidden" name="action" value="passwordChange">
                <input type="submit" value="Submit">
            </form>
        </c:if>
            
        <li><a href="login">Back to Login Page</a></li>
    </body>
</html>
