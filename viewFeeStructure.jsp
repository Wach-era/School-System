<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Fee Structure</title>
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
        .btn-danger {
            background-color: #dc3545;
            border-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
            border-color: #bd2130;
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
        .form-inline {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Fee Structure for Trimester: ${trimesterId}</h2>
    <div class="form-inline">
        <a href="${pageContext.request.contextPath}/fees/update?trimesterId=${trimesterId}" class="btn btn-primary">Update Fee Structure</a>
             <a href="${pageContext.request.contextPath}/trimesters/list" class="btn btn-primary">Go Back</a>
        <a href="${pageContext.request.contextPath}/fees/deleteAll?trimesterId=${trimesterId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete all fees for this trimester?')">Delete All</a>
    </div>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Description</th>
                <th>Amount</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="fee" items="${fees}">
                <tr>
                    <td>${fee.description}</td>
                    <td>${fee.amount}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/fees/delete?feeId=${fee.feeId}&trimesterId=${trimesterId}" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this fee?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td><strong>Total</strong></td>
                <td>
                    <c:set var="total" value="0" />
                    <c:forEach var="fee" items="${fees}">
                        <c:set var="total" value="${total + fee.amount}" />
                    </c:forEach>
                    <strong>${total}</strong>
                </td>
                <td></td>
            </tr>
        </tbody>
    </table>
</div>
</body>
</html>
