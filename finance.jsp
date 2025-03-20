<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Finance Management System Menu</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #121212; /* Dark background color */
            color: #ffffff; /* White text color for contrast */
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .menu {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 100%;
            max-width: 300px; /* Maximum width for buttons */
        }
        .menu a {
            display: block;
            width: 100%;
            padding: 15px 0;
            margin: 10px 0;
            background-color: #007bff;
            color: #ffffff;
            text-align: center;
            text-decoration: none;
            font-size: 18px;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }
        .menu a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="menu">
        <a href="${pageContext.request.contextPath}/trimesters/list">Trimesters / Fee Structure</a>
        <a href="${pageContext.request.contextPath}/student-fee-payment/list">Student Fee Payment</a>
        <a href="${pageContext.request.contextPath}/teachers-payroll/list">Teachers Payroll</a>
        <a href="${pageContext.request.contextPath}/staff-payroll/list">Staff Payroll</a>
        <a href="${pageContext.request.contextPath}/Dashboard.jsp">Go back to Dashboard</a>
    </div>
</body>
</html>
