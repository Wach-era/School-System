<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>${schoolClass != null ? 'Edit Class' : 'Add New Class'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
        }
        .container {
            width: 50%;
            margin: 50px auto;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }
        h2 {
            color: #333;
            text-align: center;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .btn-primary {
            background-color: #007bff;
            border-color: #007bff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .btn-danger {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>${schoolClass != null ? 'Edit Class' : 'Add New Class'}</h2>
    <form action="${pageContext.request.contextPath}/classes/${schoolClass != null ? 'update' : 'insert'}" method="post">
        <div class="form-group">
            <label for="classId">Class ID</label>
            <input type="text" class="form-control" name="class_id" value="${schoolClass.classId}" ${schoolClass != null ? 'readonly' : ''} required>
        </div>
        <div class="form-group">
            <label for="classTeacher">Class Teacher National ID</label>
            <input type="text" class="form-control" name="class_teacher" value="${schoolClass.classTeacher}" required>
        </div>
        <div class="form-group">
            <label for="maxStudents">Max Students</label>
            <input type="number" class="form-control" name="max_students" value="${schoolClass.maxStudents}" required>
        </div>
        <div class="form-group">
            <label for="roomNumber">Room Number</label>
            <input type="text" class="form-control" name="room_number" value="${schoolClass.roomNumber}" required>
        </div>
        <div class="form-group">
            <input type="submit" class="btn btn-primary" value="${schoolClass != null ? 'Update' : 'Add'}">
            <a href="${pageContext.request.contextPath}/classes/list" class="btn btn-danger">Cancel</a>
        </div>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">
                ${errorMessage}
            </div>
        </c:if>
    </form>
</div>
</body>
</html>
