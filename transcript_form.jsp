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
    <title>Generate Transcript</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
            <%@ include file="/WEB-INF/includes/header.jsp" %>
    
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
            display: block;
            margin-bottom: 5px;
        }
        select {
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
    <h2>Generate Transcript</h2>
    <form action="${pageContext.request.contextPath}/transcript/generate" method="post">
        <div class="form-group">
            <label for="classId">Class ID:</label>
            <select id="classId" name="classId">
                <c:forEach var="schoolClass" items="${classes}">
                    <option value="${schoolClass.classId}">${schoolClass.classId}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="trimesterId">Trimester:</label>
            <select id="trimesterId" name="trimesterId">
                <c:forEach var="trimester" items="${trimesters}">
                    <option value="${trimester.trimesterId}">${trimester.startDate} to ${trimester.endDate}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn">Generate Transcript</button>
    </form>
</div>
</body>
</html>
