<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Project</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div style="text-align: center">
        <form action="/project-update" method="post" style="width: 400px; display: inline-block; text-align: left;">
            <input hidden type="text" class="form-control" id="userId" name="userId" value="${project.user.id}">
            <input hidden type="text" class="form-control" id="id" name="id" value="${project.id}">
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${project.name}">
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <input type="text" class="form-control" id="description" name="description" value="${project.description}">
            </div>
            <div class="form-group">
                <label for="startDate">Start Date</label>
                <input type="date" class="form-control" id="startDate" name="startDate" value="${project.startDate}">
            </div>
            <div class="form-group">
                <label for="finishDate">Finish Date</label>
                <input type="date" class="form-control" id="finishDate" name="finishDate" value="${project.finishDate}">
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="statusType" id="planned" value="PLANNED" checked>
                <label class="form-check-label" for="planned">
                    Planned
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="statusType" id="in_process" value="INPROCESS">
                <label class="form-check-label" for="in_process">
                    In process
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="statusType" id="ready" value="READY">
                <label class="form-check-label" for="ready">
                    Ready
                </label>
            </div>
            <button type="submit" class="btn btn-primary mb-2">Update Project</button>
        </form>
    </div>
</div>
</body>
</html>