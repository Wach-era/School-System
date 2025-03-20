<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Return List</title>
    <style>
    /* styles.css */

body {
    font-family: Arial, sans-serif;
    background-color: #f8f9fa;
    margin: 0;
    padding: 20px;
}

.container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    background: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

h1 {
    text-align: center;
    color: #333;
}

a.btn {
    display: inline-block;
    margin: 10px 5px;
    padding: 10px 20px;
    text-decoration: none;
    border-radius: 5px;
    color: #fff;
    transition: background-color 0.3s ease;
}

.btn-primary {
    background-color: #007bff;
}

.btn-info {
    background-color: #17a2b8;
}

.btn-danger {
    background-color: #dc3545;
}

a.btn:hover {
    opacity: 0.8;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-top: 20px;
}

table, th, td {
    border: 1px solid #ddd;
}

th, td {
    padding: 15px;
    text-align: left;
}

th {
    background-color: #f2f2f2;
}

tbody tr:nth-child(even) {
    background-color: #f9f9f9;
}

tbody tr:hover {
    background-color: #f1f1f1;
}

form {
    margin-top: 20px;
}

form div {
    margin-bottom: 15px;
}

form label {
    display: block;
    margin-bottom: 5px;
    color: #333;
}

form input[type="text"],
form input[type="date"] {
    width: calc(100% - 22px);
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

form input[type="submit"] {
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    background-color: #28a745;
    color: #fff;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

form input[type="submit"]:hover {
    background-color: #218838;
}

form a {
    text-decoration: none;
    margin-left: 10px;
    color: #007bff;
    transition: color 0.3s ease;
}

form a:hover {
    color: #0056b3;
}
    
    </style>
</head>
<body>
    <h1>Return List</h1>
    <a href="${pageContext.request.contextPath}/returns/new" class="btn btn-primary mb-2">Add Return</a>
    <a href="${pageContext.request.contextPath}/library-menu.jsp" class="btn btn-info">Go Back to Library Menu</a>
    <a href="${pageContext.request.contextPath}/Dashboard.jsp" class="btn btn-info">Go Back to Dashboard</a>
        <a href="${pageContext.request.contextPath}/LogIn.jsp" class="btn btn-danger">Logout</a>
    <table border="1">
        <thead>
            <tr>
                <th>Return ID</th>
                <th>Borrow ID</th>
                <th>Return Date</th>
                <th>Fine Amount</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="return" items="${returns}">
                <tr>
                    <td>${ret.returnId}</td>
                    <td>${ret.borrowId}</td>
                    <td>${ret.returnDate}</td>
                    <td>${ret.fineAmount}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/returns/edit?id=${ret.returnId}">Edit</a>
                        <a href="${pageContext.request.contextPath}/returns/delete?id=${ret.returnId}" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
