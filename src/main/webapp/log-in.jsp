<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div style="text-align: center">
        <form action="doLogin" method="post" style="width: 400px; display: inline-block; text-align: left;">
            <fieldset>
                <legend>Please Sign In</legend>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>
                <div class="form-group">
                    <label for="login">User Login</label>
                    <input type="text" class="form-control" placeholder="User Login"
                           id="login" name="login">
                </div>
                <div class="form-group">
                    <label for="login">Password</label>
                    <input type="password" class="form-control" placeholder="Password"
                           id="password" name="password">
                </div>
                <button type="submit" class="btn btn-primary mb-2">Log In</button>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
