<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.util.List" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Student Fee Payment</title>
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
        .payment-form {
            margin-bottom: 20px;
        }
        .payment-form label {
            display: block;
            margin-bottom: 10px;
        }
        .payment-form input, .payment-form select {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .payment-form button {
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            color: #fff;
            background-color: #28a745;
            border: 1px solid #28a745;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .payment-form button:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Handle Payment for ${student.firstName} ${student.lastName}</h2>
   <form action="${pageContext.request.contextPath}/student-fee-payment/${payment != null ? 'update' : 'add'}" method="post">
    <input type="hidden" name="studentId" value="${student.studentId}">
    <input type="hidden" name="paymentId" value="${payment != null ? payment.paymentId : ''}">
    
    <div class="form-group">
        <a href="${pageContext.request.contextPath}/student-fee-payment/list" class="btn btn-success">Go Back</a><br>
        <label>Trimester</label>
        <select name="trimesterId" class="form-control">
            <c:forEach var="trimester" items="${trimesters}">
                <option value="${trimester.trimesterId}" ${payment != null && payment.trimesterId == trimester.trimesterId ? 'selected' : ''}>${trimester.trimesterId}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label>Amount Paid</label>
        <input type="number" step="0.01" name="amountPaid" class="form-control" required value="${payment != null ? payment.amountPaid : ''}">
    </div>

    <div class="form-group">
        <label>Date of Payment</label>
        <input type="date" name="dateOfPayment" class="form-control" required value="${payment != null ? payment.dateOfPayment : ''}">
    </div>

    <div class="form-group">
        <label>Payment Mode</label>
        <select name="paymentMode" class="form-control" required>
            <option value="Mobile Payment" ${payment != null && payment.paymentMode == 'Mobile Payment' ? 'selected' : ''}>Mobile Payment</option>
            <option value="Cheque" ${payment != null && payment.paymentMode == 'Cheque' ? 'selected' : ''}>Cheque</option>
            <option value="Cash" ${payment != null && payment.paymentMode == 'Cash' ? 'selected' : ''}>Cash</option>
        </select>
    </div>

    <button type="submit" class="btn btn-success">${payment != null ? 'Update Payment' : 'Submit Payment'}</button>
</form>

<h3>Previous Payments</h3>
<table class="table table-bordered">
    <thead>
        <tr>
            <th>Date</th>
            <th>Trimester</th>
            <th>Amount Paid</th>
            <th>Payment Mode</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="payment" items="${previousPayments}">
            <tr>
                <td>${payment.dateOfPayment}</td>
                <td>${payment.trimesterId}</td>
                <td>${payment.amountPaid}</td>
                <td>${payment.paymentMode}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/student-fee-payment/edit" method="get" style="display:inline;">
                        <input type="hidden" name="paymentId" value="${payment.paymentId}">
                        <button type="submit" class="btn btn-warning">Update</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/student-fee-payment/delete" method="post" style="display:inline;">
                        <input type="hidden" name="paymentId" value="${payment.paymentId}">
                        <input type="hidden" name="studentId" value="${student.studentId}">
                        <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this payment?');">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>


</div>
</body>
</html>
