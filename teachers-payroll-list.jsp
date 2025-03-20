<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Teachers Payroll List</title>
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
    <h2>Teachers Payroll List</h2>
   <a href="${pageContext.request.contextPath}/finance.jsp" class="btn mb-3">Go Back</a>
    <a href="${pageContext.request.contextPath}/teachers/new" class="btn mb-3">Add New Teacher</a>
<a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
<a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
<table class="table table-bordered">
    <thead>
        <tr>
            <th>National ID</th>
            <th>Full Name</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="teacher" items="${listPayroll}">
                <tr>
                    <td>${teacher.nationalId}</td>
                    <td>${teacher.firstName} ${teacher.middleName} ${teacher.lastName}</td>
                    <td>  
                   <a href="${pageContext.request.contextPath}/teachers-payroll/new?nationalId=${teacher.nationalId}" class="btn btn-success">Handle Payroll</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</div>
</body>
</html>
