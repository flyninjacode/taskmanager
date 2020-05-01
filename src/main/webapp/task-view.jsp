<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Task</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
<jsp:include page="index.jsp"/>
<div class="container-fluid">
    <div style="text-align: center">
        <form style="width: 400px; display: inline-block; text-align: left;">
            <input hidden type="text" class="form-control" id="user_id" name="user_id" value="${task.user.id}" readonly>
            <input hidden type="text" class="form-control" id="project_id" name="project_id" value="${task.project.id}" readonly>
            <input hidden type="text" class="form-control" id="id" name="id" value="${task.id}" readonly>
            <div class="form-group">
                <label for="name">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${task.name}" readonly>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <input type="text" class="form-control" id="description" name="description"
                       value="${task.description}" readonly>
            </div>
            <div class="form-group">
                <label for="start_date">Start Date</label>
                <input type="text" class="form-control" id="start_date" name="start_date"
                       value="${task.startDate}" readonly>
            </div>
            <div class="form-group">
                <label for="finish_date">Finish Date</label>
                <input type="text" class="form-control" id="finish_date" name="finish_date"
                       value="${task.finishDate}" readonly>
            </div>
            <div class="form-group">
                <label for="status_type">Status</label>
                <input type="text" class="form-control" id="status_type" name="status_type"
                       value="${task.statusType}" readonly>
            </div>
            <a class="btn btn-primary" href="/task-update/${task.user.id}/${task.id}">Update Task</a>
            <a class="btn btn-primary" href="/task-delete/${task.user.id}/${task.id}">Delete Task</a>
        </form>
    </div>
</div>
</body>
</html>