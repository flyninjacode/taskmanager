<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
        <c:if test="${not empty projectSearch}">
            <table class="table table-striped table-sm table-responsive-md">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">User Id</th>
                    <th scope="col">Project Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Status Type</th>
                    <th scope="col">Start Date</th>
                    <th scope="col">Finish Date</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="project" items="${projectSearch}">
                    <c:set var="number" value="${number + 1}" scope="page"/>
                    <tr>
                        <th scope="row">${number}</th>
                        <td>
                            <input value="${project.user.id}" disabled/>
                        </td>
                        <td>
                            <input value="${project.id}" disabled/>
                        </td>
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
        </c:if>
    </div>
    <c:if test="${empty projectSearch}">
        <div class="container my-5">No project found by search!</div>
    </c:if>
    <p class="my-5">
        <a href="/project-list" class="btn btn-primary">Back</a>
    </p>
</div>
</body>
</html>