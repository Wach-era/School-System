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
    <script>
        function changeTrimester() {
            document.getElementById('trimesterForm').submit();
        }
    </script>
</head>
<body>
<div class="container">
    <form id="trimesterForm" method="get" action="${pageContext.request.contextPath}/transcripts">
        <input type="hidden" name="studentId" value="${studentId}">
        <label for="trimester">Select Trimester:</label>
        <select name="trimesterId" onchange="changeTrimester()" class="form-control">
            <c:forEach var="trimester" items="${trimesters}">
                <option value="${trimester.trimesterId}" ${trimester.trimesterId == param.trimesterId ? 'selected' : ''}>
                    Trimester ${trimester.trimesterId}
                </option>
            </c:forEach>
        </select>
    </form>
    
    <h2>${studentFullName}'s Transcript</h2>
    <p><strong>Student ID:</strong> ${studentId}</p>
    <p><strong>Full Name:</strong> ${studentFullName}</p>
    <p><strong>Trimester:</strong> ${param.trimesterId}</p>
    
    <table>
        <thead>
            <tr>
                <th>Subject</th>
                <th>Score</th>
                <th>Grade</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="entry" items="${resultsBySubject}">
                <tr>
                    <td>${entry.key}</td>
                    <td>${Math.round(entry.value * 100.0 / 100.0)}</td>
                    <td><c:choose>
                        <c:when test="${entry.value >= 90}">A</c:when>
                        <c:when test="${entry.value >= 80}">B+</c:when>
                        <c:when test="${entry.value >= 70}">B</c:when>
                        <c:when test="${entry.value >= 60}">C+</c:when>
                        <c:when test="${entry.value >= 50}">C</c:when>
                        <c:when test="${entry.value >= 40}">D</c:when>
                        <c:otherwise>F</c:otherwise>
                    </c:choose></td>
                </tr>
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <td><strong>Total</strong></td>
                <td colspan="2"><strong>${Math.round(totalScore * 100.0 / 100.0)}</strong></td>
            </tr>
            <tr>
                <td><strong>GPA</strong></td>
                <td colspan="2"><strong>${gpa}</strong></td>
            </tr>
        </tfoot>
    </table>
    <a href="${pageContext.request.contextPath}/students/list" class="btn btn-primary mb-2">Go Back</a>
</div>
</body>
</html>
