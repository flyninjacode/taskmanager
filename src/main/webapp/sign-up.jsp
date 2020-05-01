<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div style="text-align: center">
        <form action="/signup" method="post" style="width: 400px; display: inline-block; text-align: left;">
            <fieldset>
                <legend>Please Sign Up</legend>

                <c:if test="${not empty error}">
                    <div class="alert alert-danger">
                        AbstractUserDetailsAuthenticationProvider.badCredentials
                        <br/>
                    </div>
                </c:if>
                <div class="form-group">
                    <label for="login">Login</label>
                    <input type="text" class="form-control" id="login" name="login">
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" id="password" name="password">
                </div>
                <div class="form-group">
                    <label for="email">Email Address</label>
                    <input type="email" class="form-control" id="email" name="email">
                </div>
                <div class="form-group">
                    <label for="firstName">First Name</label>
                    <input type="text" class="form-control" id="firstName" name="firstName">
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name</label>
                    <input type="text" class="form-control" id="lastName" name="lastName">
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="text" class="form-control" id="phone" name="phone">
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="roleType" id="user" value="USER" checked>
                    <label class="form-check-label" for="user">
                        User
                    </label>
                </div>
                <div class="form-check">
                    <input class="form-check-input" type="radio" name="roleType" id="admin" value="ADMIN">
                    <label class="form-check-label" for="admin">
                        Admin
                    </label>
                </div>
                <button type="submit" class="btn btn-primary mb-2">Sign Up</button>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
