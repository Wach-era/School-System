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
    <title>${trimester != null ? 'Edit' : 'Add'} Trimester</title>
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
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            font-weight: bold;
        }
        .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            color: #fff;
            background-color: #007bff;
            border-radius: 4px;
            border: none;
            font-size: 16px;
            cursor: pointer;
            text-align: center;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>${trimester != null ? 'Edit' : 'Add'} Trimester</h2>
        <form action="${trimester != null ? 'update' : 'insert'}" method="post">
            <div class="form-group">
                <label for="trimesterId">Trimester ID:</label>
                <input type="text" id="trimesterId" name="trimesterId" value="${trimester != null ? trimester.trimesterId : ''}" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="startDate">Start Date:</label>
                <input type="date" id="startDate" name="startDate" value="${trimester != null ? trimester.startDate : ''}" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="endDate">End Date:</label>
                <input type="date" id="endDate" name="endDate" value="${trimester != null ? trimester.endDate : ''}" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">${trimester != null ? 'Update' : 'Add'} Trimester</button>
        </form>
            <a href="${pageContext.request.contextPath}/trimesters/list" class="btn btn-primary mb-2">Go Back</a>
    </div>
</body>
</html>
