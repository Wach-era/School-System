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
    <meta charset="UTF-8">
    <title>Handle Payroll</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
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
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
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
            border: 1px solid #007bff;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .btn:hover {
            background-color: #0056b3;
            border-color: #0056b3;
        }
        .btn-success {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-success:hover {
            background-color: #218838;
            border-color: #1e7e34;
        }
        .btn-secondary {
            background-color: #6c757d;
            border-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
            border-color: #545b62;
        }
        .table-container {
    margin-top: 30px;
}

.table {
    width: 100%;
    margin-bottom: 20px;
}

.table thead th {
    background-color: #f8f9fa;
    border-bottom: 2px solid #dee2e6;
    text-align: center; /* Center the header text */
}

.table tbody td {
    text-align: center; /* Center the body text */
    vertical-align: middle; /* Vertically center the body text */
}

.table tbody .btn {
    display: inline-block; /* Ensure buttons are inline */
    margin: 0 5px; /* Optional: Add some spacing between buttons */
}

    </style>
</head>
<body>
<div class="container">
    <h2>${payroll != null ? "Edit Payroll" : "Add Payroll"}</h2>

    <form action="${pageContext.request.contextPath}/teachers-payroll/${payroll != null ? 'update' : 'insert'}" method="post">
        <input type="hidden" name="payrollId" value="${payroll != null ? payroll.payrollId : ''}" />
        <input type="hidden" name="nationalId" value="${payroll != null ? payroll.nationalId : teacher.nationalId}" />
        <div class="form-group">
            <label for="salary">Salary</label>
            <input type="number" id="salary" name="salary" class="form-control" value="${payroll != null ? payroll.salary : 0}" required>
        </div>
        <div class="form-group">
            <label for="bonus">Bonus</label>
            <input type="number" id="bonus" name="bonus" class="form-control" value="${payroll != null ? payroll.bonus : 0}" required>
        </div>
        <div class="form-group">
            <label for="deductions">Deductions</label>
            <input type="number" id="deductions" name="deductions" class="form-control" value="${payroll != null ? payroll.deductions : 0}" required>
        </div>
        <div class="form-group">
            <label for="paymentDate">Payment Date</label>
            <input type="date" id="paymentDate" name="paymentDate" class="form-control" value="${payroll != null ? payroll.paymentDate : ''}" required>
        </div>
        <div class="form-group">
            <label for="status">Status</label>
            <select id="status" name="status" class="form-control">
                <option value="Pending" ${payroll != null && payroll.status == 'Pending' ? 'selected' : ''}>Pending</option>
                <option value="Completed" ${payroll != null && payroll.status == 'Completed' ? 'selected' : ''}>Completed</option>
            </select>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">${payroll != null ? 'Update' : 'Add'}</button>
            <a href="${pageContext.request.contextPath}/teachers-payroll/list" class="btn btn-secondary">Cancel</a>
        </div>
    </form>

    <div class="table-container">
        <h2>Previous Payments</h2>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Payment Date</th>
                    <th>Salary</th>
                    <th>Bonus</th>
                    <th>Deductions</th>
                    <th>Net Salary</th>
                     <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="payment" items="${listPayments}">
                    <tr>
                        <td><fmt:formatDate value="${payment.paymentDate}" pattern="yyyy-MM-dd" /></td>
                        <td>${payment.salary}</td>
                        <td>${payment.bonus}</td>
                        <td>${payment.deductions}</td>
                        <td>${payment.netSalary}</td>
                        <td>${payment.status}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/teachers-payroll/edit?payrollId=${payment.payrollId}" class="btn btn-primary">Edit</a>
                            <a href="${pageContext.request.contextPath}/teachers-payroll/delete?payrollId=${payment.payrollId}" class="btn btn-danger">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
