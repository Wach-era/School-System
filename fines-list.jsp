<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Fines List</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #444;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            border: 1px solid #ddd;
            background-color: #f9f9f9;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #333;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
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
        <h2>Fines List</h2>
        <a href="${pageContext.request.contextPath}/fines/new" class="btn">Add New Fine</a>
        <a href="${pageContext.request.contextPath}/library-menu.jsp" class="btn">Go Back to Library Menu</a>
        <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn">Go Back to Dashboard</a>
        <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
        <table border="1">
            <tr>
                <th>Fine ID</th>
                <th>Borrow ID</th>
                <th>Fine Amount</th>
                <th>Fine Date</th>
                <th>Paid</th>
                <th>Action</th>
            </tr>
            <c:forEach items="${fines}" var="fine">
                <tr>
                    <td>${fine.fineId}</td>
                    <td>${fine.borrowId}</td>
                    <td>${fine.fineAmount}</td>
                    <td>${fine.fineDate}</td>
                    <td>${fine.paid ? 'Yes' : 'No'}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/fines/edit?id=${fine.fineId}" class="btn">Edit</a>
                        <a href="${pageContext.request.contextPath}/fines/delete?id=${fine.fineId}" class="btn btn-danger">Delete</a>
                        <c:if test="${!fine.paid}">
                            <a href="${pageContext.request.contextPath}/fines/pay?id=${fine.fineId}" class="btn">Pay</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
