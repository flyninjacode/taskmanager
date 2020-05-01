<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task Manager</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
    <link href="<c:url value="/assets/style.css" />" rel="stylesheet" type="text/css">
</head>
<body>
<nav class="navbar navbar-expand navbar-light bg-light">
    <a class="navbar-brand" href="#">Task Manager</a>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <security:authorize access="hasRole('ADMIN')">
                <a class="nav-item nav-link" href="/user-list">All Users</a>
            </security:authorize>
            <a class="nav-item nav-link" href="/project-list">Your Projects</a>
            <security:authorize access="hasRole('ADMIN')">
                <a class="nav-item nav-link" href="/project-list-all">All User Projects</a>
            </security:authorize>
            <a class="nav-item nav-link" href="/task-list">Your Tasks</a>
            <security:authorize access="hasRole('ADMIN')">
                <a class="nav-item nav-link" href="/task-list-all">All User Tasks</a>
            </security:authorize>
        </div>
    </div>
    <security:authorize access="isAuthenticated()">
        <security:authentication property="principal.username"/>
    </security:authorize>
    <security:authorize access="!isAuthenticated()">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="/login">Log In</a>
        </div>
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="/signup">Sign Up</a>
        </div>
    </security:authorize>
    <security:authorize access="hasRole('USER') and !hasRole('ADMIN')">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="/user">User</a>
        </div>
    </security:authorize>
    <security:authorize access="hasRole('ADMIN')">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="/admin">Admin</a>
        </div>
    </security:authorize>
    <security:authorize access="isAuthenticated()">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="/logout">Log Out</a>
        </div>
    </security:authorize>
</nav>
<c:if test="${not empty message}">
    <div class="alert alert-success">${message}</div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
</body>
</html>
