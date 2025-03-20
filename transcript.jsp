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
    <title>Transcript</title>
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
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Transcript for Class: ${classId}, Trimester: ${trimesterId}</h2>
    <c:if test="${not empty studentAverageScores}">
        <table>
            <thead>
            <tr>
                <th>Student ID</th>
                <th>Subject</th>
                <th>Average Score (%)</th>
                <th>Grade</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="studentEntry" items="${studentAverageScores}">
                <c:forEach var="subjectEntry" items="${studentEntry.value}">
                    <tr>
                        <td>${studentEntry.key}</td>
                        <td>${subjectEntry.key}</td>
                        <td>${subjectEntry.value}</td>
                        <td>${studentGrades[studentEntry.key][subjectEntry.key]}</td>
                    </tr>
                </c:forEach>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>
