<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
    if (session == null || session.getAttribute("username") == null) {
        response.sendRedirect("LogIn.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>${empty borrower ? 'Add New Borrower' : 'Edit Borrower'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            color: #333;
        }
        .container {
            width: 50%;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #333;
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #555;
        }
        .form-group input[type="text"],
        .form-group input[type="date"],
        .form-group input[type="email"],
        .form-group select {
            width: 100%;
            padding: 10px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .form-group input[type="submit"] {
            width: auto;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .form-group input[type="submit"]:hover {
            background-color: #45a049;
        }
        .form-group .btn-secondary {
            background-color: #ccc;
            color: black;
            text-decoration: none;
            padding: 10px 20px;
            border-radius: 5px;
            display: inline-block;
            margin-top: 10px;
        }
        .form-group .btn-secondary:hover {
            background-color: #bbb;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>${empty borrower ? 'Add New Borrower' : 'Edit Borrower'}</h2>
   <form action="${empty borrower ? '/SchoolSystem/borrower/insert' : '/SchoolSystem/borrower/update'}" method="post">
    <input type="hidden" name="action" value="${empty borrower ? 'insert' : 'update'}">
    <c:if test="${!empty borrower}">
        <input type="hidden" name="borrowerId" value="${borrower.id}">
    </c:if>

    <div class="form-group">
        <label for="borrowerType">Borrower Type:</label>
        <select id="borrowerType" name="borrowerType" required>
            <option value="Student" ${borrower.borrowerType == 'Student' ? 'selected' : ''}>Student</option>
            <option value="Teacher" ${borrower.borrowerType == 'Teacher' ? 'selected' : ''}>Teacher</option>
            <option value="Staff" ${borrower.borrowerType == 'Staff' ? 'selected' : ''}>Staff</option>
        </select>
    </div>

    <div class="form-group">
        <label for="borrowerRefId">Borrower Reference ID:</label>
        <input type="text" id="borrowerRefId" name="borrowerRefId" value="${empty borrower ? '' : borrower.borrowerRefId}" required>
    </div>

    <input type="submit" value="Save">
    <a href="${pageContext.request.contextPath}/borrower?action=list" class="btn-cancel">Back to List</a>
</form>

</div>
</body>
</html>
