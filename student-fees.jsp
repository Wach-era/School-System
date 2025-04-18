<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Fee Payments</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
            <%@ include file="/WEB-INF/includes/header.jsp" %>
    
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
        }
        .container {
            width: 70%;
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
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        .alert-success {
            color: #3c763d;
            background-color: #dff0d8;
            border-color: #d6e9c6;
        }
        .alert-danger {
            color: #a94442;
            background-color: #f2dede;
            border-color: #ebccd1;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Student Fee Payments</h2>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <form method="post" action="${pageContext.request.contextPath}/student-fees">
        <input type="hidden" name="studentId" value="${param.studentId}">
        <input type="hidden" name="trimesterId" value="${param.trimesterId}">
        <input type="hidden" name="yearId" value="${param.yearId}">
        <div class="form-group">
            <label for="amountPaid">Amount Paid (KES):</label>
            <input type="number" class="form-control" id="amountPaid" name="amountPaid" step="0.01" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Payment</button>
    </form>
    <h3>Payments</h3>
    <table class="table">
        <thead>
            <tr>
                <th>Payment ID</th>
                <th>Student ID</th>
                <th>Trimester ID</th>
                <th>Amount Paid (KES)</th>
                <th>Payment Date</th>
                <th>Year ID</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="payment" items="${payments}">
                <tr>
                    <td>${payment.paymentId}</td>
                    <td>${payment.studentId}</td>
                    <td>${payment.trimesterId}</td>
                    <td>${payment.amountPaid}</td>
                    <td>${payment.paymentDate}</td>
                    <td>${payment.yearId}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
