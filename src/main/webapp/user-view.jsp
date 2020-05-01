<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View User</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div style="text-align: center">
        <form style="width: 400px; display: inline-block; text-align: left;">
            <input hidden type="text" class="form-control" id="user_id" name="user_id" value="${user.id}" readonly>
            <div class="form-group">
                <label for="login">Login</label>
                <input type="text" class="form-control" id="login" name="login" value="${user.login}" readonly>
            </div>
            <div class="form-group">
                <label for="email">Email Address</label>
                <input type="text" class="form-control" id="email" name="email"
                       value="${user.email}" readonly>
            </div>
            <div class="form-group">
                <label for="first_name">First Name</label>
                <input type="text" class="form-control" id="first_name" name="first_name"
                       value="${user.firstName}" readonly>
            </div>
            <div class="form-group">
                <label for="last_name">Last Name</label>
                <input type="text" class="form-control" id="last_name" name="last_name"
                       value="${user.lastName}" readonly>
            </div>
            <div class="form-group">
                <label for="role_tpe">Role</label>
                <input type="text" class="form-control" id="role_tpe" name="role_tpe"
                       value="${user.roleType}" readonly>
            </div>
            <a class="btn btn-primary" href="/user-update/${user.id}">Update User</a>
            <a class="btn btn-primary" href="/user-delete/${user.id}">Delete User</a>
        </form>
    </div>
</div>
</body>
</html>