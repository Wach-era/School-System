<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Fee Structure</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
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
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
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
        .btn-primary {
            background-color: #28a745;
            border-color: #28a745;
        }
        .btn-primary:hover {
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
    </style>
    <script>
        function addRow() {
            var table = document.getElementById("feeTable");
            var row = table.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);
            var cell3 = row.insertCell(2);
            cell1.innerHTML = '<input type="text" name="description" class="form-control" required>';
            cell2.innerHTML = '<input type="number" name="amount" class="form-control" step="0.01" required>';
            cell3.innerHTML = '<button type="button" class="btn btn-danger" onclick="removeRow(this)">Remove</button>';
        }

        function removeRow(button) {
            var row = button.parentNode.parentNode;
            row.parentNode.removeChild(row);
        }
    </script>
</head>
<body>
<div class="container">
    <h2>${empty fees ? 'Add' : 'Update'} Fee Structure</h2>
    <a href="${pageContext.request.contextPath}/trimesters/list" class="btn btn-primary">Go Back</a>
    <form method="post" action="${pageContext.request.contextPath}/fees/${empty fees ? 'insert' : 'update'}">
        <input type="hidden" name="trimesterId" value="${param.trimesterId}">
        <div class="form-group">
            <label for="description">Description:</label>
            <table id="feeTable" class="table">
                <thead>
                    <tr>
                        <th>Description</th>
                        <th>Amount (KES)</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty fees}">
                        <c:forEach var="fee" items="${fees}">
                            <tr>
                                <td><input type="text" name="description" value="${fee.description}" class="form-control" required></td>
                                <td><input type="number" name="amount" value="${fee.amount}" class="form-control" step="0.01" required></td>
                                <td><button type="button" class="btn btn-danger" onclick="removeRow(this)">Remove Row</button></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
        </div>
        <button type="button" class="btn btn-secondary" onclick="addRow()">Add Row</button>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>
</body>
</html>
