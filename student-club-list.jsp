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
    <title>Student Clubs List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
            <%@ include file="/WEB-INF/includes/header.jsp" %>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
        }
        .container {
            width: 80%;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
       .btn {
            display: inline-block;
            padding: 8px 16px;
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
        .btn-delete {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-delete:hover {
            background-color: #c82333;
            border-color: #bd2130;
        }
        .mb-2 {
            margin-bottom: 10px;
        }
    </style>
    <script>
        function deleteStudentClub(studentClubId) {
            if (confirm("Are you sure you want to delete this student club?")) {
                window.location.href = '/SchoolSystem/student-clubs/delete?student_club_id=' + studentClubId;
            }
        }
    </script>
    
</head>
<body>
<div class="container">
    <h2>Student Clubs List</h2>
        <a href="${pageContext.request.contextPath}/student-clubs/new" class="btn btn-secondary">Add Student to Club</a>
                       <a href="${pageContext.request.contextPath}/clubs/listClub" class="btn btn-secondary">View Clubs</a>
                       <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
    <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    
        
    <table>
        <thead>
            <tr>
                <th>Student Club ID</th>
                <th>Student ID</th>
                <th>Club Name</th>
                <th>Join Date</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="studentClub" items="${listStudentClub}">
                <tr>
                    <td>${studentClub.studentClubId}</td>
                    <td>${studentClub.studentId}</td>
                    <td>${studentClub.clubName}</td>
                    <td>${studentClub.joinDate}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/student-clubs/edit?student_club_id=${studentClub.studentClubId}" class="btn btn-secondary">Edit</a>
<a href="${pageContext.request.contextPath}/student-clubs/delete?student_club_id=${studentClub.studentClubId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this student club?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
