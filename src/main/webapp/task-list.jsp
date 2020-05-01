<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Task List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div class="my-2" style="text-align: center">
        <c:if test="${not empty tasks}">
            <table class="table table-striped table-sm table-responsive-md">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <security:authorize access="hasRole('ADMIN')">
                        <th scope="col">Owner Id</th>
                        <th scope="col">Project Id</th>
                        <th scope="col">Task Id</th>
                    </security:authorize>
                    <th scope="col">Project Name</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">
                        <div>Status Type</div>
                        <div>
                            <a href="/task-list-status-asc">ASC</a>
                            <a href="/task-list-status-desc">DESC</a>
                        </div>
                    </th>
                    <th scope="col">
                        <div>Start Date</div>
                        <div>
                            <a href="/task-list-start-date-asc">ASC</a>
                            <a href="/task-list-start-date-desc">DESC</a>
                        </div>
                    </th>
                    <th scope="col">
                        <div>Finish Date</div>
                        <div>
                            <a href="/task-list-finish-date-asc">ASC</a>
                            <a href="/task-list-finish-date-desc">DESC</a>
                        </div>
                    </th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="task" items="${tasks}">
                    <c:set var="number" value="${number + 1}" scope="page"/>
                    <tr>
                        <th scope="row">${number}</th>
                        <security:authorize access="hasRole('ADMIN')">
                            <td><input type="text" value="${task.user.id}" disabled></td>
                            <td><input type="text" value="${task.project.id}" disabled></td>
                            <td><input type="text" value="${task.id}" disabled></td>
                        </security:authorize>
                        <td>${task.project.name}</td>
                        <td>${task.name}</td>
                        <td>${task.description}</td>
                        <td>${task.statusType}</td>
                        <td>${task.startDate}</td>
                        <td>${task.finishDate}</td>
                        <c:url var="view" value="/task-view/${task.user.id}/${task.id}"/>
                        <c:url var="update" value="/task-update/${task.user.id}/${task.id}"/>
                        <c:url var="delete" value="/task-delete/${task.user.id}/${task.id}"/>
                        <td>
                            <a href="${view}">View</a> |
                            <a href="${update}">Update</a> |
                            <a href="${delete}" onclick="if (!(confirm('Are you sure?'))) return false">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form action="/task-search" method="get" class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" id="search" name="search" type="search" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </c:if>
    </div>
    <c:if test="${empty tasks}">
        <div class="container my-5">The task list is empty!</div>
    </c:if>
    <p class="my-5">
        <a href="/task-create" class="btn btn-primary">Add New Task</a>
    </p>
</div>
</body>
</html>