<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Project List</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div class="my-2" style="text-align: center">
        <c:if test="${not empty projects}">
            <table class="table table-striped table-sm table-responsive-md">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <security:authorize access="hasRole('ADMIN')">
                        <th scope="col">User Id</th>
                        <th scope="col">Project Id</th>
                    </security:authorize>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">
                        <div>Status Type</div>
                        <div>
                            <a href="/project-list-status-asc">ASC</a>
                            <a href="/project-list-status-desc">DESC</a>
                        </div>
                    </th>
                    <th scope="col">
                        <div>Start Date</div>
                        <div>
                            <a href="/project-list-start-date-asc">ASC</a>
                            <a href="/project-list-start-date-desc">DESC</a>
                        </div>
                    </th>
                    <th scope="col">
                        <div>Finish Date</div>
                        <div>
                            <a href="/project-list-finish-date-asc">ASC</a>
                            <a href="/project-list-finish-date-desc">DESC</a>
                        </div>
                    </th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="project" items="${projects}">
                    <c:set var="number" value="${number + 1}" scope="page"/>
                    <tr>
                        <th scope="row">${number}</th>
                        <security:authorize access="hasRole('ADMIN')">
                            <td><input value="${project.user.id}" disabled/></td>
                            <td><input value="${project.id}" disabled/></td>
                        </security:authorize>
                        <td>${project.name}</td>
                        <td>${project.description}</td>
                        <td>${project.statusType}</td>
                        <td>${project.startDate}</td>
                        <td>${project.finishDate}</td>
                        <c:url var="view" value="/project-view/${project.user.id}/${project.id}"/>
                        <c:url var="update" value="/project-update/${project.user.id}/${project.id}"/>
                        <c:url var="delete" value="/project-delete/${project.user.id}/${project.id}"/>
                        <td>
                            <a href="${view}">View</a> |
                            <a href="${update}">Update</a> |
                            <a href="${delete}" onclick="if (!(confirm('Are you sure?'))) return false">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <form action="/project-search" method="get" class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" id="search" name="search" type="search" placeholder="Search"
                       aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </c:if>
    </div>
    <c:if test="${empty projects}">
        <div class="container my-5">The project list is empty!</div>
    </c:if>
    <p class="my-5">
        <a href="/project-create" class="btn btn-primary">Add New Project</a>
    </p>
</div>
</body>
</html>