<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Return Borrowed Book</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 600px;
            margin-top: 50px;
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-group label {
            font-weight: bold;
        }
        .form-control {
            margin-bottom: 15px;
        }
        .btn-primary {
            width: 100%;
        }
        .error-message {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="text-center mb-4">Return Borrowed Book</h2>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/library/processReturn" method="post">
        <div class="form-group">
            <label for="borrowId">Borrow ID:</label>
            <select id="borrowId" name="borrowId" class="form-control" required>
                <c:forEach var="borrowedBook" items="${listBorrowedBook}">
                    <option value="${borrowedBook.borrowId}">${borrowedBook.borrowId} - ${borrowedBook.bookId}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="returnDate">Return Date:</label>
            <input type="date" class="form-control" id="returnDate" name="returnDate" required>
        </div>
        <div class="form-group form-check">
            <input type="checkbox" class="form-check-input" id="lost" name="lost">
            <label class="form-check-label" for="lost">Lost</label>
        </div>
        <div class="form-group">
            <input type="submit" value="Return Book" class="btn btn-primary">
        </div>
    </form>
</div>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</body>
</html>