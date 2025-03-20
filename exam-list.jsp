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
    <title>Exam List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
        }
        .container {
            width: 80%;
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
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
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
    <h2>Exam List</h2>
    <a href="${pageContext.request.contextPath}/exams/new" class="btn mb-3">Add New Exam</a>
                <!--  a href="${pageContext.request.contextPath}/transcript/generate" class="btn mb-3">Generate Transcripts</a>-->
    <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
    <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    <table>
        <thead>
            <tr>
                <th>Exam ID</th>
                <th>Subject Name</th>
                <th>Exam Date</th>
                <th>Max Score</th>
                <th>Class ID</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="exam" items="${listExams}">
                <tr>
                    <td>${exam.examId}</td>
                    <td>${exam.subjectName}</td>
                    <td>${exam.examDate}</td>
                    <td>${exam.maxScore}</td>
                    <td>${exam.classId}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/exams/edit?examId=${exam.examId}" class="btn btn-primary">Edit</a>
                        <a href="${pageContext.request.contextPath}/exams/delete?examId=${exam.examId}" class="btn btn-danger">Delete</a>
                        <a href="${pageContext.request.contextPath}/submitResults?examId=${exam.examId}" class="btn btn-success">Enter Results</a> <!-- New Button -->
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
