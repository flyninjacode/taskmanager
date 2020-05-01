<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create User</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div style="text-align: center">
        <form action="/user-create" method="post" style="width: 400px; display: inline-block; text-align: left;">
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
            <button type="submit" class="btn btn-primary mb-2">Add User</button>
        </form>
    </div>
</div>
</body>
</html>