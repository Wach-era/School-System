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
    <title>${empty club ? 'Add New Club' : 'Edit Club'}</title>
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
        .form-group input[type="text"] {
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
    <h2>${empty club ? 'Add New Club' : 'Edit Club'}</h2>
    <form action="${empty club ? '/SchoolSystem/clubs/insert' : '/SchoolSystem/clubs/update'}" method="post">
        <c:if test="${!empty club}">
            <input type="hidden" name="old_club_name" value="${club.clubName}">
        </c:if>
        <div class="form-group">
            <label for="club_name">Club Name:</label>
            <input type="text" class="form-control" name="club_name" value="${empty club ? '' : club.clubName}" required>
        </div>
        <button type="submit" class="btn btn-primary">${empty club ? "Create" : "Update"}</button>
        <a href="${pageContext.request.contextPath}/clubs/list" class="btn btn-secondary">Cancel</a>
    </form>
</div>
</body>
</html>
