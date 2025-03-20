<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${empty fine ? 'Add New Fine' : 'Edit Fine'}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            color: #333;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 50%;
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
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="date"], input[type="checkbox"], input[type="submit"] {
            margin-bottom: 20px;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #218838;
        }
        .btn-cancel {
            display: inline-block;
            padding: 10px 20px;
            text-decoration: none;
            color: #fff;
            background-color: #dc3545;
            border-radius: 4px;
            text-align: center;
            cursor: pointer;
        }
        .btn-cancel:hover {
            background-color: #c82333;
        }
        .form-group {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Fine Details</h2>
        <form action="${empty fine ? '/SchoolSystem/fines/insert' : '/SchoolSystem/fines/update'}" method="post">
            <input type="hidden" name="action" value="${empty fine ? 'insert' : 'update'}">
            <c:if test="${!empty fine}">
                <input type="hidden" name="fineId" value="${fine.fineId}">
            </c:if>

            <div class="form-group">
                <label for="borrowId">Borrow ID:</label>
                <input type="text" id="borrowId" name="borrowId" value="${empty fine ? '' : fine.borrowId}" required>
            </div>

            <div class="form-group">
                <label for="fineAmount">Fine Amount:</label>
                <input type="text" id="fineAmount" name="fineAmount" value="${empty fine ? '' : fine.fineAmount}" required>
            </div>

            <div class="form-group">
                <label for="fineDate">Fine Date:</label>
                <input type="date" id="fineDate" name="fineDate" value="${empty fine ? '' : fine.fineDate}" required>
            </div>

            <div class="form-group">
                <label for="paid">Paid:</label>
                <input type="checkbox" id="paid" name="paid" ${!empty fine && fine.paid ? 'checked' : ''}>
            </div>

            <input type="submit" value="${fine != null ? 'Update' : 'Add'} Fine">
            <a href="${pageContext.request.contextPath}/fines?action=list" class="btn-cancel">Cancel</a>
        </form>
    </div>
</body>
</html>
