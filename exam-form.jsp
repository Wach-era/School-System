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
    <title>Exam Form</title>
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
        label {
            font-weight: bold;
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
    <h2>${exam != null ? "Update Exam" : "Add New Exam"}</h2>
    <form action="${pageContext.request.contextPath}/exams${exam != null ? '/update' : '/insert'}" method="post">
        <div class="form-group">
            <label for="examId">Exam ID</label>
            <input type="text" class="form-control" id="examId" name="examId" value="${exam.examId}" ${exam != null ? "readonly" : ""} placeholder="subjectname-form-()-cat-()" required>
        </div>
        <div class="form-group">
            <label for="subjectName">Subject Name</label>
            <select class="form-control" id="subjectName" name="subjectName" required>
                <c:forEach var="subject" items="${subjects}">
                    <option value="${subject.subjectName}" ${exam != null && exam.subjectName == subject.subjectName ? "selected" : ""}>${subject.subjectName}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
    <label for="classId">Class</label>
    <select class="form-control" id="classId" name="classId" required>
        <c:forEach var="schoolClass" items="${classes}">
            <option value="${schoolClass.classId}" ${exam != null && exam.classId == schoolClass.classId ? "selected" : ""}>${schoolClass.classId}</option>
        </c:forEach>
    </select>
</div>
        <div class="form-group">
            <label for="examDate">Exam Date</label>
            <input type="date" class="form-control" id="examDate" name="examDate" value="${exam != null ? exam.examDate : ''}" required>
        </div>
        <div class="form-group">
            <label for="maxScore">Max Score</label>
            <input type="number" class="form-control" id="maxScore" name="maxScore" value="${exam != null ? exam.maxScore : ''}" step="0.01" required>
        </div>
        <button type="submit" class="btn">${exam != null ? "Update" : "Add"}</button>
        <a href="${pageContext.request.contextPath}/exams/list" class="btn btn-danger">Back to List</a>
    </form>
</div>
</body>
</html>
