<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>User List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div class="my-2" style="text-align: center">
        <c:if test="${not empty users}">
            <table class="table table-striped table-sm table-responsive-md">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">User Id</th>
                    <th scope="col">Login</th>
                    <th scope="col">Role Type</th>
                    <th scope="col">Email</th>
                    <th scope="col">First Name</th>
                    <th scope="col">Last Name</th>
                    <th scope="col">Phone</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <c:set var="number" value="${number + 1}" scope="page"/>
                    <tr>
                        <th scope="row">${number}</th>
                        <td>
                            <input value="${user.id}" disabled/>
                        </td>
                        <td>${user.login}</td>
                        <td>${user.roleType}</td>
                        <td>${user.email}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.phone}</td>
                        <c:url var="view" value="/user-view/${user.id}"/>
                        <c:url var="update" value="/user-update/${user.id}"/>
                        <c:url var="delete" value="/user-delete/${user.id}"/>
                        <td>
                            <a href="${view}">View</a> |
                            <a href="${update}">Update</a> |
                            <a href="${delete}" onclick="if (!(confirm('Are you sure?'))) return false">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form action="/user-search" method="get" class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" id="search" name="search" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </c:if>
    </div>
    <c:if test="${empty users}">
        <div class="container my-5">The user list is empty!</div>
    </c:if>
    <p class="my-5">
        <a href="/user-create" class="btn btn-primary">Add New User</a>
    </p>
</div>
</body>
</html>