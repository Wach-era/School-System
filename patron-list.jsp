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
    <meta charset="UTF-8">
    <title>List of Patrons</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
        }
        .container {
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
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
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
</head>
<body>
<div class="container">
    <h2>List of Patrons</h2>
    <a href="${pageContext.request.contextPath}/patrons/new" class="btn btn-primary mb-2">Add New Patron</a>
    <a href="${pageContext.request.contextPath}/clubs/listClub" class="btn btn-secondary">View Clubs</a>
    <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
    <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>ID</th>
                <th>National ID</th>
                <th>Club Name</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="patron" items="${listPatrons}">
                <tr>
                    <td>${patron.patronId}</td>
                    <td>${patron.nationalId}</td>
                    <td>${patron.clubName}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/patrons/edit?patron_id=${patron.patronId}" class="btn btn-primary">Edit</a>
                        <a href="${pageContext.request.contextPath}/patrons/delete?patron_id=${patron.patronId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this patron?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
