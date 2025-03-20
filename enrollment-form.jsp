<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Enrollment Form</title>
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
        .form-group label {
            display: block;
            margin-bottom: 8px;
        }
        .form-control {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            color: #fff;
            background-color: #007bff;
            border: 1px solid #007bff;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Enrollment Form</h2>
    <form action="${pageContext.request.contextPath}/enrollments/${enrollment != null ? 'update' : 'insert'}" method="post">
        <input type="hidden" name="enrollment_id" value="${enrollment.enrollmentId}">
        <div class="form-group">
            <label for="student_id">Student ID</label>
            <input type="text" name="student_id" class="form-control" value="${enrollment.studentId}" required>
        </div>
        <div class="form-group">
            <label for="class_id">Class ID</label>
            <input type="text" name="class_id" class="form-control" value="${enrollment.classId}" required>
        </div>
        <div class="form-group">
            <label for="year_id">Year ID</label>
            <select name="year_id" class="form-control" required>
                <c:forEach var="year" items="${listYear}">
                    <option value="${year.yearId}" ${year.yearId == enrollment.yearId ? 'selected' : ''}>${year.yearId}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="status">Status</label>
            <input type="text" name="status" class="form-control" value="${enrollment.status}" required>
        </div>
        <div class="form-group">
        <label for="retake">Retake:</label>
    <input type="checkbox" name="retake" value="true" ${enrollment.retake ? 'checked' : ''}>
    </div>
        <button type="submit" class="btn btn-primary">${enrollment != null ? 'Update' : 'Add'} Enrollment</button>
    </form>
</div>
</body>
</html>
