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
    <title>Student Club Form</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
        }
        .container {
            width: 50%;
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
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        .form-group input[type="text"], .form-group input[type="number"], .form-group input[type="date"] {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-group input[type="submit"] {
            width: auto;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .form-group input[type="submit"]:hover {
            background-color: #45a049;
        }
        .form-group .btn-secondary {
            background-color: #ccc;
            color: black;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 5px;
            display: inline-block;
            margin-top: 10px;
        }
        .form-group .btn-secondary:hover {
            background-color: #bbb;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>${empty studentClub ? 'Add New Student Club' : 'Edit Student Club'}</h2>
    <form action="${empty studentClub ? '/SchoolSystem/student-clubs/insert' : '/SchoolSystem/student-clubs/update'}" method="post">
        <c:if test="${!empty studentClub}">
            <input type="hidden" name="student_club_id" value="${studentClub.studentClubId}">
        </c:if>
        <div class="form-group">
            <label for="student_id">Student ID:</label>
            <input type="number" class="form-control" name="student_id" value="${empty studentClub ? '' : studentClub.studentId}" required>
        </div>
        <div class="form-group">
            <label for="club_name">Club Name:</label>
            <input type="text" class="form-control" name="club_name" value="${empty studentClub ? '' : studentClub.clubName}" required>
        </div>
        <div class="form-group">
            <label for="join_date">Join Date:</label>
            <input type="date" class="form-control" name="join_date" value="${empty studentClub ? '' : studentClub.joinDate}" required>
        </div>
        <button type="submit" class="btn btn-primary">${empty studentClub ? "Add Member" : "Update"}</button>
        <a href="${pageContext.request.contextPath}/student-clubs/list" class="btn btn-secondary">View Students in Clubs</a>
    </form>
</div>
</body>
</html>
